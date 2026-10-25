use std::{num::NonZero, sync::Arc};

use ::anyhow::{Context, anyhow, bail};
use arrow::{
    array::{
        ArrayRef, BinaryArray, BooleanArray, Float32Array, Float64Array, Int8Array, Int16Array,
        Int32Array, Int64Array, StringArray, UInt8Array, UInt16Array, UInt32Array, UInt64Array,
    },
    datatypes::DataType,
};
use parse::wpistruct::{
    ResolveError, StructSchemas, UnresolvedWpiLibStructType, WpiLibStructData,
    WpiLibStructPrimitives, WpiLibStructSchema, WpiLibStructType,
};

pub mod parse;

/// One decoded struct instance: the shared layout plus one value per field, in schema order.
///
/// The schema is shared through [`Arc`], so this is cheap to build for every sample and keeps
/// field names borrowed from the (memoized) layout instead of cloning them per value.
#[derive(Clone, Debug, PartialEq)]
pub struct StructValue {
    pub schema: Arc<WpiLibStructSchema<WpiLibStructType>>,
    pub fields: Vec<EntryValue>,
}

impl StructValue {
    #[must_use]
    pub fn get(&self, name: &str) -> Option<&EntryValue> {
        let index = self.schema.fields.get_index_of(name)?;
        self.fields.get(index)
    }

    pub fn iter(&self) -> impl Iterator<Item = (&str, &EntryValue)> {
        self.schema
            .fields
            .keys()
            .map(String::as_str)
            .zip(self.fields.iter())
    }

    #[must_use]
    pub fn len(&self) -> usize {
        self.fields.len()
    }

    #[must_use]
    pub fn is_empty(&self) -> bool {
        self.fields.is_empty()
    }
}

#[derive(Clone, Debug, PartialEq)]
pub enum EntryValue {
    Arrow(ArrayRef),
    ArrayArrow(Vec<ArrayRef>),
    StructSchema(WpiLibStructSchema<UnresolvedWpiLibStructType>),

    Struct(StructValue),
    StructArray(Vec<StructValue>),
}

#[derive(Debug, PartialEq, Eq)]
pub enum ParseError {
    InvalidFormat(nom::error::ErrorKind),
}

impl std::error::Error for ParseError {}

impl std::fmt::Display for ParseError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::InvalidFormat(kind) => write!(f, "Invalid format: {kind:?}"),
        }
    }
}
impl<T> nom::error::ParseError<T> for ParseError {
    fn from_error_kind(_input: T, kind: nom::error::ErrorKind) -> Self {
        Self::InvalidFormat(kind)
    }

    fn append(_input: T, kind: nom::error::ErrorKind, _other: Self) -> Self {
        Self::InvalidFormat(kind)
    }
}

#[derive(Debug)]
pub enum EntryValueParseError {
    StructNotFound(String),
    Other(anyhow::Error),
}
impl std::fmt::Display for EntryValueParseError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::StructNotFound(s) => write!(f, "Struct not found: {s}"),
            Self::Other(err) => write!(f, "{err}"),
        }
    }
}
impl std::error::Error for EntryValueParseError {}
impl From<anyhow::Error> for EntryValueParseError {
    fn from(err: anyhow::Error) -> Self {
        Self::Other(err)
    }
}

impl EntryValue {
    /// Whether a WPILog type string names a WPILib struct type rather than a primitive.
    #[must_use]
    pub fn is_struct_type(ty: &str) -> bool {
        let ty = ty.strip_suffix("[]").unwrap_or(ty);
        !matches!(
            ty,
            "raw"
                | "byte"
                | "bytes"
                | "boolean"
                | "bool"
                | "int64"
                | "int"
                | "long"
                | "int32"
                | "int16"
                | "int8"
                | "uint64"
                | "uint32"
                | "uint16"
                | "uint8"
                | "float"
                | "float32"
                | "double"
                | "float64"
                | "string"
                | "json"
                | "structschema"
        )
    }

    /// Decodes one primitive struct field: `bytes` holds exactly one value (or `count` values).
    pub fn parse_struct_leaf(
        bytes: &[u8],
        primitive: WpiLibStructPrimitives,
        count: Option<NonZero<usize>>,
    ) -> Result<ArrayRef, anyhow::Error> {
        if primitive.is_char() {
            // `char[N]` is N raw bytes, not a WPILog length-prefixed `string[]`.
            return Ok(Self::parse_chars(bytes));
        }
        if count.is_some() {
            Self::parse_array(bytes, &primitive.datatype())
        } else {
            Self::parse_scalar(bytes, &primitive.datatype())
        }
    }

    pub fn parse_from_wpilog(
        mut ty: &str,
        data: &[u8],
        schemas: &mut StructSchemas,
    ) -> Result<EntryValue, EntryValueParseError> {
        let is_array = ty.strip_suffix("[]").map(|st| ty = st).is_some();

        Ok(match ty {
            "raw" | "byte" | "bytes" => Self::parse_datatype(data, is_array, &DataType::Binary)?,
            "boolean" | "bool" => Self::parse_datatype(data, is_array, &DataType::Boolean)?,
            // WPILog's own integers are all 64 bits wide; the narrower spellings are decoded at
            // their native width so producers that use them are not rejected.
            "int64" | "int" | "long" => Self::parse_datatype(data, is_array, &DataType::Int64)?,
            "int32" => Self::parse_datatype(data, is_array, &DataType::Int32)?,
            "int16" => Self::parse_datatype(data, is_array, &DataType::Int16)?,
            "int8" => Self::parse_datatype(data, is_array, &DataType::Int8)?,
            "uint64" => Self::parse_datatype(data, is_array, &DataType::UInt64)?,
            "uint32" => Self::parse_datatype(data, is_array, &DataType::UInt32)?,
            "uint16" => Self::parse_datatype(data, is_array, &DataType::UInt16)?,
            "uint8" => Self::parse_datatype(data, is_array, &DataType::UInt8)?,
            "float" | "float32" => Self::parse_datatype(data, is_array, &DataType::Float32)?,
            "double" | "float64" => Self::parse_datatype(data, is_array, &DataType::Float64)?,
            "string" | "json" => Self::parse_datatype(data, is_array, &DataType::Utf8)?,
            "structschema" => Self::StructSchema(WpiLibStructSchema::parse(data)?),
            name => match schemas.resolve(name) {
                Ok(schema) => Self::parse_from_struct(data, &schema, is_array)?,
                Err(ResolveError::NotFound(name)) => {
                    return Err(EntryValueParseError::StructNotFound(name));
                }
                Err(err) => return Err(anyhow!("{err}").into()),
            },
        })
    }

    fn parse_datatype(
        data: &[u8],
        is_array: bool,
        ty: &DataType,
    ) -> Result<EntryValue, EntryValueParseError> {
        let array = if is_array {
            Self::parse_array(data, ty)?
        } else {
            Self::parse_scalar(data, ty)?
        };
        Ok(EntryValue::Arrow(array))
    }

    /// Parses exactly one value of `ty`.
    fn parse_scalar(data: &[u8], ty: &DataType) -> Result<ArrayRef, anyhow::Error> {
        Ok(match ty {
            DataType::Binary => Arc::new(BinaryArray::from_iter_values([data])) as ArrayRef,
            DataType::Utf8 => Arc::new(StringArray::from_iter_values([String::from_utf8_lossy(
                data,
            )])) as ArrayRef,
            DataType::Boolean => {
                let Some(byte) = data.first() else {
                    bail!("not enough data for a boolean");
                };
                if data.len() != 1 {
                    bail!("expected 1 byte for a boolean, got {}", data.len());
                }
                Arc::new(BooleanArray::from(vec![*byte != 0]))
            }
            ty => {
                let size = Self::datatype_size(ty)
                    .ok_or_else(|| anyhow!("unsupported datatype {ty:?}"))?;
                if data.len() != size {
                    bail!(
                        "expected {size} bytes for {ty} but the payload has {}",
                        data.len()
                    );
                }
                Self::parse_element(data, ty)
            }
        })
    }

    /// Parses a fixed-width array with a single Arrow buffer allocation.
    fn parse_array(data: &[u8], ty: &DataType) -> Result<ArrayRef, anyhow::Error> {
        if data.is_empty() {
            return Ok(arrow::array::new_empty_array(ty));
        }

        match ty {
            // WPILog's `string[]` and `raw[]` are encoded as a u32 count followed by
            // length-prefixed values rather than fixed-width elements.
            DataType::Utf8 | DataType::Binary => Self::parse_length_prefixed_array(data, ty),
            DataType::Boolean => Ok(Arc::new(BooleanArray::from_iter(
                data.iter().map(|byte| Some(*byte != 0)),
            ))),
            ty => {
                let size = Self::datatype_size(ty)
                    .ok_or_else(|| anyhow!("datatype {ty} cannot be used as an array"))?;
                let chunks = data.chunks_exact(size);
                if !chunks.remainder().is_empty() {
                    bail!(
                        "array payload has {} trailing bytes for {ty}",
                        chunks.remainder().len()
                    );
                }

                macro_rules! collect {
                    ($array:ty, $native:ty) => {{
                        Ok(Arc::new(<$array>::from_iter_values(chunks.map(|chunk| {
                            <$native>::from_le_bytes(chunk.try_into().expect("chunk size"))
                        }))) as ArrayRef)
                    }};
                }

                match ty {
                    DataType::Int8 => collect!(Int8Array, i8),
                    DataType::Int16 => collect!(Int16Array, i16),
                    DataType::Int32 => collect!(Int32Array, i32),
                    DataType::Int64 => collect!(Int64Array, i64),
                    DataType::UInt8 => collect!(UInt8Array, u8),
                    DataType::UInt16 => collect!(UInt16Array, u16),
                    DataType::UInt32 => collect!(UInt32Array, u32),
                    DataType::UInt64 => collect!(UInt64Array, u64),
                    DataType::Float32 => collect!(Float32Array, f32),
                    DataType::Float64 => collect!(Float64Array, f64),
                    other => bail!("unsupported datatype {other}"),
                }
            }
        }
    }

    fn parse_length_prefixed_array(data: &[u8], ty: &DataType) -> Result<ArrayRef, anyhow::Error> {
        let (count, mut rest) = Self::read_u32_prefix(data)?;
        let count = count as usize;
        // Every element needs at least its own u32 length prefix, so a count that cannot fit in
        // the remaining payload is corrupt (or malicious). Check before reserving anything.
        if count > rest.len() / 4 {
            bail!(
                "array declares {count} elements but only {} bytes follow",
                rest.len()
            );
        }

        let mut values = Vec::with_capacity(count);
        for _ in 0..count {
            let (len, tail) = Self::read_u32_prefix(rest)?;
            let Some((value, tail)) = tail.split_at_checked(len as usize) else {
                return Err(anyhow!("truncated length-prefixed array element"));
            };
            values.push(value);
            rest = tail;
        }
        if !rest.is_empty() {
            bail!("array payload has {} trailing bytes", rest.len());
        }

        if ty == &DataType::Utf8 {
            Ok(Arc::new(StringArray::from_iter_values(
                values
                    .into_iter()
                    .map(|value| String::from_utf8_lossy(value)),
            )))
        } else {
            Ok(Arc::new(BinaryArray::from_iter_values(values)))
        }
    }

    fn read_u32_prefix(data: &[u8]) -> Result<(u32, &[u8]), anyhow::Error> {
        let bytes: [u8; 4] = data
            .get(0..4)
            .with_context(|| anyhow!("not enough data for a u32 length prefix"))?
            .try_into()
            .map_err(|_| anyhow!("not enough data for a u32 length prefix"))?;
        Ok((u32::from_le_bytes(bytes), &data[4..]))
    }

    /// Parses one fixed-width element. `data` must be exactly one element long.
    fn parse_element(data: &[u8], ty: &DataType) -> ArrayRef {
        macro_rules! element {
            ($array:ty, $native:ty) => {{
                let bytes = data.try_into().expect("element size was checked");
                Arc::new(<$array>::from_iter_values([<$native>::from_le_bytes(
                    bytes,
                )])) as ArrayRef
            }};
        }

        match ty {
            DataType::Int8 => element!(Int8Array, i8),
            DataType::Int16 => element!(Int16Array, i16),
            DataType::Int32 => element!(Int32Array, i32),
            DataType::Int64 => element!(Int64Array, i64),
            DataType::UInt8 => element!(UInt8Array, u8),
            DataType::UInt16 => element!(UInt16Array, u16),
            DataType::UInt32 => element!(UInt32Array, u32),
            DataType::UInt64 => element!(UInt64Array, u64),
            DataType::Float32 => element!(Float32Array, f32),
            DataType::Float64 => element!(Float64Array, f64),
            other => unreachable!("unsupported scalar datatype {other:?}"),
        }
    }

    // Returns the size of the datatype in the datalog spec.
    //
    // A return value of `None` indicates that the datatype is variable-sized, and cannot be used
    // as an array.
    fn datatype_size(ty: &DataType) -> Option<usize> {
        match ty {
            DataType::Binary | DataType::Utf8 => None,
            DataType::Boolean | DataType::Int8 | DataType::UInt8 => Some(1),
            DataType::Int16 | DataType::UInt16 => Some(2),
            DataType::Int32 | DataType::UInt32 | DataType::Float32 => Some(4),
            DataType::Int64 | DataType::UInt64 | DataType::Float64 => Some(8),
            _ => None,
        }
    }

    fn parse_from_struct(
        data: &[u8],
        schema: &Arc<WpiLibStructSchema<WpiLibStructType>>,
        is_array: bool,
    ) -> Result<EntryValue, anyhow::Error> {
        if !is_array {
            let (remaining, value) = Self::parse_fields(data, schema)?;
            anyhow::ensure!(remaining.is_empty(), "struct payload has trailing bytes");
            return Ok(EntryValue::Struct(value));
        }

        let size = schema.size();
        if size == 0 {
            bail!("cannot parse an array of zero-sized structs");
        }
        let mut chunks = data.chunks_exact(size);
        if !chunks.remainder().is_empty() {
            bail!(
                "struct array payload has {} trailing bytes",
                chunks.remainder().len()
            );
        }

        let mut values = Vec::with_capacity(data.len() / size);
        for chunk in chunks.by_ref() {
            let (remaining, value) = Self::parse_fields(chunk, schema)?;
            debug_assert!(remaining.is_empty(), "chunked struct parse left bytes");
            values.push(value);
        }

        Ok(EntryValue::StructArray(values))
    }

    fn parse_fields<'d>(
        mut data: &'d [u8],
        schema: &Arc<WpiLibStructSchema<WpiLibStructType>>,
    ) -> Result<(&'d [u8], StructValue), anyhow::Error> {
        let mut fields = Vec::with_capacity(schema.fields.len());

        for (_, field) in &schema.fields {
            let (remaining, value) = Self::parse_field(data, field)?;
            data = remaining;
            fields.push(value);
        }

        Ok((
            data,
            StructValue {
                schema: Arc::clone(schema),
                fields,
            },
        ))
    }

    fn parse_field<'d>(
        data: &'d [u8],
        field: &WpiLibStructData<WpiLibStructType>,
    ) -> Result<(&'d [u8], EntryValue), anyhow::Error> {
        let count = field.count.map_or(1, NonZero::get);

        match &field.ty {
            WpiLibStructType::Primitive(primitive) => {
                let len = primitive.size() * count;
                let (remaining, bytes) = nom::bytes::complete::take::<_, _, ()>(len)(data)?;

                let value = if primitive.is_char() {
                    // `char[N]` is N raw bytes, not a WPILog length-prefixed `string[]`.
                    Self::parse_chars(bytes)
                } else if field.count.is_some() {
                    Self::parse_array(bytes, &primitive.datatype())?
                } else {
                    Self::parse_scalar(bytes, &primitive.datatype())?
                };

                Ok((remaining, EntryValue::Arrow(value)))
            }
            WpiLibStructType::Custom(nested) => {
                if field.count.is_none() {
                    let (remaining, value) = Self::parse_fields(data, nested)?;
                    return Ok((remaining, EntryValue::Struct(value)));
                }

                let size = nested.size();
                if size == 0 {
                    bail!("cannot parse an array of zero-sized structs");
                }
                let len = size
                    .checked_mul(count)
                    .ok_or_else(|| anyhow!("struct array size overflow"))?;
                let (remaining, bytes) = nom::bytes::complete::take::<_, _, ()>(len)(data)?;

                let mut values = Vec::with_capacity(count);
                for chunk in bytes.chunks_exact(size) {
                    let (leftover, value) = Self::parse_fields(chunk, nested)?;
                    debug_assert!(leftover.is_empty(), "chunked struct parse left bytes");
                    values.push(value);
                }

                Ok((remaining, EntryValue::StructArray(values)))
            }
        }
    }

    /// Decodes a `char[N]` field: one single-character string per byte.
    fn parse_chars(bytes: &[u8]) -> ArrayRef {
        Arc::new(StringArray::from_iter_values(
            bytes.iter().map(|byte| char::from(*byte).to_string()),
        ))
    }
}

#[cfg(test)]
mod tests {
    use super::{EntryValue, StructValue};
    use crate::values::parse::wpistruct::{StructSchemas, WpiLibStructSchema};
    use arrow::array::{Array, Float64Array, Int64Array, StringArray};

    fn schemas() -> StructSchemas {
        StructSchemas::new()
    }

    fn arrow_of(value: &StructValue, field: &str) -> arrow::array::ArrayRef {
        let Some(EntryValue::Arrow(array)) = value.get(field) else {
            panic!("expected {field} to be an Arrow value");
        };
        array.clone()
    }

    #[test]
    fn parses_string_arrays_with_length_prefixes() {
        let mut payload = Vec::new();
        payload.extend_from_slice(&2u32.to_le_bytes());
        payload.extend_from_slice(&3u32.to_le_bytes());
        payload.extend_from_slice(b"one");
        payload.extend_from_slice(&3u32.to_le_bytes());
        payload.extend_from_slice(b"two");

        let value = EntryValue::parse_from_wpilog("string[]", &payload, &mut schemas()).unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        let values = value
            .as_any()
            .downcast_ref::<arrow::array::StringArray>()
            .unwrap();
        assert_eq!(values.value(0), "one");
        assert_eq!(values.value(1), "two");
    }

    #[test]
    fn rejects_string_array_counts_that_do_not_fit() {
        // A corrupt count must not reserve a huge buffer before failing.
        let mut payload = Vec::new();
        payload.extend_from_slice(&u32::MAX.to_le_bytes());
        payload.extend_from_slice(b"ab");
        assert!(EntryValue::parse_from_wpilog("string[]", &payload, &mut schemas()).is_err());
    }

    #[test]
    fn parses_empty_primitive_arrays() {
        let value = EntryValue::parse_from_wpilog("double[]", &[], &mut schemas()).unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        assert_eq!(value.len(), 0);
    }

    #[test]
    fn parses_json_as_text() {
        let value =
            EntryValue::parse_from_wpilog("json", br#"{"ok":true}"#, &mut schemas()).unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        let values = value
            .as_any()
            .downcast_ref::<arrow::array::StringArray>()
            .unwrap();
        assert_eq!(values.value(0), r#"{"ok":true}"#);
    }

    #[test]
    fn parses_fixed_width_arrays_without_overlapping_elements() {
        let value = EntryValue::parse_from_wpilog(
            "int64[]",
            &[
                1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0,
            ],
            &mut schemas(),
        )
        .unwrap();

        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        let values = value.as_any().downcast_ref::<Int64Array>().unwrap();
        assert_eq!(values.len(), 3);
        assert_eq!(values.value(0), 1);
        assert_eq!(values.value(1), 2);
        assert_eq!(values.value(2), 3);
    }

    #[test]
    fn rejects_partial_fixed_width_array_payloads() {
        assert!(EntryValue::parse_from_wpilog("int64[]", &[1, 2, 3], &mut schemas()).is_err());
        // Scalars must be exactly one element wide.
        assert!(EntryValue::parse_from_wpilog("int64", &[1, 2, 3, 4], &mut schemas()).is_err());
        assert!(
            EntryValue::parse_from_wpilog("int64", &[1, 2, 3, 4, 5, 6, 7, 8, 9], &mut schemas())
                .is_err()
        );
    }

    #[test]
    fn parses_narrow_integer_topics() {
        let value =
            EntryValue::parse_from_wpilog("int32", &7i32.to_le_bytes(), &mut schemas()).unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<arrow::array::Int32Array>()
                .unwrap()
                .value(0),
            7
        );

        let value = EntryValue::parse_from_wpilog("uint8", &[9], &mut schemas()).unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected one Arrow array");
        };
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<arrow::array::UInt8Array>()
                .unwrap()
                .value(0),
            9
        );
    }

    #[test]
    fn decodes_struct_fields_in_schema_order() {
        let mut schemas = schemas();

        let mut payload = Vec::new();
        payload.extend_from_slice(&1.25f64.to_le_bytes());
        payload.extend_from_slice(&(-2.5f64).to_le_bytes());

        let value =
            EntryValue::parse_from_wpilog("struct:Translation2d", &payload, &mut schemas).unwrap();
        let EntryValue::Struct(fields) = value else {
            panic!("expected a struct value");
        };
        assert_eq!(
            fields.iter().map(|(name, _)| name).collect::<Vec<_>>(),
            ["x", "y"]
        );
        assert_eq!(
            arrow_of(&fields, "x")
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            1.25
        );
        assert_eq!(
            arrow_of(&fields, "y")
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            -2.5
        );
    }

    #[test]
    fn decodes_struct_arrays_into_ordered_indexed_maps() {
        let mut schemas = schemas();

        let mut payload = Vec::new();
        for (x, y) in [(1.0f64, 2.0f64), (3.0f64, 4.0f64)] {
            payload.extend_from_slice(&x.to_le_bytes());
            payload.extend_from_slice(&y.to_le_bytes());
        }

        let value = EntryValue::parse_from_wpilog("struct:Translation2d[]", &payload, &mut schemas)
            .unwrap();
        let EntryValue::StructArray(elements) = value else {
            panic!("expected an array of struct values");
        };
        assert_eq!(elements.len(), 2);
        for (element, expected) in elements.iter().zip([(1.0, 2.0), (3.0, 4.0)]) {
            assert_eq!(
                arrow_of(element, "x")
                    .as_any()
                    .downcast_ref::<Float64Array>()
                    .unwrap()
                    .value(0),
                expected.0
            );
            assert_eq!(
                arrow_of(element, "y")
                    .as_any()
                    .downcast_ref::<Float64Array>()
                    .unwrap()
                    .value(0),
                expected.1
            );
        }
    }

    #[test]
    fn decodes_char_arrays_as_raw_bytes() {
        let mut schemas = schemas();
        schemas.insert(
            "struct:HasChars",
            WpiLibStructSchema::parse(b"char name[3]; double x;").unwrap(),
        );

        let mut payload = Vec::new();
        payload.extend_from_slice(b"abc");
        payload.extend_from_slice(&1.5f64.to_le_bytes());

        let value =
            EntryValue::parse_from_wpilog("struct:HasChars", &payload, &mut schemas).unwrap();
        let EntryValue::Struct(fields) = value else {
            panic!("expected a struct value");
        };
        let letters = arrow_of(&fields, "name");
        let letters = letters.as_any().downcast_ref::<StringArray>().unwrap();
        assert_eq!(letters.len(), 3);
        assert_eq!(
            (0..3).map(|i| letters.value(i)).collect::<Vec<_>>(),
            ["a", "b", "c"]
        );
        assert_eq!(
            arrow_of(&fields, "x")
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            1.5
        );
    }

    #[test]
    fn decodes_nested_struct_array_fields() {
        let mut schemas = schemas();
        schemas.insert(
            "struct:Holder",
            WpiLibStructSchema::parse(b"struct:Translation2d pts[2]; double z;").unwrap(),
        );

        let mut payload = Vec::new();
        for value in [1.0f64, 2.0, 3.0, 4.0, 9.0] {
            payload.extend_from_slice(&value.to_le_bytes());
        }

        let value = EntryValue::parse_from_wpilog("struct:Holder", &payload, &mut schemas).unwrap();
        let EntryValue::Struct(fields) = value else {
            panic!("expected a struct value");
        };
        let Some(EntryValue::StructArray(points)) = fields.get("pts") else {
            panic!("expected an array of nested structs");
        };
        assert_eq!(points.len(), 2);
        assert_eq!(
            arrow_of(&points[1], "x")
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            3.0
        );
        assert_eq!(
            arrow_of(&fields, "z")
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            9.0,
            "fields after a struct array must stay aligned"
        );
    }

    #[test]
    fn rejects_recursive_struct_values() {
        let mut schemas = schemas();
        schemas.insert(
            "struct:Loop",
            WpiLibStructSchema::parse(b"struct:Loop self;").unwrap(),
        );
        let error =
            EntryValue::parse_from_wpilog("struct:Loop", &[0; 8], &mut schemas).unwrap_err();
        assert!(error.to_string().contains("recursive"), "{error}");
    }

    #[test]
    fn reports_unknown_structs() {
        let error =
            EntryValue::parse_from_wpilog("struct:Unknown", &[0; 8], &mut schemas()).unwrap_err();
        assert!(matches!(
            error,
            super::EntryValueParseError::StructNotFound(_)
        ));
    }

    #[test]
    fn resolves_fully_qualified_struct_names() {
        let mut schemas = schemas();
        let mut payload = Vec::new();
        payload.extend_from_slice(&1.0f64.to_le_bytes());
        payload.extend_from_slice(&2.0f64.to_le_bytes());
        let value = EntryValue::parse_from_wpilog(
            "struct:edu.wpi.first.math.geometry.Translation2d",
            &payload,
            &mut schemas,
        )
        .unwrap();
        let EntryValue::Struct(fields) = value else {
            panic!("expected a struct value");
        };
        assert_eq!(fields.len(), 2);
    }
}
