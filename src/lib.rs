pub mod conv;
pub mod log;
pub mod values;
pub mod wpilog;

use std::path::Path;

use hashbrown::HashMap;
use re_build_info::CrateVersion;
use re_chunk::RowId;
use re_log;
use re_log_encoding::rrd::{Encoder, EncodingOptions};
use re_log_types::{
    EntityPath, LogMsg, SetStoreInfo, StoreId, StoreInfo, StoreKind, StoreSource, Timeline,
};
use wasm_bindgen::prelude::*;

use conv::{ConversionOptions, log_changes_to_chunks_with_options};
use log::{EntryLog, Timestamp};
use wpilog::parse::{Payload, WpiLogFile, WpiRecord};

struct EntryContext<'log> {
    ty: &'log str,
    name: &'log str,
}

fn handle_data(
    ty: &str,
    timestamp: Timestamp,
    key: EntityPath,
    data: &[u8],
    logger: &mut EntryLog,
) -> anyhow::Result<()> {
    logger.add_entry(key, timestamp, ty, data)
}

fn fill_log<'file>(
    contexts: &mut HashMap<u32, EntryContext<'file>>,
    logger: &mut EntryLog,
    record: WpiRecord<'file>,
) -> anyhow::Result<()> {
    match record.payload {
        Payload::Start {
            entry_id,
            entry_name,
            entry_type,
            ..
        } => {
            let mut entry_name = entry_name.strip_prefix("NT:").unwrap_or(entry_name);
            while let Some(new_name) = entry_name.strip_prefix('/') {
                entry_name = new_name;
            }
            contexts.insert(
                entry_id,
                EntryContext {
                    ty: entry_type,
                    name: entry_name,
                },
            );
        }
        Payload::Raw { entry_id, data } => {
            let Some(context) = contexts.get(&entry_id) else {
                return Ok(());
            };
            let key = EntityPath::from_file_path(Path::new(context.name));
            handle_data(context.ty, record.timestamp, key, data, logger)?;
        }
        _ => {}
    }
    Ok(())
}

pub fn import_to_rrd(contents: &[u8]) -> anyhow::Result<Vec<u8>> {
    if !WpiLogFile::is_wpilog(contents) {
        anyhow::bail!("the selected file is not a WPI log");
    }

    let store_id = StoreId::random(StoreKind::Recording, "FIRSTrun");
    let timeline = Timeline::new_duration("robotime");
    let options = ConversionOptions {
        recording_name: Some("FIRSTrun WPI log".into()),
        view_coordinates: true,
        ..ConversionOptions::default()
    };
    let mut contexts = HashMap::new();
    let mut logger = EntryLog::new();

    let (_, _) = WpiLogFile::parse(contents, |record| {
        if let Err(error) = fill_log(&mut contexts, &mut logger, record) {
            re_log::warn!("failed to parse WPI log entry: {error}");
        }
    })
    .map_err(|error| anyhow::anyhow!("failed to parse WPI log: {error}"))?;

    let mut output = Vec::new();
    let mut encoder = Encoder::new_eager(
        CrateVersion::LOCAL,
        EncodingOptions::PROTOBUF_COMPRESSED,
        &mut output,
    )?;
    encoder.append(&LogMsg::SetStoreInfo(SetStoreInfo {
        row_id: *RowId::new(),
        info: StoreInfo::new(store_id.clone(), StoreSource::Other("WpiLog".into())),
    }))?;
    for chunk in log_changes_to_chunks_with_options(timeline, &mut logger, &options) {
        encoder.append(&LogMsg::ArrowMsg(store_id.clone(), chunk.to_arrow_msg()?))?;
    }
    encoder.finish()?;
    drop(encoder);
    Ok(output)
}

#[wasm_bindgen]
pub fn import_wpilog(contents: &[u8]) -> Result<Vec<u8>, JsValue> {
    import_to_rrd(contents).map_err(|error| JsValue::from_str(&error.to_string()))
}

#[cfg(test)]
mod tests {
    use super::import_to_rrd;

    #[test]
    fn rejects_non_wpilog_data() {
        assert!(import_to_rrd(b"not a WPI log").is_err());
    }

    #[test]
    fn imports_the_example_wpilog() {
        let contents = include_bytes!("../test_data/FRC_TBD_d225b5377c70a88d.wpilog");
        let rrd = import_to_rrd(contents).unwrap();
        assert!(!rrd.is_empty());
    }

    #[test]
    fn the_example_wpilog_contains_renderable_native_values() {
        use crate::log::EntryLog;
        use crate::wpilog::parse::WpiLogFile;
        use hashbrown::HashMap;

        let contents = include_bytes!("../test_data/FRC_TBD_d225b5377c70a88d.wpilog");
        let mut contexts = HashMap::new();
        let mut logger = EntryLog::new();
        WpiLogFile::parse(contents, |record| {
            super::fill_log(&mut contexts, &mut logger, record).unwrap();
        })
        .unwrap();

        let chunks = crate::conv::log_changes_to_chunks_with_options(
            re_log_types::Timeline::new_duration("robotime"),
            &mut logger,
            &crate::conv::ConversionOptions::default(),
        );

        let has_archetype = |entity: &str, archetype: &str| {
            chunks.iter().any(|chunk| {
                chunk.entity_path() == &re_log_types::EntityPath::from(entity)
                    && chunk.component_descriptors().any(|descriptor| {
                        descriptor
                            .archetype
                            .as_ref()
                            .is_some_and(|name| name == archetype)
                    })
            })
        };

        assert!(has_archetype(
            "AdvantageKit/RealOutputs/SomeSubsystem/ScalarEntity",
            "rerun.archetypes.Scalars"
        ));
        assert!(has_archetype(
            "AdvantageKit/RealOutputs/OtherSubsystem/Point3dEntity",
            "rerun.archetypes.Points3D"
        ));
        assert!(has_archetype(
            "AdvantageKit/RadioStatus/Status",
            "rerun.archetypes.TextDocument"
        ));
        assert!(!chunks.iter().any(|chunk| {
            let path = chunk.entity_path().to_string();
            path.ends_with("/.type") || path.ends_with("/.components")
        }));
    }
}
