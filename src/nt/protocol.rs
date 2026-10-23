//! Decoder for the `/Rerun/` NetworkTables protocol (v1).
//!
//! Java's `NtRecorder` publishes one native NT topic per leaf of each component's Arrow datatype.
//! This module turns those columns back into Arrow arrays using `re_sdk_types` reflection, so the
//! Rust side never carries per-component knowledge.
//!
//! | Arrow type | Topics |
//! |---|---|
//! | `Boolean` | `P` = `boolean[]` |
//! | integers | `P` = `long[]` |
//! | `UInt64` | `P` = `long[]` raw bits, plus `P/.u64` = `string[]` decimals once a value overflows |
//! | floats | `P` = `double[]` |
//! | `Utf8` | `P` = `string[]` |
//! | `Binary` | `P` = `raw`, `P/.lengths` = `long[]` |
//! | `FixedSizeList(n, T)` | recurse at `P/0` … `P/{n-1}` |
//! | `Struct` | recurse at `P/<field name>` |
//! | `List` | `P/.count` = `long[]`, flattened elements at `P/.items` |
//! | `Union` | `P/.variant` = `long[]` field ordinals (`-1` null), payloads at `P/<variant name>` |
//! | nullable node | `P/.present` = `boolean[]`, only once a null has been seen |
//!
//! Lists and unions deviate from a naive reading of the table in one way each: both are
//! *variable-length* per component instance, so the count/ordinal columns are per-instance arrays
//! and their payloads are flattened. That keeps the topic count bounded by the datatype while
//! letting one batch hold lists of different lengths.

use std::collections::HashMap;
use std::sync::Arc;

use arrow::array::{
    ArrayRef, BinaryArray, BooleanArray, Float32Array, Float64Array, Int8Array, Int16Array,
    Int32Array, Int64Array, LargeBinaryArray, LargeListArray, LargeStringArray, ListArray,
    NullArray, StringArray, StructArray, UInt8Array, UInt16Array, UInt32Array, UInt64Array,
    UnionArray,
};
use arrow::buffer::{Buffer, OffsetBuffer, ScalarBuffer};
use arrow::datatypes::{DataType as ArrowDataType, UnionFields};
use arrow::error::ArrowError;
use re_log_types::EntityPath;
use re_sdk_types::reflection::reflection;
use re_types_core::{
    ArchetypeName, ComponentDescriptor, ComponentIdentifier, ComponentType,
    SerializedComponentBatch,
};

/// A value as published on one NetworkTables topic.
#[derive(Clone, Debug, PartialEq)]
pub enum NtValue {
    Long(i64),
    Double(f64),
    Boolean(bool),
    String(String),
    Raw(Vec<u8>),
    Longs(Vec<i64>),
    Doubles(Vec<f64>),
    Booleans(Vec<bool>),
    Strings(Vec<String>),
}

impl NtValue {
    /// Converts a NetworkTables msgpack value into the column representation.
    #[must_use]
    pub fn from_msgpack(value: &rmpv::Value) -> Option<Self> {
        Some(match value {
            rmpv::Value::Boolean(value) => Self::Boolean(*value),
            rmpv::Value::F64(value) => Self::Double(*value),
            rmpv::Value::F32(value) => Self::Double(f64::from(*value)),
            rmpv::Value::Integer(value) => Self::Long(value.as_i64()?),
            rmpv::Value::String(value) => Self::String(value.as_str()?.to_owned()),
            rmpv::Value::Binary(value) => Self::Raw(value.clone()),
            rmpv::Value::Array(values) => {
                if values.is_empty() {
                    return None;
                }
                match &values[0] {
                    rmpv::Value::Boolean(_) => Self::Booleans(
                        values
                            .iter()
                            .map(|value| value.as_bool())
                            .collect::<Option<Vec<_>>>()?,
                    ),
                    rmpv::Value::F64(_) | rmpv::Value::F32(_) => Self::Doubles(
                        values
                            .iter()
                            .map(|value| value.as_f64())
                            .collect::<Option<Vec<_>>>()?,
                    ),
                    rmpv::Value::Integer(_) => Self::Longs(
                        values
                            .iter()
                            .map(|value| value.as_i64())
                            .collect::<Option<Vec<_>>>()?,
                    ),
                    rmpv::Value::String(_) => Self::Strings(
                        values
                            .iter()
                            .map(|value| value.as_str().map(ToOwned::to_owned))
                            .collect::<Option<Vec<_>>>()?,
                    ),
                    _ => return None,
                }
            }
            rmpv::Value::Nil | rmpv::Value::Map(_) | rmpv::Value::Ext(_, _) => return None,
        })
    }
}

/// Read access to the cached NT columns of one entity.
pub trait ColumnSource {
    fn value(&self, path: &str) -> Option<&NtValue>;

    /// Whether this node (or a descendant) has any published column.
    fn has_any(&self, path: &str) -> bool;

    fn longs(&self, path: &str) -> Option<&[i64]> {
        match self.value(path)? {
            NtValue::Longs(values) => Some(values),
            _ => None,
        }
    }

    fn doubles(&self, path: &str) -> Option<&[f64]> {
        match self.value(path)? {
            NtValue::Doubles(values) => Some(values),
            _ => None,
        }
    }

    fn booleans(&self, path: &str) -> Option<&[bool]> {
        match self.value(path)? {
            NtValue::Booleans(values) => Some(values),
            _ => None,
        }
    }

    fn strings(&self, path: &str) -> Option<&[String]> {
        match self.value(path)? {
            NtValue::Strings(values) => Some(values),
            _ => None,
        }
    }

    fn raw(&self, path: &str) -> Option<&[u8]> {
        match self.value(path)? {
            NtValue::Raw(bytes) => Some(bytes),
            _ => None,
        }
    }
}

impl ColumnSource for HashMap<String, NtValue> {
    fn value(&self, path: &str) -> Option<&NtValue> {
        self.get(path)
    }

    fn has_any(&self, path: &str) -> bool {
        let prefix = format!("{path}/");
        self.keys()
            .any(|key| key == path || key.starts_with(&prefix))
    }
}

/// The `/Rerun/` metadata for one entity.
#[derive(Clone, Debug, Default, PartialEq, Eq)]
pub struct EntityMeta {
    /// Fully-qualified archetype name, e.g. `rerun.archetypes.Points3D`.
    pub archetype: Option<String>,
    /// `(column root, component type)` in archetype field order.
    pub components: Vec<(String, String)>,
    /// The entity was logged with `logStatic`.
    pub static_: bool,
}

impl EntityMeta {
    /// Parses the `.rerun/components` value (`positions=rerun.components.Position3D,colors=...`).
    #[must_use]
    pub fn parse_components(value: &str) -> Vec<(String, String)> {
        value
            .split(',')
            .filter_map(|entry| {
                let entry = entry.trim();
                if entry.is_empty() {
                    return None;
                }
                let (field, component) = entry.split_once('=')?;
                let field = field.trim();
                let component = component.trim();
                if field.is_empty() || component.is_empty() {
                    return None;
                }
                Some((field.to_owned(), component.to_owned()))
            })
            .collect()
    }
}

/// Errors from decoding one entity's columns.
#[derive(Debug, thiserror::Error)]
pub enum DecodeError {
    #[error("unknown component type {0:?}")]
    UnknownComponent(String),
    #[error("unsupported Arrow datatype {0:?}")]
    UnsupportedDatatype(ArrowDataType),
    #[error("malformed columns for {path}: {message}")]
    Columns { path: String, message: String },
    #[error(transparent)]
    Arrow(#[from] ArrowError),
}

/// Decodes every component of `meta` from `columns` into Rerun component batches.
pub fn decode_entity(
    meta: &EntityMeta,
    columns: &dyn ColumnSource,
) -> Result<Vec<SerializedComponentBatch>, DecodeError> {
    let reflection = reflection();
    let archetype = meta
        .archetype
        .as_deref()
        .filter(|name| !name.is_empty())
        .and_then(|name| ArchetypeName::try_new(name).ok());

    let mut batches = Vec::with_capacity(meta.components.len());
    for (field, component_type) in &meta.components {
        let Ok(component_type_id) = ComponentType::try_new(component_type.as_str()) else {
            return Err(DecodeError::UnknownComponent(component_type.clone()));
        };
        let Some(component) = reflection.components.get(&component_type_id) else {
            return Err(DecodeError::UnknownComponent(component_type.clone()));
        };
        if !columns.has_any(field) {
            // The archetype did not log this component in this batch.
            continue;
        }
        let count = infer_count(&component.datatype, columns, field)?;
        if count == 0 {
            continue;
        }
        let array = build_column(&component.datatype, columns, field, count)?;

        if cfg!(debug_assertions)
            && array.null_count() == 0
            && let Err(error) = (component.verify_arrow_array)(array.as_ref())
        {
            return Err(DecodeError::Columns {
                path: field.clone(),
                message: format!("reflection rejected the decoded array: {error}"),
            });
        }

        let identifier = match archetype {
            Some(archetype_name) => {
                ComponentIdentifier::from_archetype_field(archetype_name, field)
            }
            None => {
                ComponentIdentifier::try_new(field.as_str()).expect("column roots are non-empty")
            }
        };
        let descriptor = ComponentDescriptor {
            archetype,
            component: identifier,
            component_type: Some(component_type_id),
        };
        batches.push(SerializedComponentBatch::new(array, descriptor));
    }
    Ok(batches)
}

/// How many instances this node holds.
fn infer_count(
    datatype: &ArrowDataType,
    columns: &dyn ColumnSource,
    path: &str,
) -> Result<usize, DecodeError> {
    let present = format!("{path}/.present");
    if let Some(mask) = columns.booleans(&present) {
        return Ok(mask.len());
    }

    Ok(match datatype {
        ArrowDataType::Null => 0,
        ArrowDataType::Boolean => columns.booleans(path).map_or(0, <[bool]>::len),
        ArrowDataType::Int8
        | ArrowDataType::Int16
        | ArrowDataType::Int32
        | ArrowDataType::Int64
        | ArrowDataType::UInt8
        | ArrowDataType::UInt16
        | ArrowDataType::UInt32
        | ArrowDataType::UInt64 => columns.longs(path).map_or(0, <[i64]>::len),
        ArrowDataType::Float16 | ArrowDataType::Float32 | ArrowDataType::Float64 => {
            columns.doubles(path).map_or(0, <[f64]>::len)
        }
        ArrowDataType::Utf8 | ArrowDataType::LargeUtf8 => {
            columns.strings(path).map_or(0, <[String]>::len)
        }
        ArrowDataType::Binary | ArrowDataType::LargeBinary => columns
            .longs(&format!("{path}/.lengths"))
            .map_or(0, <[i64]>::len),
        ArrowDataType::FixedSizeList(field, _) => {
            infer_count(field.data_type(), columns, &format!("{path}/0"))?
        }
        ArrowDataType::Struct(fields) => match fields.first() {
            Some(field) => infer_count(
                field.data_type(),
                columns,
                &format!("{path}/{}", field.name()),
            )?,
            None => 0,
        },
        ArrowDataType::List(_) | ArrowDataType::LargeList(_) => columns
            .longs(&format!("{path}/.count"))
            .map_or(0, <[i64]>::len),
        ArrowDataType::Union(_, _) => columns
            .longs(&format!("{path}/.variant"))
            .map_or(0, <[i64]>::len),
        other => return Err(DecodeError::UnsupportedDatatype(other.clone())),
    })
}

/// The null mask published for this node, if any.
fn validity(
    columns: &dyn ColumnSource,
    path: &str,
    count: usize,
) -> Option<arrow::buffer::NullBuffer> {
    let mask = columns.booleans(&format!("{path}/.present"))?;
    if mask.len() != count || mask.iter().all(|valid| *valid) {
        return None;
    }
    Some(arrow::buffer::NullBuffer::from(mask.to_vec()))
}

fn value_at<T: Copy>(values: &[T], index: usize, default: T) -> T {
    values.get(index).copied().unwrap_or(default)
}

/// Recursively reconstructs one Arrow array from the NT columns.
fn build_column(
    datatype: &ArrowDataType,
    columns: &dyn ColumnSource,
    path: &str,
    count: usize,
) -> Result<ArrayRef, DecodeError> {
    if count == 0 {
        return Ok(arrow::array::new_empty_array(datatype));
    }
    let nulls = validity(columns, path, count);

    let array: ArrayRef = match datatype {
        ArrowDataType::Null => Arc::new(NullArray::new(count)),
        ArrowDataType::Boolean => {
            let values = columns.booleans(path).unwrap_or(&[]);
            Arc::new(
                (0..count)
                    .map(|i| {
                        nulls
                            .as_ref()
                            .is_none_or(|nulls| nulls.is_valid(i))
                            .then(|| value_at(values, i, false))
                    })
                    .collect::<BooleanArray>(),
            )
        }
        ArrowDataType::Int8
        | ArrowDataType::Int16
        | ArrowDataType::Int32
        | ArrowDataType::Int64 => {
            let values = columns.longs(path).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            macro_rules! build_int {
                ($array:ty, $ty:ty) => {
                    Arc::new(
                        (0..count)
                            .map(|i| valid(i).then(|| value_at(values, i, 0) as $ty))
                            .collect::<$array>(),
                    ) as ArrayRef
                };
            }
            match datatype {
                ArrowDataType::Int8 => build_int!(Int8Array, i8),
                ArrowDataType::Int16 => build_int!(Int16Array, i16),
                ArrowDataType::Int32 => build_int!(Int32Array, i32),
                _ => build_int!(Int64Array, i64),
            }
        }
        ArrowDataType::UInt8 | ArrowDataType::UInt16 | ArrowDataType::UInt32 => {
            let values = columns.longs(path).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            macro_rules! build_uint {
                ($array:ty, $ty:ty) => {
                    Arc::new(
                        (0..count)
                            .map(|i| valid(i).then(|| value_at(values, i, 0) as $ty))
                            .collect::<$array>(),
                    ) as ArrayRef
                };
            }
            match datatype {
                ArrowDataType::UInt8 => build_uint!(UInt8Array, u8),
                ArrowDataType::UInt16 => build_uint!(UInt16Array, u16),
                _ => build_uint!(UInt32Array, u32),
            }
        }
        ArrowDataType::UInt64 => {
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            // Values above `i64::MAX` get a decimal sidecar under `P/.u64`; the native `long[]`
            // column always holds the raw bits, so the two can never disagree about the shape.
            if let Some(values) = columns.strings(&format!("{path}/.u64")) {
                let mut parsed = Vec::with_capacity(count);
                for i in 0..count {
                    if !valid(i) {
                        parsed.push(None);
                        continue;
                    }
                    let text = values.get(i).map_or("0", String::as_str);
                    let value = text.parse::<u64>().map_err(|_| DecodeError::Columns {
                        path: path.to_owned(),
                        message: format!("{text:?} is not a u64"),
                    })?;
                    parsed.push(Some(value));
                }
                Arc::new(UInt64Array::from(parsed))
            } else {
                let values = columns.longs(path).unwrap_or(&[]);
                Arc::new(
                    (0..count)
                        .map(|i| valid(i).then(|| value_at(values, i, 0) as u64))
                        .collect::<UInt64Array>(),
                )
            }
        }
        ArrowDataType::Float16 => {
            let values = columns.doubles(path).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            let singles = (0..count)
                .map(|i| valid(i).then(|| value_at(values, i, 0.0) as f32))
                .collect::<Float32Array>();
            arrow::compute::cast(&singles, datatype)?
        }
        ArrowDataType::Float32 | ArrowDataType::Float64 => {
            let values = columns.doubles(path).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            if *datatype == ArrowDataType::Float32 {
                Arc::new(
                    (0..count)
                        .map(|i| valid(i).then(|| value_at(values, i, 0.0) as f32))
                        .collect::<Float32Array>(),
                )
            } else {
                Arc::new(
                    (0..count)
                        .map(|i| valid(i).then(|| value_at(values, i, 0.0)))
                        .collect::<Float64Array>(),
                )
            }
        }
        ArrowDataType::Utf8 | ArrowDataType::LargeUtf8 => {
            let values = columns.strings(path).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));
            let items = (0..count)
                .map(|i| valid(i).then(|| values.get(i).map_or("", String::as_str).to_owned()))
                .collect::<Vec<_>>();
            if *datatype == ArrowDataType::Utf8 {
                Arc::new(items.into_iter().collect::<StringArray>())
            } else {
                Arc::new(items.into_iter().collect::<LargeStringArray>())
            }
        }
        ArrowDataType::Binary | ArrowDataType::LargeBinary => {
            let bytes = columns.raw(path).unwrap_or(&[]);
            let lengths = columns.longs(&format!("{path}/.lengths")).unwrap_or(&[]);
            let valid = |i: usize| nulls.as_ref().is_none_or(|nulls| nulls.is_valid(i));

            let mut offsets = Vec::with_capacity(count + 1);
            let mut offset = 0usize;
            for i in 0..count {
                offsets.push(offset as i32);
                offset += lengths.get(i).copied().unwrap_or(0).max(0) as usize;
            }
            offsets.push(offset as i32);
            if offset > bytes.len() {
                return Err(DecodeError::Columns {
                    path: path.to_owned(),
                    message: format!(
                        "binary column has {offset} declared bytes but only {} were published",
                        bytes.len()
                    ),
                });
            }
            let buffer = Buffer::from(bytes);
            let _ = &valid;
            if *datatype == ArrowDataType::Binary {
                Arc::new(BinaryArray::try_new(
                    OffsetBuffer::new(ScalarBuffer::from(offsets)),
                    buffer,
                    nulls,
                )?)
            } else {
                let offsets = offsets.into_iter().map(i64::from).collect::<Vec<_>>();
                Arc::new(LargeBinaryArray::try_new(
                    OffsetBuffer::new(ScalarBuffer::from(offsets)),
                    buffer,
                    nulls,
                )?)
            }
        }
        ArrowDataType::FixedSizeList(field, size) => {
            let size = usize::try_from(*size).map_err(|_| DecodeError::Columns {
                path: path.to_owned(),
                message: format!("negative fixed-size-list size {size}"),
            })?;
            let mut children = Vec::with_capacity(size);
            for index in 0..size {
                children.push(build_column(
                    field.data_type(),
                    columns,
                    &format!("{path}/{index}"),
                    count,
                )?);
            }
            let values = if size == 0 {
                arrow::array::new_empty_array(field.data_type())
            } else {
                let refs = children.iter().map(AsRef::as_ref).collect::<Vec<_>>();
                arrow::compute::concat(&refs)?
            };
            Arc::new(arrow::array::FixedSizeListArray::try_new(
                Arc::clone(field),
                i32::try_from(size).unwrap_or(i32::MAX),
                values,
                nulls,
            )?)
        }
        ArrowDataType::Struct(fields) => {
            let mut children = Vec::with_capacity(fields.len());
            for field in fields {
                children.push(build_column(
                    field.data_type(),
                    columns,
                    &format!("{path}/{}", field.name()),
                    count,
                )?);
            }
            Arc::new(StructArray::try_new(fields.clone(), children, nulls)?)
        }
        ArrowDataType::List(field) | ArrowDataType::LargeList(field) => {
            let counts =
                columns
                    .longs(&format!("{path}/.count"))
                    .ok_or_else(|| DecodeError::Columns {
                        path: path.to_owned(),
                        message: "missing /.count".to_owned(),
                    })?;
            let mut offsets = Vec::with_capacity(count + 1);
            let mut total = 0i64;
            offsets.push(0);
            for index in 0..count {
                total += counts.get(index).copied().unwrap_or(0).max(0);
                offsets.push(total);
            }
            let items = build_column(
                field.data_type(),
                columns,
                &format!("{path}/.items"),
                usize::try_from(total).unwrap_or(0),
            )?;
            let items = if total == 0 {
                arrow::array::new_empty_array(field.data_type())
            } else {
                items
            };
            if matches!(datatype, ArrowDataType::List(_)) {
                let offsets = offsets
                    .into_iter()
                    .map(|offset| i32::try_from(offset).unwrap_or(i32::MAX))
                    .collect::<Vec<_>>();
                Arc::new(ListArray::try_new(
                    Arc::clone(field),
                    OffsetBuffer::new(ScalarBuffer::from(offsets)),
                    items,
                    nulls,
                )?)
            } else {
                let offsets = offsets
                    .into_iter()
                    .map(|offset| i64::try_from(offset).unwrap_or(i64::MAX))
                    .collect::<Vec<_>>();
                Arc::new(LargeListArray::try_new(
                    Arc::clone(field),
                    OffsetBuffer::new(ScalarBuffer::from(offsets)),
                    items,
                    nulls,
                )?)
            }
        }
        ArrowDataType::Union(fields, _) => build_union(fields, columns, path, count)?,
        other => return Err(DecodeError::UnsupportedDatatype(other.clone())),
    };

    Ok(array)
}

fn build_union(
    fields: &UnionFields,
    columns: &dyn ColumnSource,
    path: &str,
    count: usize,
) -> Result<ArrayRef, DecodeError> {
    let variants =
        columns
            .longs(&format!("{path}/.variant"))
            .ok_or_else(|| DecodeError::Columns {
                path: path.to_owned(),
                message: "missing /.variant".to_owned(),
            })?;

    // The type-definition fields are the datatype's children in order, with Rerun's
    // `_null_markers` child (always first) representing null instances. Java writes ordinals over
    // the type-definition fields, so map ordinal -> union child index -> Arrow type id.
    let mut null_child = None;
    let mut payload_children = Vec::with_capacity(fields.len());
    for (index, (type_id, field)) in fields.iter().enumerate() {
        if field.data_type() == &ArrowDataType::Null {
            null_child = Some((index, type_id));
        } else {
            payload_children.push((index, type_id, field));
        }
    }

    let mut per_child_counts = vec![0usize; fields.len()];
    let mut child_index_for_instance = Vec::with_capacity(count);
    for index in 0..count {
        let ordinal = variants.get(index).copied().unwrap_or(-1);
        let child = if ordinal < 0 {
            null_child.map(|(index, _)| index)
        } else {
            payload_children
                .get(ordinal as usize)
                .map(|(index, _, _)| *index)
        };
        let Some(child) = child else {
            return Err(DecodeError::Columns {
                path: path.to_owned(),
                message: format!("union variant {ordinal} does not exist in the datatype"),
            });
        };
        per_child_counts[child] += 1;
        child_index_for_instance.push(child);
    }

    let mut children: Vec<ArrayRef> = Vec::with_capacity(fields.len());
    let mut offsets_per_child = vec![0i32; fields.len()];
    for (index, (_, field)) in fields.iter().enumerate() {
        let child_count = per_child_counts[index];
        let child = if field.data_type() == &ArrowDataType::Null {
            Arc::new(NullArray::new(child_count)) as ArrayRef
        } else {
            build_column(
                field.data_type(),
                columns,
                &format!("{path}/{}", field.name()),
                child_count,
            )?
        };
        children.push(child);
    }

    let mut type_ids = Vec::with_capacity(count);
    let mut offsets = Vec::with_capacity(count);
    let mut type_id_for_child = vec![0i8; fields.len()];
    for (index, (type_id, _)) in fields.iter().enumerate() {
        type_id_for_child[index] = type_id;
    }
    for child_index in child_index_for_instance {
        type_ids.push(type_id_for_child[child_index]);
        offsets.push(offsets_per_child[child_index]);
        offsets_per_child[child_index] += 1;
    }

    Ok(Arc::new(UnionArray::try_new(
        fields.clone(),
        ScalarBuffer::from(type_ids),
        Some(ScalarBuffer::from(offsets)),
        children,
    )?))
}

/// Decodes a topic name under `/Rerun` into an [`EntityPath`].
///
/// Segments are percent-decoded as UTF-8; `/` only ever separates segments. Returns `None` for
/// topics outside the root, empty paths, and invalid escapes.
#[must_use]
pub fn entity_path_from_topic(topic: &str) -> Option<EntityPath> {
    let rest = topic.strip_prefix("/Rerun")?;
    let rest = rest.strip_prefix('/')?;
    if rest.is_empty() {
        return None;
    }
    let mut parts = Vec::new();
    for segment in rest.split('/') {
        if segment.is_empty() {
            return None;
        }
        parts.push(percent_decode(segment)?);
    }
    Some(EntityPath::from(parts.join("/")))
}

/// Percent-decodes one path segment, leaving unreserved ASCII untouched.
#[must_use]
pub fn percent_decode(segment: &str) -> Option<String> {
    let bytes = segment.as_bytes();
    let mut out = Vec::with_capacity(bytes.len());
    let mut index = 0;
    while index < bytes.len() {
        if bytes[index] == b'%' {
            let high = hex_digit(*bytes.get(index + 1)?)?;
            let low = hex_digit(*bytes.get(index + 2)?)?;
            out.push((high << 4) | low);
            index += 3;
        } else {
            out.push(bytes[index]);
            index += 1;
        }
    }
    String::from_utf8(out).ok()
}

fn hex_digit(byte: u8) -> Option<u8> {
    match byte {
        b'0'..=b'9' => Some(byte - b'0'),
        b'a'..=b'f' => Some(byte - b'a' + 10),
        b'A'..=b'F' => Some(byte - b'A' + 10),
        _ => None,
    }
}

#[cfg(test)]
mod tests {
    use super::{
        EntityMeta, NtValue, build_column, decode_entity, entity_path_from_topic, percent_decode,
    };
    use arrow::array::{Array, AsArray};
    use arrow::datatypes::{DataType, Field, UnionFields, UnionMode};
    use std::collections::HashMap;

    fn columns(entries: &[(&str, NtValue)]) -> HashMap<String, NtValue> {
        entries
            .iter()
            .map(|(path, value)| ((*path).to_owned(), value.clone()))
            .collect()
    }

    fn meta(components: &[(&str, &str)]) -> EntityMeta {
        EntityMeta {
            archetype: Some("rerun.archetypes.Points3D".to_owned()),
            components: components
                .iter()
                .map(|(field, ty)| ((*field).to_owned(), (*ty).to_owned()))
                .collect(),
            static_: false,
        }
    }

    #[test]
    fn decodes_fixed_size_lists_of_floats() {
        let meta = meta(&[("positions", "rerun.components.Position3D")]);
        let columns = columns(&[
            ("positions/.present", NtValue::Booleans(vec![true, false])),
            ("positions/0", NtValue::Doubles(vec![1.0, 2.0])),
            ("positions/1", NtValue::Doubles(vec![3.0, 4.0])),
            ("positions/2", NtValue::Doubles(vec![5.0, 6.0])),
        ]);

        let batches = decode_entity(&meta, &columns).unwrap();
        assert_eq!(batches.len(), 1);
        let array = batches[0].array.as_ref();
        assert!(matches!(array.data_type(), DataType::FixedSizeList(_, 3)));
        assert_eq!(array.len(), 2);
        assert!(array.is_valid(0));
        assert!(array.is_null(1));
        let descriptor = &batches[0].descriptor;
        assert_eq!(descriptor.component.as_str(), "Points3D:positions");
        assert_eq!(
            descriptor.archetype.as_ref().unwrap().as_str(),
            "rerun.archetypes.Points3D"
        );
    }

    #[test]
    fn decodes_a_nullable_utf8_leaf() {
        let meta = meta(&[("labels", "rerun.components.Text")]);
        let columns = columns(&[
            ("labels/.present", NtValue::Booleans(vec![true, false])),
            (
                "labels",
                NtValue::Strings(vec!["hello".to_owned(), "ignored".to_owned()]),
            ),
        ]);

        let batches = decode_entity(&meta, &columns).unwrap();
        let array = batches[0].array.as_string::<i32>();
        assert_eq!(array.value(0), "hello");
        assert!(array.is_null(1));
    }

    #[test]
    fn decodes_lists_of_fixed_size_lists() {
        let meta = meta(&[("strips", "rerun.components.LineStrip3D")]);
        let columns = columns(&[
            ("strips/.count", NtValue::Longs(vec![2, 1])),
            ("strips/.items/0", NtValue::Doubles(vec![1.0, 2.0, 3.0])),
            ("strips/.items/1", NtValue::Doubles(vec![4.0, 5.0, 6.0])),
            ("strips/.items/2", NtValue::Doubles(vec![7.0, 8.0, 9.0])),
        ]);

        let batches = decode_entity(&meta, &columns).unwrap();
        let array = batches[0].array.as_list::<i32>();
        assert_eq!(array.len(), 2);
        assert_eq!(array.value_length(0), 2);
        assert_eq!(array.value_length(1), 1);
        assert_eq!(array.value(0).len(), 2);
    }

    #[test]
    fn decodes_structs_lists_and_unions() {
        // `TensorData` exercises a struct root with a `List<UInt64>`, a `TensorBuffer` union and a
        // `List<Utf8>` field, all in one reflection-driven decode.
        let meta = meta(&[("tensor", "rerun.components.TensorData")]);
        let columns = columns(&[
            ("tensor/shape/.count", NtValue::Longs(vec![2])),
            ("tensor/shape/.items", NtValue::Longs(vec![2, 3])),
            ("tensor/buffer/.variant", NtValue::Longs(vec![0])),
            ("tensor/buffer/U8/.count", NtValue::Longs(vec![4])),
            ("tensor/buffer/U8/.items", NtValue::Longs(vec![1, 2, 3, 4])),
            ("tensor/names/.count", NtValue::Longs(vec![1])),
            (
                "tensor/names/.items",
                NtValue::Strings(vec!["x".to_owned()]),
            ),
        ]);

        let batches = decode_entity(&meta, &columns).unwrap();
        let array = batches[0].array.as_struct();
        assert_eq!(array.len(), 1);
        let shape = array.column(0).as_list::<i32>();
        assert_eq!(shape.len(), 1);
        assert_eq!(shape.value_length(0), 2);

        let buffer = array.column(2).as_union();
        assert_eq!(buffer.len(), 1);
        assert_eq!(buffer.type_id(0), 1, "U8 is union child 1");
        assert_eq!(
            buffer.child(1).as_list::<i32>().value_length(0),
            4,
            "U8 payload has four bytes"
        );

        let names = array.column(1).as_list::<i32>();
        assert_eq!(names.value_length(0), 1);
    }

    #[test]
    fn decodes_dense_unions_using_arrow_type_ids() {
        let fields = UnionFields::try_new(
            vec![0, 1, 2],
            vec![
                Field::new("_null_markers", DataType::Null, true),
                Field::new("CursorRelative", DataType::Int64, false),
                Field::new("Absolute", DataType::Int64, false),
            ],
        )
        .unwrap();
        let datatype = DataType::Union(fields, UnionMode::Dense);
        let columns = columns(&[
            ("u/.variant", NtValue::Longs(vec![0, 1, -1])),
            ("u/CursorRelative", NtValue::Longs(vec![10, 20])),
            ("u/Absolute", NtValue::Longs(vec![30])),
        ]);

        let array = build_column(&datatype, &columns, "u", 3).unwrap();
        let union = array.as_union();
        assert_eq!(
            (0..3).map(|i| union.type_id(i)).collect::<Vec<_>>(),
            [1, 2, 0]
        );
        assert_eq!(
            (0..3).map(|i| union.value_offset(i)).collect::<Vec<_>>(),
            [0, 0, 0]
        );
        assert_eq!(union.child(0).len(), 1, "one null marker value");
    }

    #[test]
    fn decodes_binary_columns() {
        let datatype = DataType::Binary;
        let cols = columns(&[
            ("blob", NtValue::Raw(b"abcdef".to_vec())),
            ("blob/.lengths", NtValue::Longs(vec![2, 4])),
        ]);
        let array = build_column(&datatype, &cols, "blob", 2).unwrap();
        let array = array.as_binary::<i32>();
        assert_eq!(array.value(0), b"ab");
        assert_eq!(array.value(1), b"cdef");

        let cols = columns(&[
            ("blob/.present", NtValue::Booleans(vec![true, false])),
            ("blob", NtValue::Raw(b"ab".to_vec())),
            ("blob/.lengths", NtValue::Longs(vec![2, 0])),
        ]);
        let array = build_column(&datatype, &cols, "blob", 2).unwrap();
        assert!(array.as_binary::<i32>().is_null(1));
    }

    #[test]
    fn decodes_the_u64_string_fallback() {
        let meta = meta(&[("value", "rerun.components.Scalar")]);
        // `Scalar` is an f64, so use the raw datatype for the u64 path instead.
        let datatype = DataType::UInt64;
        let cols = columns(&[
            ("value", NtValue::Longs(vec![-1, 7])),
            (
                "value/.u64",
                NtValue::Strings(vec![u64::MAX.to_string(), "7".to_owned()]),
            ),
        ]);
        let array = build_column(&datatype, &cols, "value", 2).unwrap();
        let array = array.as_primitive::<arrow::datatypes::UInt64Type>();
        assert_eq!(array.value(0), u64::MAX);
        assert_eq!(array.value(1), 7);

        let cols = columns(&[("value", NtValue::Longs(vec![-1, 7]))]);
        let array = build_column(&datatype, &cols, "value", 2).unwrap();
        let array = array.as_primitive::<arrow::datatypes::UInt64Type>();
        assert_eq!(array.value(0), u64::MAX);

        let _ = meta;
    }

    #[test]
    fn skips_components_without_columns() {
        let meta = meta(&[
            ("positions", "rerun.components.Position3D"),
            ("colors", "rerun.components.Color"),
        ]);
        let columns = columns(&[
            ("positions/0", NtValue::Doubles(vec![1.0])),
            ("positions/1", NtValue::Doubles(vec![2.0])),
            ("positions/2", NtValue::Doubles(vec![3.0])),
        ]);
        let batches = decode_entity(&meta, &columns).unwrap();
        assert_eq!(batches.len(), 1);
        assert_eq!(
            batches[0].descriptor.component.as_str(),
            "Points3D:positions"
        );
    }

    #[test]
    fn reports_unknown_component_types() {
        let meta = meta(&[("value", "my.custom.Component")]);
        let error = decode_entity(&meta, &columns(&[])).unwrap_err();
        assert!(error.to_string().contains("unknown component type"));
    }

    #[test]
    fn decodes_escaped_entity_paths() {
        let path = entity_path_from_topic("/Rerun/world/my%20robot%2Ev2/h%C3%A9llo").unwrap();
        assert_eq!(
            path.iter()
                .map(|part| part.unescaped_str())
                .collect::<Vec<_>>(),
            ["world", "my robot.v2", "héllo"]
        );
        assert!(entity_path_from_topic("/Rerun").is_none());
        assert!(entity_path_from_topic("/Other/x").is_none());
        assert!(entity_path_from_topic("/Rerun/a%2").is_none());
        assert_eq!(percent_decode("a%25b").unwrap(), "a%b");
        assert!(percent_decode("%FF").is_none());
    }
}
