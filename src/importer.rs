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
            application_id: "moneyshift".to_owned(),
            recording_id: None,
            recording_name: Some("moneyshift WPI log".to_owned()),
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
            .unwrap_or_else(|_| ApplicationId::from_static_str("moneyshift"));
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
        write_store_and_chunks(&mut encoder, &store_id, options, &chunks, &[])?;
        encoder.finish()?;
    }
    Ok(output)
}

fn write_store_and_chunks<W: std::io::Write>(
    encoder: &mut Encoder<W>,
    store_id: &StoreId,
    options: &ImportOptions,
    chunks: &[Chunk],
    messages: &[LogMsg],
) -> anyhow::Result<()> {
    encoder.append(&LogMsg::SetStoreInfo(SetStoreInfo {
        row_id: RowId::new().as_tuid(),
        info: StoreInfo::new(
            store_id.clone(),
            StoreSource::Other(options.store_source.clone()),
        ),
    }))?;
    for message in messages {
        // The layout is logged through the SDK, whose own store info this store already wrote.
        if let LogMsg::SetStoreInfo(info) = message
            && info.info.store_id == *store_id
        {
            continue;
        }
        encoder.append(message)?;
    }
    for chunk in chunks {
        encoder.append(&LogMsg::ArrowMsg(store_id.clone(), chunk.to_arrow_msg()?))?;
    }
    Ok(())
}

/// Imports a log with the layout `moneyshift.toml` above it declares, if there is one.
///
/// The config file is looked up from the log's own directory upwards, so one file at the top of a
/// session's log folder applies to every log in it.
#[cfg(not(target_arch = "wasm32"))]
pub fn import_with_layout(
    contents: &[u8],
    options: &ImportOptions,
    log: &Path,
) -> anyhow::Result<Vec<u8>> {
    let (chunks, _) = parse_wpilog(contents, options)?;
    let messages = layout_messages(contents, options, log)?;
    let store_id = options.store_id();

    let mut output = Vec::new();
    {
        let mut encoder = Encoder::new_eager(
            CrateVersion::LOCAL,
            EncodingOptions::PROTOBUF_COMPRESSED,
            &mut output,
        )?;
        write_store_and_chunks(&mut encoder, &store_id, options, &chunks, &messages)?;
        encoder.finish()?;
    }
    Ok(output)
}

/// Draws the layout the config file above `log` declares, if there is one.
#[cfg(not(target_arch = "wasm32"))]
fn layout_messages(
    contents: &[u8],
    options: &ImportOptions,
    log: &Path,
) -> anyhow::Result<Vec<LogMsg>> {
    let Some(config) = crate::config::Config::discover_for(log)? else {
        return Ok(Vec::new());
    };
    let Some(layout) = config.layout else {
        return Ok(Vec::new());
    };
    if let Some(source) = &config.source {
        re_log::info!("drawing the layout from {}", source.display());
    }
    draw_layout(contents, options, layout)
}

/// Replays the log through the [`crate::first::Overlay`] the live bridge draws with.
///
/// The overlay is fed exactly what the bridge feeds it — a pose, swerve states or a path on a
/// declared topic — and each frame is drawn at the log's own timestamp, so an imported recording
/// shows the field and the robots the way a live session does.
#[cfg(not(target_arch = "wasm32"))]
fn draw_layout(
    contents: &[u8],
    options: &ImportOptions,
    layout: crate::first::Layout,
) -> anyhow::Result<Vec<LogMsg>> {
    use crate::first;

    // The layout goes through an SDK stream so it is drawn by the same code as a live session;
    // the memory sink hands the messages back for the importer's own encoder.
    let store_id = options.store_id();
    let (recording, storage) = re_sdk::RecordingStreamBuilder::from_store_id(&store_id)
        .recording_name(options.recording_name.clone().unwrap_or_default())
        .memory()?;

    let mut overlay = first::Overlay::new();
    overlay.set_layout(Some(layout));
    overlay.draw_static(&recording, first::Models::default(), None)?;
    overlay.draw_blueprint(&recording, None)?;

    let mut entries: HashMap<u32, (&str, &str)> = HashMap::new();
    WpiLogFile::parse_streaming(contents, |record| {
        let (entry_id, data) = match record.payload {
            Payload::Start {
                entry_id,
                entry_name,
                entry_type,
                ..
            } => {
                let name = entry_name.strip_prefix("NT:").unwrap_or(entry_name);
                entries.insert(entry_id, (name, entry_type));
                return;
            }
            Payload::Raw { entry_id, data } => (entry_id, data),
            _ => return,
        };
        let Some((name, ty)) = entries.get(&entry_id) else {
            return;
        };

        // The overlay decides what a declared topic carries, exactly as it does for a live
        // session; the log only supplies the bytes.
        let time = layout_time(record.timestamp);
        match overlay.feed_log(name, ty, data) {
            Ok(true) => {
                for error in overlay.flush(&recording, LAYOUT_TIMELINE, time, None) {
                    re_log::warn!("{error:#}");
                }
            }
            Ok(false) => {}
            Err(error) => re_log::warn!("ignoring the value on {name}: {error:#}"),
        }
    })
    .map_err(|error| anyhow::anyhow!("failed to read the log's records: {error}"))?;

    recording.flush_blocking()?;
    let messages = storage.take();
    Ok(if options.static_ {
        // An all-static import stays all-static, layout included: the SDK's own recording
        // properties are chunks like any other.
        messages.into_iter().map(make_static).collect()
    } else {
        messages
    })
}

/// Rewrites a message's chunk onto the static timeline, leaving other messages alone.
#[cfg(not(target_arch = "wasm32"))]
fn make_static(message: LogMsg) -> LogMsg {
    let LogMsg::ArrowMsg(store, arrow) = message else {
        return message;
    };
    match Chunk::from_arrow_msg(&arrow) {
        Ok(chunk) => LogMsg::ArrowMsg(store, chunk.into_static().to_arrow_msg().unwrap_or(arrow)),
        Err(_) => LogMsg::ArrowMsg(store, arrow),
    }
}

/// The timeline the config file's layout is drawn on: the same one the log itself uses.
#[cfg(not(target_arch = "wasm32"))]
const LAYOUT_TIMELINE: &str = "robotime";

/// A log timestamp (microseconds since the RIO started) as the importer's timeline cell.
#[cfg(not(target_arch = "wasm32"))]
fn layout_time(timestamp: Timestamp) -> TimeCell {
    let nanos = i64::try_from(i128::from(timestamp.0) * 1_000).unwrap_or(i64::MAX);
    duration_cell(nanos)
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
            eprintln!(
                "moneyshift: failed to read {}: {error}",
                args.path.display()
            );
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

    // No separate validation pass: `import_with_layout` parses into memory before anything is
    // written to stdout, so a broken log still fails without emitting a partial RRD — and the
    // log is read once instead of twice.
    let options = ImportOptions::from_args(args);
    let rrd = match import_with_layout(&contents, &options, &args.path) {
        Ok(rrd) => rrd,
        Err(error) => {
            eprintln!(
                "moneyshift: failed to import {}: {error}",
                args.path.display()
            );
            return std::process::ExitCode::FAILURE;
        }
    };

    let stdout = std::io::stdout();
    let mut writer = BufWriter::new(stdout.lock());
    match std::io::Write::write_all(&mut writer, &rrd)
        .and_then(|()| std::io::Write::flush(&mut writer))
    {
        Ok(()) => std::process::ExitCode::SUCCESS,
        Err(error) => {
            eprintln!("moneyshift: failed to write RRD: {error}");
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

    #[cfg(not(target_arch = "wasm32"))]
    #[test]
    fn draws_the_layout_the_config_above_the_log_declares() {
        use crate::config;

        let root = std::env::temp_dir().join(format!("moneyshift-import-{}", std::process::id()));
        let logs = root.join("logs");
        std::fs::create_dir_all(&logs).unwrap();
        std::fs::write(
            root.join(config::DEFAULT_PATH),
            "override = true\n\
             [layout]\n\
             version = 1\n\
             [layout.field]\n\
             carpet = true\n\
             [[layout.robots]]\n\
             team = 254\n\
             pose_topic = \"/Pose\"\n\
             half_extents = [0.45, 0.45, 0.3]\n",
        )
        .unwrap();
        let log = logs.join("match.wpilog");
        std::fs::write(&log, FIXTURE).unwrap();

        let rrd = super::import_with_layout(FIXTURE, &super::ImportOptions::default(), &log)
            .expect("the log imports with the layout above it");

        let mut decoder: re_log_encoding::rrd::Decoder<re_log_msg::LogMsg> =
            re_log_encoding::rrd::Decoder::new();
        decoder.push_byte_chunk(rrd);
        let mut entities = Vec::new();
        while let Some(message) = decoder.try_read().unwrap() {
            if let re_log_msg::LogMsg::ArrowMsg(_, arrow) = message
                && let Ok(chunk) = re_chunk::Chunk::from_arrow_msg(&arrow)
            {
                entities.push(chunk.entity_path().to_string());
            }
        }
        let _ = std::fs::remove_dir_all(&root);

        assert!(
            entities
                .iter()
                .any(|entity| entity == "/first/field/carpet"),
            "the config file's field is drawn, got {entities:?}"
        );
        assert!(
            entities
                .iter()
                .any(|entity| entity == "/first/robots/254/model"),
            "the config file's robot is drawn, got {entities:?}"
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
