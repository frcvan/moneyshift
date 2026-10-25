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
//! | `UInt64` | `P` = `raw`, eight little-endian bytes per instance |
//! | floats | `P` = `double[]` |
//! | `Utf8` | `P` = `string[]` |
//! | `Binary` | `P` = `raw`, `P/.lengths` = `long[]` |
//! | `FixedSizeList(n, T)` | recurse at `P/0` … `P/{n-1}` |
//! | `Struct` | recurse at `P/<field name>` |
//! | `List` | `P/.offsets` = `long[]` prefix sums, flattened elements at `P/.items` |
//! | `Union` | `P/.variant` = `long[]` field ordinals (`-1` null), payloads at `P/<variant name>` |
//! | nullable node | `P/.present` = `boolean[]`, only once a null has been seen |
//!
//! Lists and unions deviate from a naive reading of the table in one way each: both are
//! *variable-length* per component instance, so the offset/ordinal columns are per-instance arrays
//! and their payloads are flattened. That keeps the topic count bounded by the datatype while
//! letting one batch hold lists of different lengths.

use std::collections::HashMap;
use std::sync::Arc;

use arrow::array::{
    Array, ArrayRef, BinaryArray, BooleanArray, Float32Array, Float64Array, Int8Array, Int16Array,
    Int32Array, Int64Array, LargeBinaryArray, LargeListArray, LargeStringBuilder, ListArray,
    NullArray, StringBuilder, StructArray, UInt8Array, UInt16Array, UInt32Array, UInt64Array,
    UnionArray,
};
use arrow::buffer::{Buffer, OffsetBuffer, ScalarBuffer};
use arrow::datatypes::{DataType as ArrowDataType, UnionFields};
use arrow::error::ArrowError;
use re_log_types::EntityPath;
use re_sdk_types::reflection::reflection;
use re_types_core::{
    ArchetypeName, ComponentDescriptor, ComponentIdentifier, ComponentType, DeserializationResult,
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

/// Read access to the cached NetworkTables columns of one entity.
pub trait ColumnSource {
    /// Calls `f` once per published column with its path relative to the entity and its value.
    ///
    /// Decoding walks the plan's column trie once per published column instead of building (and
    /// hashing) one path string per datatype node per flush.
    fn for_each_column<'a>(&'a self, f: &mut dyn FnMut(&'a str, &'a NtValue));
}

impl ColumnSource for HashMap<String, NtValue> {
    fn for_each_column<'a>(&'a self, f: &mut dyn FnMut(&'a str, &'a NtValue)) {
        for (path, value) in self {
            f(path, value);
        }
    }
}

impl NtValue {
    #[must_use]
    pub fn longs(&self) -> Option<&[i64]> {
        match self {
            Self::Longs(values) => Some(values),
            _ => None,
        }
    }

    #[must_use]
    pub fn doubles(&self) -> Option<&[f64]> {
        match self {
            Self::Doubles(values) => Some(values),
            _ => None,
        }
    }

    #[must_use]
    pub fn booleans(&self) -> Option<&[bool]> {
        match self {
            Self::Booleans(values) => Some(values),
            _ => None,
        }
    }

    #[must_use]
    pub fn strings(&self) -> Option<&[String]> {
        match self {
            Self::Strings(values) => Some(values),
            _ => None,
        }
    }

    #[must_use]
    pub fn raw(&self) -> Option<&[u8]> {
        match self {
            Self::Raw(bytes) => Some(bytes),
            _ => None,
        }
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

/// One topic path segment of a component's wire layout.
///
/// The layout is a trie mirroring the Arrow datatype. Resolving the published columns into the
/// trie once per flush replaces one path `String` build and hash per datatype node, which used to
/// dominate small-batch decoding.
#[derive(Clone, Debug)]
struct WireNode {
    /// This node's segment below its parent: `positions`, `0`, `.present`, `.items`, …
    segment: Box<str>,
    /// The full column path, used in diagnostics only.
    path: Box<str>,
    datatype: ArrowDataType,
    /// Child nodes in wire order, including the auxiliary columns described below.
    children: Vec<usize>,
    /// The `.present` null mask, when this node can be null.
    present: Option<usize>,
    /// The auxiliary column of this node: `.lengths` (binary), `.offsets` (list), `.variant` (union).
    aux: Option<usize>,
}

/// One component of an entity, resolved once per metadata change.
///
/// Reflection lookups, identifier construction and the column trie are all built here, so a
/// steady-state flush only resolves the published columns into the trie and walks it.
#[derive(Clone, Debug)]
pub struct ComponentPlan {
    field: String,
    descriptor: ComponentDescriptor,
    verify: fn(&dyn Array) -> DeserializationResult<()>,
    /// Node 0 is the component root; every other node is reachable from it.
    nodes: Vec<WireNode>,
}

impl ComponentPlan {
    fn new(
        field: String,
        descriptor: ComponentDescriptor,
        datatype: ArrowDataType,
        verify: fn(&dyn Array) -> DeserializationResult<()>,
    ) -> Self {
        let mut plan = Self {
            field: String::new(),
            descriptor,
            verify,
            nodes: Vec::new(),
        };
        plan.add_node("", &field, &datatype);
        plan.field = field;
        plan.populate(0);
        plan
    }

    fn add_node(&mut self, parent_path: &str, segment: &str, datatype: &ArrowDataType) -> usize {
        let path = if parent_path.is_empty() {
            segment.to_owned()
        } else {
            format!("{parent_path}/{segment}")
        };
        self.nodes.push(WireNode {
            segment: segment.into(),
            path: path.into_boxed_str(),
            datatype: datatype.clone(),
            children: Vec::new(),
            present: None,
            aux: None,
        });
        self.nodes.len() - 1
    }

    fn add_child(&mut self, parent: usize, segment: &str, datatype: &ArrowDataType) -> usize {
        let parent_path = self.nodes[parent].path.to_string();
        let child = self.add_node(&parent_path, segment, datatype);
        self.nodes[parent].children.push(child);
        child
    }

    /// Fills in the children of `id` from its datatype.
    fn populate(&mut self, id: usize) {
        let present = self.add_child(id, ".present", &ArrowDataType::Boolean);
        self.nodes[id].present = Some(present);

        let datatype = self.nodes[id].datatype.clone();
        match datatype {
            ArrowDataType::Null
            | ArrowDataType::Boolean
            | ArrowDataType::Int8
            | ArrowDataType::Int16
            | ArrowDataType::Int32
            | ArrowDataType::Int64
            | ArrowDataType::UInt8
            | ArrowDataType::UInt16
            | ArrowDataType::UInt32
            | ArrowDataType::UInt64
            | ArrowDataType::Float16
            | ArrowDataType::Float32
            | ArrowDataType::Float64
            | ArrowDataType::Utf8
            | ArrowDataType::LargeUtf8 => {}
            ArrowDataType::Binary | ArrowDataType::LargeBinary => {
                let aux = self.add_child(id, ".lengths", &ArrowDataType::Int64);
                self.nodes[id].aux = Some(aux);
            }
            ArrowDataType::FixedSizeList(field, size) => {
                for index in 0..size {
                    let child = self.add_child(id, &index.to_string(), field.data_type());
                    self.populate(child);
                }
            }
            ArrowDataType::Struct(fields) => {
                for field in &fields {
                    let child = self.add_child(id, field.name(), field.data_type());
                    self.populate(child);
                }
            }
            ArrowDataType::List(field) | ArrowDataType::LargeList(field) => {
                let aux = self.add_child(id, ".offsets", &ArrowDataType::Int64);
                self.nodes[id].aux = Some(aux);
                let items = self.add_child(id, ".items", field.data_type());
                self.populate(items);
            }
            ArrowDataType::Union(fields, _) => {
                let aux = self.add_child(id, ".variant", &ArrowDataType::Int64);
                self.nodes[id].aux = Some(aux);
                for (_, field) in fields.iter() {
                    let child = self.add_child(id, field.name(), field.data_type());
                    self.populate(child);
                }
            }
            // Unsupported datatypes stay childless; decoding reports them.
            _ => {}
        }
    }

    fn child(&self, id: usize, segment: &str) -> Option<usize> {
        self.nodes[id]
            .children
            .iter()
            .copied()
            .find(|child| &*self.nodes[*child].segment == segment)
    }

    fn data_children(&self, id: usize) -> impl Iterator<Item = usize> + '_ {
        let node = &self.nodes[id];
        node.children
            .iter()
            .copied()
            .filter(move |child| Some(*child) != node.present && Some(*child) != node.aux)
    }

    /// The node's null mask, with a warning when its length disagrees with the instance count.
    fn null_mask(
        &self,
        slots: &[Option<&NtValue>],
        id: usize,
        count: usize,
        warnings: &mut Vec<String>,
    ) -> Option<arrow::buffer::NullBuffer> {
        let present = self.nodes[id].present?;
        let mask = slots.get(present).copied().flatten()?.booleans()?;
        if mask.len() != count {
            warnings.push(format!(
                "{} has {} entries, expected {count}",
                self.nodes[present].path,
                mask.len()
            ));
        }
        if mask.iter().all(|valid| *valid) {
            return None;
        }
        let mut bits = mask.to_vec();
        // A short mask must not make the null lookups below panic.
        bits.resize(count, true);
        Some(arrow::buffer::NullBuffer::from(bits))
    }

    /// How many instances this node holds.
    fn infer_count(&self, slots: &[Option<&NtValue>], id: usize) -> Result<usize, DecodeError> {
        let node = &self.nodes[id];
        if let Some(present) = node.present
            && let Some(mask) = slots
                .get(present)
                .copied()
                .flatten()
                .and_then(NtValue::booleans)
        {
            return Ok(mask.len());
        }
        let column = || slots.get(id).copied().flatten();

        Ok(match &node.datatype {
            ArrowDataType::Null => 0,
            ArrowDataType::Boolean => column()
                .and_then(NtValue::booleans)
                .map_or(0, <[bool]>::len),
            ArrowDataType::Int8
            | ArrowDataType::Int16
            | ArrowDataType::Int32
            | ArrowDataType::Int64
            | ArrowDataType::UInt8
            | ArrowDataType::UInt16
            | ArrowDataType::UInt32 => column().and_then(NtValue::longs).map_or(0, <[i64]>::len),
            // Unsigned 64-bit values are published as eight little-endian bytes per instance,
            // which keeps the full range without a decimal sidecar topic.
            ArrowDataType::UInt64 => column()
                .and_then(NtValue::raw)
                .map_or(0, |bytes| bytes.len() / 8),
            ArrowDataType::Float16 | ArrowDataType::Float32 | ArrowDataType::Float64 => {
                column().and_then(NtValue::doubles).map_or(0, <[f64]>::len)
            }
            ArrowDataType::Utf8 | ArrowDataType::LargeUtf8 => column()
                .and_then(NtValue::strings)
                .map_or(0, <[String]>::len),
            ArrowDataType::Binary | ArrowDataType::LargeBinary => node
                .aux
                .and_then(|aux| slots.get(aux).copied().flatten())
                .and_then(NtValue::longs)
                .map_or(0, <[i64]>::len),
            ArrowDataType::FixedSizeList(..) => match self.data_children(id).next() {
                Some(child) => self.infer_count(slots, child)?,
                None => 0,
            },
            ArrowDataType::Struct(_) => {
                // Any published child gives the instance count. A leading null-typed or
                // unpublished field must not hide the whole component.
                let mut count = 0;
                for child in self.data_children(id) {
                    let candidate = self.infer_count(slots, child)?;
                    if candidate > 0 {
                        count = candidate;
                        break;
                    }
                }
                count
            }
            // A list publishes the prefix sums of its element counts, one more offset than
            // instances.
            ArrowDataType::List(_) | ArrowDataType::LargeList(_) => node
                .aux
                .and_then(|aux| slots.get(aux).copied().flatten())
                .and_then(NtValue::longs)
                .map_or(0, |offsets| offsets.len().saturating_sub(1)),
            ArrowDataType::Union(..) => node
                .aux
                .and_then(|aux| slots.get(aux).copied().flatten())
                .and_then(NtValue::longs)
                .map_or(0, <[i64]>::len),
            other => return Err(DecodeError::UnsupportedDatatype(other.clone())),
        })
    }

    /// Recursively reconstructs one Arrow array from the resolved columns.
    fn build(
        &self,
        id: usize,
        count: usize,
        slots: &[Option<&NtValue>],
        warnings: &mut Vec<String>,
    ) -> Result<ArrayRef, DecodeError> {
        let node = &self.nodes[id];
        if count == 0 {
            return Ok(arrow::array::new_empty_array(&node.datatype));
        }
        let path = node.path.as_ref();
        let nulls = self.null_mask(slots, id, count, warnings);
        let column = slots.get(id).copied().flatten();

        let array: ArrayRef = match &node.datatype {
            ArrowDataType::Null => Arc::new(NullArray::new(count)),
            ArrowDataType::Boolean => {
                let values = column.and_then(NtValue::booleans);
                check_len(values.map(<[bool]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                match &nulls {
                    None => Arc::new(BooleanArray::from_iter(
                        (0..count).map(|i| Some(value_at(values, i, false))),
                    )),
                    Some(nulls) => Arc::new(
                        (0..count)
                            .map(|i| nulls.is_valid(i).then(|| value_at(values, i, false)))
                            .collect::<BooleanArray>(),
                    ),
                }
            }
            ArrowDataType::Int8
            | ArrowDataType::Int16
            | ArrowDataType::Int32
            | ArrowDataType::Int64 => {
                let values = column.and_then(NtValue::longs);
                check_len(values.map(<[i64]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                macro_rules! build_int {
                    ($array:ty, $ty:ty) => {
                        match &nulls {
                            // Fast path: without nulls there is no null buffer and no per-value
                            // `Option`, which matters for large columns (point clouds, images).
                            None => Arc::new(<$array>::from_iter_values(
                                (0..count).map(|i| value_at(values, i, 0) as $ty),
                            )) as ArrayRef,
                            Some(nulls) => Arc::new(
                                (0..count)
                                    .map(|i| {
                                        nulls.is_valid(i).then(|| value_at(values, i, 0) as $ty)
                                    })
                                    .collect::<$array>(),
                            ) as ArrayRef,
                        }
                    };
                }
                match node.datatype {
                    ArrowDataType::Int8 => build_int!(Int8Array, i8),
                    ArrowDataType::Int16 => build_int!(Int16Array, i16),
                    ArrowDataType::Int32 => build_int!(Int32Array, i32),
                    _ => build_int!(Int64Array, i64),
                }
            }
            ArrowDataType::UInt8 | ArrowDataType::UInt16 | ArrowDataType::UInt32 => {
                let values = column.and_then(NtValue::longs);
                check_len(values.map(<[i64]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                macro_rules! build_uint {
                    ($array:ty, $ty:ty) => {
                        match &nulls {
                            None => Arc::new(<$array>::from_iter_values(
                                (0..count).map(|i| value_at(values, i, 0) as $ty),
                            )) as ArrayRef,
                            Some(nulls) => Arc::new(
                                (0..count)
                                    .map(|i| {
                                        nulls.is_valid(i).then(|| value_at(values, i, 0) as $ty)
                                    })
                                    .collect::<$array>(),
                            ) as ArrayRef,
                        }
                    };
                }
                match node.datatype {
                    ArrowDataType::UInt8 => build_uint!(UInt8Array, u8),
                    ArrowDataType::UInt16 => build_uint!(UInt16Array, u16),
                    _ => build_uint!(UInt32Array, u32),
                }
            }
            ArrowDataType::UInt64 => {
                let bytes = column.and_then(NtValue::raw);
                check_len(bytes.map(|bytes| bytes.len() / 8), count, path, warnings);
                let bytes = bytes.unwrap_or(&[]);
                let value = |index: usize| -> u64 {
                    let start = index * 8;
                    match bytes.get(start..start + 8) {
                        Some(chunk) => u64::from_le_bytes(chunk.try_into().expect("8 bytes")),
                        None => 0,
                    }
                };
                match &nulls {
                    None => Arc::new(UInt64Array::from_iter_values((0..count).map(value))),
                    Some(nulls) => Arc::new(
                        (0..count)
                            .map(|i| nulls.is_valid(i).then(|| value(i)))
                            .collect::<UInt64Array>(),
                    ),
                }
            }
            ArrowDataType::Float16 => {
                let values = column.and_then(NtValue::doubles);
                check_len(values.map(<[f64]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                let singles: Float32Array = match &nulls {
                    None => Float32Array::from_iter_values(
                        (0..count).map(|i| value_at(values, i, 0.0) as f32),
                    ),
                    Some(nulls) => (0..count)
                        .map(|i| nulls.is_valid(i).then(|| value_at(values, i, 0.0) as f32))
                        .collect(),
                };
                arrow::compute::cast(&singles, &node.datatype)?
            }
            ArrowDataType::Float32 | ArrowDataType::Float64 => {
                let values = column.and_then(NtValue::doubles);
                check_len(values.map(<[f64]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                match (&nulls, node.datatype == ArrowDataType::Float32) {
                    (None, true) => Arc::new(Float32Array::from_iter_values(
                        (0..count).map(|i| value_at(values, i, 0.0) as f32),
                    )),
                    (None, false) => Arc::new(Float64Array::from_iter_values(
                        (0..count).map(|i| value_at(values, i, 0.0)),
                    )),
                    (Some(nulls), true) => Arc::new(
                        (0..count)
                            .map(|i| nulls.is_valid(i).then(|| value_at(values, i, 0.0) as f32))
                            .collect::<Float32Array>(),
                    ),
                    (Some(nulls), false) => Arc::new(
                        (0..count)
                            .map(|i| nulls.is_valid(i).then(|| value_at(values, i, 0.0)))
                            .collect::<Float64Array>(),
                    ),
                }
            }
            ArrowDataType::Utf8 | ArrowDataType::LargeUtf8 => {
                let values = column.and_then(NtValue::strings);
                check_len(values.map(<[String]>::len), count, path, warnings);
                let values = values.unwrap_or(&[]);
                // Appending the borrowed strings copies them once into the Arrow buffer instead
                // of allocating an owned copy of every value first.
                let bytes = values.iter().take(count).map(String::len).sum::<usize>();
                let is_large = node.datatype == ArrowDataType::LargeUtf8;
                macro_rules! build_string {
                    ($builder:ty) => {{
                        let mut builder = <$builder>::with_capacity(count, bytes);
                        for index in 0..count {
                            if nulls.as_ref().is_some_and(|nulls| !nulls.is_valid(index)) {
                                builder.append_null();
                            } else {
                                builder.append_value(values.get(index).map_or("", String::as_str));
                            }
                        }
                        Arc::new(builder.finish()) as ArrayRef
                    }};
                }
                if is_large {
                    build_string!(LargeStringBuilder)
                } else {
                    build_string!(StringBuilder)
                }
            }
            ArrowDataType::Binary | ArrowDataType::LargeBinary => {
                let lengths = node
                    .aux
                    .and_then(|aux| slots.get(aux).copied().flatten())
                    .and_then(NtValue::longs);
                check_len(lengths.map(<[i64]>::len), count, path, warnings);
                let lengths = lengths.unwrap_or(&[]);
                let bytes = column.and_then(NtValue::raw).unwrap_or(&[]);

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
                if node.datatype == ArrowDataType::Binary {
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
                for child in self.data_children(id) {
                    children.push(self.build(child, count, slots, warnings)?);
                }
                // Rerun stores fixed-size lists contiguously per element (`x0, y0, z0, x1, …`),
                // while the wire format publishes one column per component (`x0, x1, …`).
                // Concatenating the columns is therefore only correct for single-component
                // lists; the rest have to be interleaved. Interleaving straight from the
                // per-column arrays avoids materializing the concatenated copy first.
                let values = if size == 0 {
                    arrow::array::new_empty_array(field.data_type())
                } else if size == 1 {
                    children[0].clone()
                } else {
                    let refs = children.iter().map(AsRef::as_ref).collect::<Vec<_>>();
                    let mut indices = Vec::with_capacity(size * count);
                    for instance in 0..count {
                        for component in 0..size {
                            indices.push((component, instance));
                        }
                    }
                    arrow::compute::interleave(&refs, &indices)?
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
                for child in self.data_children(id) {
                    children.push(self.build(child, count, slots, warnings)?);
                }
                Arc::new(StructArray::try_new(fields.clone(), children, nulls)?)
            }
            ArrowDataType::List(field) | ArrowDataType::LargeList(field) => {
                let raw_offsets = node
                    .aux
                    .and_then(|aux| slots.get(aux).copied().flatten())
                    .and_then(NtValue::longs)
                    .ok_or_else(|| DecodeError::Columns {
                        path: format!("{path}/.offsets"),
                        message: "missing offsets".to_owned(),
                    })?;
                if raw_offsets.len() != count + 1 {
                    warnings.push(format!(
                        "{path}/.offsets has {} entries, expected {}",
                        raw_offsets.len(),
                        count + 1
                    ));
                }
                let mut offsets = Vec::with_capacity(count + 1);
                let mut previous = 0i64;
                for index in 0..=count {
                    let offset = raw_offsets
                        .get(index)
                        .copied()
                        .unwrap_or(previous)
                        .max(previous);
                    offsets.push(offset);
                    previous = offset;
                }
                let total = offsets.last().copied().unwrap_or(0).max(0) as usize;

                let items = match self.data_children(id).next() {
                    Some(child) => self.build(child, total, slots, warnings)?,
                    None => arrow::array::new_empty_array(field.data_type()),
                };
                let items = if total == 0 {
                    arrow::array::new_empty_array(field.data_type())
                } else {
                    items
                };
                if matches!(&node.datatype, ArrowDataType::List(_)) {
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
                    Arc::new(LargeListArray::try_new(
                        Arc::clone(field),
                        OffsetBuffer::new(ScalarBuffer::from(offsets)),
                        items,
                        nulls,
                    )?)
                }
            }
            ArrowDataType::Union(fields, _) => {
                self.build_union(id, fields, count, slots, warnings)?
            }
            other => return Err(DecodeError::UnsupportedDatatype(other.clone())),
        };

        Ok(array)
    }

    fn build_union(
        &self,
        id: usize,
        fields: &UnionFields,
        count: usize,
        slots: &[Option<&NtValue>],
        warnings: &mut Vec<String>,
    ) -> Result<ArrayRef, DecodeError> {
        let path = self.nodes[id].path.as_ref();
        let variants = self.nodes[id]
            .aux
            .and_then(|aux| slots.get(aux).copied().flatten())
            .and_then(NtValue::longs)
            .ok_or_else(|| DecodeError::Columns {
                path: format!("{path}/.variant"),
                message: "missing variant ordinals".to_owned(),
            })?;
        check_len(Some(variants.len()), count, path, warnings);

        // The type-definition fields are the datatype's children in order, with Rerun's
        // `_null_markers` child (always first) representing null instances. Java writes ordinals
        // over the type-definition fields, so map ordinal -> union child index -> Arrow type id.
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
        // The payload nodes are the data children in datatype order, so they line up with the
        // non-null fields of the union.
        let payload_nodes = self
            .data_children(id)
            .filter(|child| self.nodes[*child].datatype != ArrowDataType::Null)
            .collect::<Vec<_>>();
        let mut payload_index = 0usize;
        for (index, (_, field)) in fields.iter().enumerate() {
            let child_count = per_child_counts[index];
            let child = if field.data_type() == &ArrowDataType::Null {
                Arc::new(NullArray::new(child_count)) as ArrayRef
            } else {
                match payload_nodes.get(payload_index) {
                    Some(&child) => {
                        payload_index += 1;
                        self.build(child, child_count, slots, warnings)?
                    }
                    None => Arc::new(arrow::array::new_empty_array(field.data_type())) as ArrayRef,
                }
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
}

/// Every component of one entity, in archetype field order.
#[derive(Clone, Debug, Default)]
pub struct EntityPlan {
    components: Vec<ComponentPlan>,
}

impl EntityPlan {
    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.components.is_empty()
    }

    #[must_use]
    pub fn len(&self) -> usize {
        self.components.len()
    }
}

/// Resolves `meta` into the reflection data needed to decode it.
pub fn plan_entity(meta: &EntityMeta) -> Result<EntityPlan, DecodeError> {
    let reflection = reflection();
    let archetype = meta
        .archetype
        .as_deref()
        .filter(|name| !name.is_empty())
        .and_then(|name| ArchetypeName::try_new(name).ok());

    let mut components = Vec::with_capacity(meta.components.len());
    for (field, component_type) in &meta.components {
        let Ok(component_type_id) = ComponentType::try_new(component_type.as_str()) else {
            return Err(DecodeError::UnknownComponent(component_type.clone()));
        };
        let Some(component) = reflection.components.get(&component_type_id) else {
            return Err(DecodeError::UnknownComponent(component_type.clone()));
        };

        let identifier = match archetype {
            Some(archetype_name) => {
                ComponentIdentifier::from_archetype_field(archetype_name, field)
            }
            None => {
                ComponentIdentifier::try_new(field.as_str()).expect("column roots are non-empty")
            }
        };

        components.push(ComponentPlan::new(
            field.clone(),
            ComponentDescriptor {
                archetype,
                component: identifier,
                component_type: Some(component_type_id),
            },
            component.datatype.clone(),
            component.verify_arrow_array,
        ));
    }

    Ok(EntityPlan { components })
}

/// Decodes an already planned entity.
///
/// Returns the batches plus one message per column whose length disagrees with the instance
/// count. The caller owns de-duplication of those messages; they are empty for healthy data.
pub fn decode_planned(
    plan: &EntityPlan,
    columns: &dyn ColumnSource,
) -> Result<(Vec<SerializedComponentBatch>, Vec<String>), DecodeError> {
    let mut batches = Vec::with_capacity(plan.components.len());
    let mut warnings = Vec::new();
    if plan.components.is_empty() {
        return Ok((batches, warnings));
    }

    let (slots, published) = resolve_columns(plan, columns);

    for (index, component) in plan.components.iter().enumerate() {
        if !published[index] {
            // The archetype did not log this component in this batch.
            continue;
        }
        let count = component.infer_count(&slots[index], 0)?;
        if count == 0 {
            continue;
        }
        let array = component.build(0, count, &slots[index], &mut warnings)?;

        if cfg!(debug_assertions)
            && array.null_count() == 0
            && let Err(error) = (component.verify)(array.as_ref())
        {
            return Err(DecodeError::Columns {
                path: component.field.clone(),
                message: format!("reflection rejected the decoded array: {error}"),
            });
        }

        batches.push(SerializedComponentBatch::new(
            array,
            component.descriptor.clone(),
        ));
    }

    Ok((batches, warnings))
}

/// Resolves every published column into its component's wire trie.
///
/// This is the only pass that touches the column source: a steady-state flush never builds or
/// hashes a path string.
fn resolve_columns<'a>(
    plan: &EntityPlan,
    columns: &'a dyn ColumnSource,
) -> (Vec<Vec<Option<&'a NtValue>>>, Vec<bool>) {
    let mut slots: Vec<Vec<Option<&NtValue>>> = plan
        .components
        .iter()
        .map(|component| vec![None; component.nodes.len()])
        .collect();
    let mut published = vec![false; plan.components.len()];
    columns.for_each_column(&mut |path, value| {
        let (root, rest) = path.split_once('/').unwrap_or((path, ""));
        for (index, component) in plan.components.iter().enumerate() {
            if component.field != root {
                continue;
            }
            let mut current = 0usize;
            let mut matched = true;
            if !rest.is_empty() {
                for segment in rest.split('/') {
                    match component.child(current, segment) {
                        Some(child) => current = child,
                        None => {
                            matched = false;
                            break;
                        }
                    }
                }
            }
            if matched {
                slots[index][current] = Some(value);
                published[index] = true;
            }
        }
    });
    (slots, published)
}

/// Decodes every component of `meta` from `columns` into Rerun component batches.
pub fn decode_entity(
    meta: &EntityMeta,
    columns: &dyn ColumnSource,
) -> Result<Vec<SerializedComponentBatch>, DecodeError> {
    let plan = plan_entity(meta)?;
    Ok(decode_planned(&plan, columns)?.0)
}

fn check_len(actual: Option<usize>, count: usize, path: &str, warnings: &mut Vec<String>) {
    if let Some(actual) = actual
        && actual != count
    {
        warnings.push(format!("{path} has {actual} values, expected {count}"));
    }
}

fn value_at<T: Copy>(values: &[T], index: usize, default: T) -> T {
    values.get(index).copied().unwrap_or(default)
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
    use super::{EntityMeta, NtValue, decode_entity, entity_path_from_topic, percent_decode};
    use arrow::array::{Array, AsArray};
    use arrow::datatypes::{DataType, Field, Float32Type, UnionFields, UnionMode};
    use std::collections::HashMap;

    fn columns(entries: &[(&str, NtValue)]) -> HashMap<String, NtValue> {
        entries
            .iter()
            .map(|(path, value)| ((*path).to_owned(), value.clone()))
            .collect()
    }

    /// Builds one standalone component from `datatype`, resolving `columns` into its trie.
    fn build(
        datatype: &DataType,
        columns: &dyn super::ColumnSource,
        path: &str,
        count: usize,
    ) -> Result<arrow::array::ArrayRef, super::DecodeError> {
        let descriptor = super::ComponentDescriptor {
            archetype: None,
            component: super::ComponentIdentifier::try_new(path).unwrap(),
            component_type: None,
        };
        let plan = super::EntityPlan {
            components: vec![super::ComponentPlan::new(
                path.to_owned(),
                descriptor,
                datatype.clone(),
                |_| Ok(()),
            )],
        };
        let (slots, _) = super::resolve_columns(&plan, columns);
        let mut warnings = Vec::new();
        plan.components[0].build(0, count, &slots[0], &mut warnings)
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

        // Fixed-size lists are stored contiguously per element, while the wire format publishes
        // one column per component.
        let array = array.as_fixed_size_list();
        let first = array.value(0);
        assert_eq!(
            first.as_primitive::<Float32Type>().values(),
            &[1.0, 3.0, 5.0]
        );
        let second = array.value(1);
        assert_eq!(
            second.as_primitive::<Float32Type>().values(),
            &[2.0, 4.0, 6.0]
        );

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
            ("strips/.offsets", NtValue::Longs(vec![0, 2, 3])),
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
        let mut shape_bytes = Vec::new();
        for value in [2u64, 3] {
            shape_bytes.extend_from_slice(&value.to_le_bytes());
        }
        let columns = columns(&[
            ("tensor/shape/.offsets", NtValue::Longs(vec![0, 2])),
            ("tensor/shape/.items", NtValue::Raw(shape_bytes)),
            ("tensor/buffer/.variant", NtValue::Longs(vec![0])),
            ("tensor/buffer/U8/.offsets", NtValue::Longs(vec![0, 4])),
            ("tensor/buffer/U8/.items", NtValue::Longs(vec![1, 2, 3, 4])),
            ("tensor/names/.offsets", NtValue::Longs(vec![0, 1])),
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

        let array = build(&datatype, &columns, "u", 3).unwrap();
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
        let array = build(&datatype, &cols, "blob", 2).unwrap();
        let array = array.as_binary::<i32>();
        assert_eq!(array.value(0), b"ab");
        assert_eq!(array.value(1), b"cdef");

        let cols = columns(&[
            ("blob/.present", NtValue::Booleans(vec![true, false])),
            ("blob", NtValue::Raw(b"ab".to_vec())),
            ("blob/.lengths", NtValue::Longs(vec![2, 0])),
        ]);
        let array = build(&datatype, &cols, "blob", 2).unwrap();
        assert!(array.as_binary::<i32>().is_null(1));
    }

    #[test]
    fn decodes_unsigned_64_bit_raw_columns() {
        // `Scalar` is an f64, so use the raw datatype for the u64 path instead.
        let datatype = DataType::UInt64;
        let mut bytes = Vec::new();
        for value in [u64::MAX, 0, 7] {
            bytes.extend_from_slice(&value.to_le_bytes());
        }
        let cols = columns(&[("value", NtValue::Raw(bytes))]);
        let array = build(&datatype, &cols, "value", 3).unwrap();
        let array = array.as_primitive::<arrow::datatypes::UInt64Type>();
        assert_eq!(array.values(), &[u64::MAX, 0, 7]);

        // A short column keeps the promised instance count instead of panicking.
        let cols = columns(&[("value", NtValue::Raw(7u64.to_le_bytes().to_vec()))]);
        let array = build(&datatype, &cols, "value", 2).unwrap();
        let array = array.as_primitive::<arrow::datatypes::UInt64Type>();
        assert_eq!(array.value(0), 7);
        assert_eq!(array.value(1), 0);
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
