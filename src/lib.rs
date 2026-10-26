//! FIRSTrun: turn FRC robot telemetry into Rerun data.
//!
//! The crate is wasm-safe: [`import_to_rrd`] and [`import_wpilog`] turn a WPI log into an in-memory
//! RRD stream for the browser importer. The native-only modules add the console importer
//! ([`importer::run_cli`]) and the live NetworkTables bridge ([`live`]).

pub mod cli;
pub mod conv;
pub mod importer;
pub mod log;
pub mod values;
pub mod wpilog;

#[cfg(not(target_arch = "wasm32"))]
pub mod first;
#[cfg(not(target_arch = "wasm32"))]
pub mod live;
#[cfg(not(target_arch = "wasm32"))]
pub mod mjpeg;
#[cfg(not(target_arch = "wasm32"))]
pub mod nt;

use wasm_bindgen::prelude::*;

pub use importer::{ImportOptions, import_to_rrd, import_to_rrd_with_options, parse_wpilog};

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
            crate::importer::fill_log(&mut contexts, &mut logger, record).unwrap();
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
