//! Importing WPI logs and emitting RRD.
//!
//! [`import_to_rrd`] is the wasm entry point (it returns the whole recording in memory);
//! [`run_cli`] is the Rerun external-importer entry point (it streams RRD to stdout and uses exit
//! code 66 for "not my file").

use std::path::Path;

use hashbrown::HashMap;
use re_build_info::CrateVersion;
use re_chunk::{Chunk, RowId};
use re_log;
use re_log_encoding::rrd::{Encoder, EncodingOptions};
use re_log_msg::{LogMsg, SetStoreInfo, StoreInfo, StoreSource};
use re_log_types::{
    ApplicationId, EntityPath, NonMinI64, StoreId, StoreKind, TimeCell, TimePoint, Timeline,
    TimelineName,
};

use crate::cli::{ImportArgs, TimeCellKind};
use crate::conv::{ConversionOptions, log_changes_to_chunks_with_options};
use crate::log::{EntryLog, Timestamp};
use crate::wpilog::parse::{Payload, WpiLogFile, WpiRecord};

/// Options that control how a WPI log becomes a Rerun recording.
///
/// This replaces the old dependency on the embedded viewer's `ImporterSettings`; the wasm entry
/// point uses [`ImportOptions::default`] while the console importer fills it from its flags.
#[derive(Clone, Debug)]
pub struct ImportOptions {
    pub application_id: String,
    pub recording_id: Option<String>,
    pub recording_name: Option<String>,
    pub store_source: String,
    pub entity_path_prefix: Option<EntityPath>,
    /// Extra timeline cells attached to every row (the importer's `--time*` flags).
    pub timepoint: Option<TimePoint>,
    /// Added to every WPILog timestamp, in nanoseconds.
    pub timestamp_offset_ns: Option<i64>,
    /// Import every row on the static timeline.
    pub static_: bool,
    /// Add static [`re_sdk_types::archetypes::ViewCoordinates`] for WPILib's FLU frame.
    pub view_coordinates: bool,
}

impl Default for ImportOptions {
    fn default() -> Self {
        Self {
            application_id: "FIRSTrun".to_owned(),
            recording_id: None,
            recording_name: Some("FIRSTrun WPI log".to_owned()),
            store_source: "WpiLog".to_owned(),
            entity_path_prefix: None,
            timepoint: None,
            timestamp_offset_ns: None,
            static_: false,
            view_coordinates: true,
        }
    }
}

impl ImportOptions {
    /// Builds import options from parsed importer arguments.
    #[must_use]
    pub fn from_args(args: &ImportArgs) -> Self {
        let mut options = Self {
            application_id: args.application_id.clone(),
            recording_id: args.recording_id.clone(),
            entity_path_prefix: args
                .entity_path_prefix
                .as_deref()
                .map(|prefix| EntityPath::from_file_path(Path::new(prefix))),
            static_: args.static_ || args.timeless,
            ..Self::default()
        };

        if !args.time_cells.is_empty() {
            let mut timepoint = TimePoint::default();
            for cell in &args.time_cells {
                let nanos = NonMinI64::new(cell.value).unwrap_or_default();
                let (timeline, cell_value) = match cell.kind {
                    TimeCellKind::Sequence => (
                        Timeline::new_sequence(
                            TimelineName::try_new(cell.name.as_str()).expect("non-empty timeline"),
                        ),
                        TimeCell::from_sequence(cell.value),
                    ),
                    TimeCellKind::DurationNanos => (
                        Timeline::new_duration(
                            TimelineName::try_new(cell.name.as_str()).expect("non-empty timeline"),
                        ),
                        TimeCell::from_duration_nanos(nanos),
                    ),
                    TimeCellKind::TimestampNanos => (
                        Timeline::new_timestamp(
                            TimelineName::try_new(cell.name.as_str()).expect("non-empty timeline"),
                        ),
                        TimeCell::from_timestamp_nanos_since_epoch(nanos),
                    ),
                };
                timepoint.insert_cell(timeline.name().as_str(), cell_value);
            }
            options.timepoint = Some(timepoint);
        }

        options
    }

    fn store_id(&self) -> StoreId {
        let application_id = ApplicationId::try_new(self.application_id.as_str())
            .unwrap_or_else(|_| ApplicationId::from_static_str("FIRSTrun"));
        let id = StoreId::random(StoreKind::Recording, application_id);
        match &self.recording_id {
            Some(recording_id) => id.with_recording_id(recording_id.clone()),
            None => id,
        }
    }

    fn conversion_options(&self) -> ConversionOptions {
        ConversionOptions {
            entity_path_prefix: self.entity_path_prefix.clone(),
            timepoint: self.timepoint.clone(),
            timestamp_offset_ns: self.timestamp_offset_ns,
            recording_name: self.recording_name.clone(),
            view_coordinates: self.view_coordinates,
        }
    }
}

pub(crate) struct EntryContext<'log> {
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

pub(crate) fn fill_log<'file>(
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

/// Parses a WPI log into chunks, plus the timeline its timestamps belong to.
///
/// The `robotime` timeline keeps the WPILog epoch (microseconds since the RIO was enabled); it is
/// deliberately not renamed to match the live bridge's `robot_time`.
pub fn parse_wpilog(
    contents: &[u8],
    options: &ImportOptions,
) -> anyhow::Result<(Vec<Chunk>, Timeline)> {
    if !WpiLogFile::is_wpilog(contents) {
        anyhow::bail!("the selected file is not a WPI log");
    }

    let timeline = Timeline::new_duration("robotime");
    let conversion = options.conversion_options();
    let mut contexts = HashMap::new();
    let mut logger = EntryLog::new();

    // Stream the records in: keeping them would hold a second copy of the whole log's metadata
    // for nothing, since only the callback's side effects are used.
    WpiLogFile::parse_streaming(contents, |record| {
        if let Err(error) = fill_log(&mut contexts, &mut logger, record) {
            re_log::warn!("failed to parse WPI log entry: {error}");
        }
    })
    .map_err(|error| anyhow::anyhow!("failed to parse WPI log: {error}"))?;

    let mut chunks = log_changes_to_chunks_with_options(timeline, &mut logger, &conversion);
    if options.static_ {
        chunks = chunks.into_iter().map(Chunk::into_static).collect();
    }
    Ok((chunks, timeline))
}

/// Imports a WPI log into an in-memory RRD byte stream.
pub fn import_to_rrd(contents: &[u8]) -> anyhow::Result<Vec<u8>> {
    import_to_rrd_with_options(contents, &ImportOptions::default())
}

/// Imports a WPI log into an in-memory RRD byte stream with explicit options.
pub fn import_to_rrd_with_options(
    contents: &[u8],
    options: &ImportOptions,
) -> anyhow::Result<Vec<u8>> {
    let (chunks, _) = parse_wpilog(contents, options)?;
    let store_id = options.store_id();

    let mut output = Vec::new();
    {
        let mut encoder = Encoder::new_eager(
            CrateVersion::LOCAL,
            EncodingOptions::PROTOBUF_COMPRESSED,
            &mut output,
        )?;
        write_store_and_chunks(&mut encoder, &store_id, options, &chunks)?;
        encoder.finish()?;
    }
    Ok(output)
}

fn write_store_and_chunks<W: std::io::Write>(
    encoder: &mut Encoder<W>,
    store_id: &StoreId,
    options: &ImportOptions,
    chunks: &[Chunk],
) -> anyhow::Result<()> {
    encoder.append(&LogMsg::SetStoreInfo(SetStoreInfo {
        row_id: RowId::new().as_tuid(),
        info: StoreInfo::new(
            store_id.clone(),
            StoreSource::Other(options.store_source.clone()),
        ),
    }))?;
    for chunk in chunks {
        encoder.append(&LogMsg::ArrowMsg(store_id.clone(), chunk.to_arrow_msg()?))?;
    }
    Ok(())
}

/// Console entry point for the external-importer protocol.
///
/// Returns exit code 66 *before writing anything* when the input is not a WPI log. All diagnostics
/// go to stderr through `re_log`; stdout only ever contains RRD bytes.
#[cfg(not(target_arch = "wasm32"))]
pub fn run_cli(args: &ImportArgs) -> std::process::ExitCode {
    use std::io::BufWriter;

    let contents = match std::fs::read(&args.path) {
        Ok(contents) => contents,
        Err(error) => {
            eprintln!("firstrun: failed to read {}: {error}", args.path.display());
            return std::process::ExitCode::FAILURE;
        }
    };

    if !WpiLogFile::is_wpilog(&contents) {
        re_log::debug!(
            "{} is not a WPI log; leaving it to other importers",
            args.path.display()
        );
        return std::process::ExitCode::from(66);
    }

    let options = ImportOptions::from_args(args);
    let (chunks, _) = match parse_wpilog(&contents, &options) {
        Ok(parsed) => parsed,
        Err(error) => {
            eprintln!(
                "firstrun: failed to import {}: {error}",
                args.path.display()
            );
            return std::process::ExitCode::FAILURE;
        }
    };

    let stdout = std::io::stdout();
    let mut writer = BufWriter::new(stdout.lock());
    let store_id = options.store_id();
    let result = (|| -> anyhow::Result<()> {
        {
            let mut encoder = Encoder::new_eager(
                CrateVersion::LOCAL,
                EncodingOptions::PROTOBUF_COMPRESSED,
                &mut writer,
            )?;
            write_store_and_chunks(&mut encoder, &store_id, &options, &chunks)?;
            encoder.finish()?;
        }
        std::io::Write::flush(&mut writer)?;
        Ok(())
    })();

    match result {
        Ok(()) => std::process::ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("firstrun: failed to write RRD: {error}");
            std::process::ExitCode::FAILURE
        }
    }
}

/// Builds the standard timepoint cell constructor used by tests and the live bridge.
#[must_use]
pub fn duration_cell(nanos: i64) -> TimeCell {
    TimeCell::from_duration_nanos(NonMinI64::new(nanos).unwrap_or_default())
}

#[cfg(test)]
mod tests {
    use super::{ImportOptions, parse_wpilog};
    use re_log_types::{EntityPath, Timeline};

    const FIXTURE: &[u8] = include_bytes!("../test_data/FRC_TBD_d225b5377c70a88d.wpilog");

    #[test]
    fn rejects_non_wpilog_data() {
        assert!(parse_wpilog(b"not a WPI log", &ImportOptions::default()).is_err());
    }

    #[test]
    fn applies_entity_prefix_and_static_import() {
        let options = ImportOptions {
            entity_path_prefix: Some(EntityPath::from("robot")),
            static_: true,
            ..ImportOptions::default()
        };
        let (chunks, timeline) = parse_wpilog(FIXTURE, &options).unwrap();
        assert_eq!(timeline, Timeline::new_duration("robotime"));
        assert!(!chunks.is_empty());
        assert!(chunks.iter().all(|chunk| chunk.is_static()));
        assert!(
            chunks
                .iter()
                .any(|chunk| chunk.entity_path().to_string().starts_with("/robot/"))
        );
    }

    #[test]
    fn applies_a_timestamp_offset() {
        let options = ImportOptions {
            timestamp_offset_ns: Some(1_000_000),
            ..ImportOptions::default()
        };
        let (chunks, timeline) = parse_wpilog(FIXTURE, &options).unwrap();
        assert!(!chunks.is_empty());
        assert_eq!(timeline.name().as_str(), "robotime");
    }
}
