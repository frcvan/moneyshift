//! Adapter for plain (non-`/Rerun`) NetworkTables topics.
//!
//! Robot code, AdvantageKit and dashboards publish ordinary NT values: `double`, `double[]`,
//! `boolean`, `string`, `struct:Pose2d`, … . This module turns those values into the same
//! [`EntryValue`] model the WPILog importer uses, so both paths share `conv`'s heuristics.

use std::sync::Arc;

use arrow::array::{
    ArrayRef, BinaryArray, BooleanArray, Float32Array, Float64Array, Int64Array, StringArray,
};
use nt_client::data::DataType;
use re_log_types::EntityPath;

use crate::log::{EntryLog, Timestamp};
use crate::values::EntryValue;

/// Whether a plain topic is NetworkTables machinery that should never be graphed.
#[must_use]
pub fn is_internal_topic(name: &str) -> bool {
    name.starts_with("/.schema/")
        || name == "/.schema"
        || name.starts_with("/$")
        || name.starts_with("/Rerun/.rerun")
}

/// Ingests one NT topic update into the log.
///
/// Struct values are decoded with the schemas `EntryLog` already knows (WPILib's built-ins plus
/// anything published under `/.schema/`). Unknown struct types are queued by `EntryLog` until
/// their schema arrives, exactly like a WPILog that publishes schemas late.
pub fn ingest(
    log: &mut EntryLog,
    key: EntityPath,
    timestamp: Timestamp,
    ty: &DataType,
    value: &rmpv::Value,
) -> anyhow::Result<()> {
    if is_schema_type(ty) {
        if let Some(bytes) = binary_bytes(value) {
            // The schema type name lives in the topic (`/.schema/struct:Pose2d`); `EntryLog`
            // registers it under both the bare and prefixed names.
            log.add_entry(key, timestamp, "structschema", bytes)?;
        }
        return Ok(());
    }

    if let Some((name, is_array)) = struct_type(ty)
        && let Some(bytes) = binary_bytes(value)
    {
        let data = if is_array {
            format!("struct:{name}[]")
        } else {
            format!("struct:{name}")
        };
        log.add_entry(key, timestamp, &data, bytes)?;
        return Ok(());
    }

    let Some(value) = entry_value(ty, value) else {
        re_log::debug_once!("ignoring unsupported NetworkTables value of type {ty:?}");
        return Ok(());
    };
    log.add_entryvalue(key, timestamp, value)
}

fn is_schema_type(ty: &DataType) -> bool {
    matches!(ty, DataType::StructSchema)
        || matches!(ty, DataType::Unknown(name) if name == "structschema")
}

fn binary_bytes(value: &rmpv::Value) -> Option<&[u8]> {
    match value {
        rmpv::Value::Binary(bytes) => Some(bytes),
        rmpv::Value::String(text) => Some(text.as_str()?.as_bytes()),
        _ => None,
    }
}

/// `(struct name, is_array)` for struct-typed topics.
fn struct_type(ty: &DataType) -> Option<(String, bool)> {
    match ty {
        DataType::Struct(name) => Some((name.clone(), false)),
        DataType::StructArray(name) => Some((name.clone(), true)),
        DataType::Unknown(name) => {
            let name = name.strip_prefix("struct:")?;
            match name.strip_suffix("[]") {
                Some(name) => Some((name.to_owned(), true)),
                None => Some((name.to_owned(), false)),
            }
        }
        _ => None,
    }
}

/// Maps a primitive NT value to an Arrow-backed [`EntryValue`].
#[must_use]
pub fn entry_value(ty: &DataType, value: &rmpv::Value) -> Option<EntryValue> {
    Some(EntryValue::Arrow(match ty {
        DataType::Boolean => Arc::new(BooleanArray::from(vec![value.as_bool()?])) as ArrayRef,
        DataType::Double => Arc::new(Float64Array::from(vec![value.as_f64()?])) as ArrayRef,
        DataType::Float => Arc::new(Float32Array::from(vec![value.as_f64()? as f32])) as ArrayRef,
        DataType::Int => Arc::new(Int64Array::from(vec![value.as_i64()?])) as ArrayRef,
        DataType::String | DataType::Json => {
            Arc::new(StringArray::from(vec![value.as_str()?])) as ArrayRef
        }
        DataType::Raw | DataType::Rpc => {
            let rmpv::Value::Binary(bytes) = value else {
                return None;
            };
            Arc::new(BinaryArray::from_iter_values([bytes.as_slice()])) as ArrayRef
        }
        DataType::BooleanArray => {
            let values = value.as_array()?;
            Arc::new(BooleanArray::from_iter(
                values.iter().map(rmpv::Value::as_bool),
            )) as ArrayRef
        }
        DataType::DoubleArray => {
            let values = value.as_array()?;
            Arc::new(Float64Array::from_iter(
                values.iter().map(rmpv::Value::as_f64),
            )) as ArrayRef
        }
        DataType::FloatArray => {
            let values = value.as_array()?;
            Arc::new(Float32Array::from_iter(
                values
                    .iter()
                    .map(|value| value.as_f64().map(|value| value as f32)),
            )) as ArrayRef
        }
        DataType::IntArray => {
            let values = value.as_array()?;
            Arc::new(Int64Array::from_iter(
                values.iter().map(rmpv::Value::as_i64),
            )) as ArrayRef
        }
        DataType::StringArray => {
            let values = value.as_array()?;
            Arc::new(StringArray::from_iter(
                values.iter().map(rmpv::Value::as_str),
            )) as ArrayRef
        }
        _ => return None,
    }))
}

/// The entity path for a plain NT topic (drops the leading slash).
#[must_use]
pub fn entity_path(topic: &str) -> EntityPath {
    EntityPath::from_file_path(std::path::Path::new(topic.trim_start_matches('/')))
}

#[cfg(test)]
mod tests {
    use super::{entity_path, entry_value, ingest, is_internal_topic};
    use crate::log::{EntryLog, Timestamp};
    use crate::values::EntryValue;
    use arrow::array::{Array, Float64Array, StringArray};
    use nt_client::data::DataType;

    #[test]
    fn maps_primitive_nt_values() {
        let EntryValue::Arrow(array) =
            entry_value(&DataType::Double, &rmpv::Value::F64(1.5)).unwrap()
        else {
            panic!("expected an Arrow value");
        };
        assert_eq!(
            array
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            1.5
        );

        let EntryValue::Arrow(array) = entry_value(
            &DataType::StringArray,
            &rmpv::Value::Array(vec![
                rmpv::Value::String("a".into()),
                rmpv::Value::String("b".into()),
            ]),
        )
        .unwrap() else {
            panic!("expected an Arrow value");
        };
        let array = array.as_any().downcast_ref::<StringArray>().unwrap();
        assert_eq!(array.len(), 2);
        assert_eq!(array.value(1), "b");
    }

    #[test]
    fn struct_bytes_go_through_the_wpilog_schema_decoder() {
        let mut log = EntryLog::new();
        let key = entity_path("/Drive/Translation");
        let payload = [1.0f64.to_le_bytes(), 2.0f64.to_le_bytes()]
            .into_iter()
            .flatten()
            .collect::<Vec<_>>();

        ingest(
            &mut log,
            key.clone(),
            Timestamp(1),
            &DataType::Struct("Translation2d".to_owned()),
            &rmpv::Value::Binary(payload),
        )
        .unwrap();

        let fields = log
            .get_latest_entry(&key.join(&re_log_types::EntityPath::from_single_string("x")))
            .map(|(_, value)| value.clone());
        assert!(
            fields.is_some(),
            "Translation2d should decode into x/y fields"
        );
    }

    #[test]
    fn schema_topics_are_registered_for_later_structs() {
        let mut log = EntryLog::new();
        ingest(
            &mut log,
            entity_path("/.schema/struct:Custom"),
            Timestamp(1),
            &DataType::StructSchema,
            &rmpv::Value::Binary(b"double value;".to_vec()),
        )
        .unwrap();

        let mut payload = Vec::new();
        payload.extend_from_slice(&3.5f64.to_le_bytes());
        ingest(
            &mut log,
            entity_path("/custom"),
            Timestamp(2),
            &DataType::Struct("Custom".to_owned()),
            &rmpv::Value::Binary(payload),
        )
        .unwrap();

        let (_, value) = log
            .get_latest_entry(&entity_path("/custom/value"))
            .expect("schema was registered");
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            3.5
        );
    }

    #[test]
    fn filters_networktables_internals() {
        assert!(is_internal_topic("/.schema/struct:Pose2d"));
        assert!(is_internal_topic("/Rerun/.rerun"));
        assert!(!is_internal_topic("/Drive/Pose"));
        assert!(!is_internal_topic("/Rerun/world/points"));
    }
}
