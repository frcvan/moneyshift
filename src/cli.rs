//! Hand-written command-line parsing for `rerun-importer-firstrun`.
//!
//! There are two modes: the Rerun external-importer mode (a WPI log path plus the flags the Rerun
//! viewer passes) and the live NetworkTables bridge (`--nt`). The parser deliberately has no
//! dependencies so it is cheap to unit-test and keeps the binary small.

use std::net::Ipv4Addr;
use std::path::PathBuf;

/// Which timeline cell a `--time*` flag describes.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum TimeCellKind {
    Sequence,
    DurationNanos,
    /// Nanoseconds since the Unix epoch (`--time_timestamp_nanos`, new in Rerun 0.40).
    TimestampNanos,
}

/// One `--time_sequence name=value`-style cell.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct TimeCellArg {
    pub name: String,
    pub value: i64,
    pub kind: TimeCellKind,
}

/// Arguments for the external-importer mode.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct ImportArgs {
    pub path: PathBuf,
    /// `--application-id`, defaults to `FIRSTrun`.
    pub application_id: String,
    /// `--recording-id`, if the viewer assigned one.
    pub recording_id: Option<String>,
    pub entity_path_prefix: Option<String>,
    pub static_: bool,
    pub timeless: bool,
    pub time_cells: Vec<TimeCellArg>,
}

/// Where the live bridge sends its data.
#[derive(Clone, Debug, PartialEq, Eq)]
pub enum LiveSink {
    /// Spawn the `rerun` executable on `PATH`.
    Spawn,
    /// Connect to a running viewer at this URL.
    Connect(String),
    /// Write an RRD file.
    Save(PathBuf),
    /// Stream RRD to stdout.
    Stdout,
}

/// Arguments for the live NetworkTables mode.
#[derive(Clone, Debug, PartialEq, Eq)]
pub struct LiveArgs {
    pub team: Option<u16>,
    pub nt_addr: Option<Ipv4Addr>,
    /// Robot-time source topic; seconds (`double`) or microseconds (`int`).
    pub robot_time_topic: Option<String>,
    pub sink: LiveSink,
    pub quiet: bool,
    /// Prefix applied to every mapped entity (mostly for tests and recordings).
    pub entity_path_prefix: Option<String>,
    /// A model file that replaces the declaration's field model.
    pub field_model: Option<PathBuf>,
    /// A model file that replaces every robot's bounding box.
    pub robot_model: Option<PathBuf>,
}

#[derive(Clone, Debug, PartialEq, Eq)]
pub enum Command {
    Import(ImportArgs),
    Live(LiveArgs),
    Help,
    Version,
}

#[derive(Clone, Debug, PartialEq, Eq)]
pub struct CliError(pub String);

impl std::fmt::Display for CliError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.write_str(&self.0)
    }
}

impl std::error::Error for CliError {}

impl ImportArgs {
    fn new(path: PathBuf) -> Self {
        Self {
            path,
            application_id: "FIRSTrun".to_owned(),
            recording_id: None,
            entity_path_prefix: None,
            static_: false,
            timeless: false,
            time_cells: Vec::new(),
        }
    }
}

impl LiveArgs {
    fn new() -> Self {
        Self {
            team: None,
            nt_addr: None,
            robot_time_topic: None,
            sink: LiveSink::Spawn,
            quiet: false,
            entity_path_prefix: None,
            field_model: None,
            robot_model: None,
        }
    }
}

/// Parses the process arguments (including the program name).
pub fn parse<I, S>(args: I) -> Result<Command, CliError>
where
    I: IntoIterator<Item = S>,
    S: Into<String>,
{
    let mut args = args.into_iter().map(Into::into);
    let _program = args.next();

    let mut positional: Vec<String> = Vec::new();
    let mut live = false;
    let mut sink: Option<LiveSink> = None;
    let mut import: Option<ImportArgs> = None;
    let mut live_args = LiveArgs::new();
    let mut team: Option<u16> = None;
    let mut nt_addr: Option<Ipv4Addr> = None;

    while let Some(arg) = args.next() {
        let mut next_value = |flag: &str| -> Result<String, CliError> {
            args.next()
                .ok_or_else(|| CliError(format!("{flag} needs a value")))
        };

        match arg.as_str() {
            "-h" | "--help" => return Ok(Command::Help),
            "--version" | "-V" => return Ok(Command::Version),
            "--nt" => live = true,
            "--quiet" | "-q" => live_args.quiet = true,
            "--static" => {
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .static_ = true;
            }
            "--timeless" => {
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .timeless = true;
            }
            "--application-id" | "--opened-application-id" => {
                let value = next_value(&arg)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .application_id = value;
            }
            "--recording-id" | "--opened-recording-id" => {
                let value = next_value(&arg)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .recording_id = Some(value);
            }
            "--entity-path-prefix" => {
                let value = next_value(&arg)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .entity_path_prefix = Some(value.clone());
                live_args.entity_path_prefix = Some(value);
            }
            "--time_sequence" | "--sequence" => {
                let cell = parse_time_cell(&arg, &next_value(&arg)?, TimeCellKind::Sequence)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .time_cells
                    .push(cell);
            }
            "--time_duration_nanos" | "--time" => {
                let cell = parse_time_cell(&arg, &next_value(&arg)?, TimeCellKind::DurationNanos)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .time_cells
                    .push(cell);
            }
            "--time_timestamp_nanos" => {
                let cell = parse_time_cell(&arg, &next_value(&arg)?, TimeCellKind::TimestampNanos)?;
                import
                    .get_or_insert_with(|| ImportArgs::new(PathBuf::new()))
                    .time_cells
                    .push(cell);
            }
            "--team" => {
                let value = next_value(&arg)?;
                team = Some(
                    value
                        .parse()
                        .map_err(|_| CliError(format!("--team needs a number, got {value:?}")))?,
                );
            }
            "--nt-addr" => {
                let value = next_value(&arg)?;
                nt_addr = Some(value.parse().map_err(|_| {
                    CliError(format!("--nt-addr needs an IPv4 address, got {value:?}"))
                })?);
            }
            "--robot-time-topic" => {
                live_args.robot_time_topic = Some(next_value(&arg)?);
            }
            "--field-model" => {
                live_args.field_model = Some(PathBuf::from(next_value(&arg)?));
            }
            "--robot-model" => {
                live_args.robot_model = Some(PathBuf::from(next_value(&arg)?));
            }
            "--spawn" => set_sink(&mut sink, LiveSink::Spawn)?,
            "--stdout" => set_sink(&mut sink, LiveSink::Stdout)?,
            "--connect" => set_sink(&mut sink, LiveSink::Connect(next_value(&arg)?))?,
            "--save" => set_sink(&mut sink, LiveSink::Save(PathBuf::from(next_value(&arg)?)))?,
            other if other.starts_with('-') && other != "-" => {
                return Err(CliError(format!("unknown flag {other:?}")));
            }
            other => positional.push(other.to_owned()),
        }
    }

    if live {
        live_args.team = team;
        live_args.nt_addr = nt_addr;
        if team.is_some() && nt_addr.is_some() {
            return Err(CliError(
                "--team and --nt-addr are mutually exclusive".to_owned(),
            ));
        }
        if let Some(sink) = sink {
            live_args.sink = sink;
        }
        return Ok(Command::Live(live_args));
    }

    if team.is_some()
        || nt_addr.is_some()
        || sink.is_some()
        || live_args.field_model.is_some()
        || live_args.robot_model.is_some()
    {
        return Err(CliError(
            "--team, --nt-addr, --field-model, --robot-model and the sink flags need --nt"
                .to_owned(),
        ));
    }

    let mut import = import.unwrap_or_else(|| ImportArgs::new(PathBuf::new()));
    match positional.len() {
        0 => {}
        1 => import.path = PathBuf::from(positional.pop().expect("one positional")),
        _ => {
            return Err(CliError(format!(
                "expected one log path, got {}",
                positional.len()
            )));
        }
    }
    Ok(Command::Import(import))
}

fn set_sink(sink: &mut Option<LiveSink>, value: LiveSink) -> Result<(), CliError> {
    if sink.is_some() {
        return Err(CliError(
            "--spawn, --connect, --save and --stdout are mutually exclusive".to_owned(),
        ));
    }
    *sink = Some(value);
    Ok(())
}

fn parse_time_cell(flag: &str, value: &str, kind: TimeCellKind) -> Result<TimeCellArg, CliError> {
    let (name, value) = value
        .split_once('=')
        .ok_or_else(|| CliError(format!("{flag} expects name=value, got {value:?}")))?;
    if name.is_empty() {
        return Err(CliError(format!("{flag} needs a timeline name")));
    }
    let value = value
        .parse()
        .map_err(|_| CliError(format!("{flag} expects an integer value, got {value:?}")))?;
    Ok(TimeCellArg {
        name: name.to_owned(),
        value,
        kind,
    })
}

pub fn usage() -> &'static str {
    concat!(
        "FIRSTrun: import FRC robot telemetry into Rerun\n",
        "\n",
        "Usage:\n",
        "  rerun-importer-firstrun <path.wpilog> [importer flags]\n",
        "  rerun-importer-firstrun --nt [--team <n> | --nt-addr <ip>] [options]\n",
        "\n",
        "Importer flags (passed by the Rerun viewer):\n",
        "  --application-id <id>        application id for the recording\n",
        "  --recording-id <id>          recording id for the recording\n",
        "  --entity-path-prefix <path>  prefix every entity with this path\n",
        "  --static --timeless          import all data as static\n",
        "  --time_sequence <name=value> add a sequence timeline cell to every row\n",
        "  --time_duration_nanos <name=value>\n",
        "                               add a duration timeline cell to every row\n",
        "  --time_timestamp_nanos <name=value>\n",
        "                               add a timestamp timeline cell to every row\n",
        "\n",
        "Live NetworkTables flags:\n",
        "  --nt                         run the live NT -> Rerun bridge\n",
        "  --team <n>                   connect to the NT server of team <n>\n",
        "  --nt-addr <ip>               connect to a specific NT server\n",
        "  --robot-time-topic <topic>   use this topic as the robot_time source\n",
        "  --field-model <path>         draw this glTF/GLB/OBJ/STL field model instead of the\n",
        "                               one the declaration names\n",
        "  --robot-model <path>         draw this model for every robot instead of a box\n",
        "  --spawn                      start a viewer (default)\n",
        "  --connect <url>              send to a running viewer\n",
        "  --save <path.rrd>            write a recording instead\n",
        "  --stdout                     write RRD to stdout\n",
        "  --quiet                      only log warnings and errors\n",
        "\n",
        "Exit codes: 66 means \"this file is not a WPI log\"; 2 means bad arguments.\n",
    )
}

#[cfg(test)]
mod tests {
    use super::{Command, LiveSink, TimeCellKind, parse, usage};
    use std::path::PathBuf;

    fn cmd(args: &[&str]) -> Result<Command, super::CliError> {
        parse(std::iter::once("rerun-importer-firstrun").chain(args.iter().copied()))
    }

    #[test]
    fn parses_a_plain_wpilog_path() {
        let Ok(Command::Import(args)) = cmd(&["log.wpilog"]) else {
            panic!("expected importer mode");
        };
        assert_eq!(args.path, PathBuf::from("log.wpilog"));
        assert_eq!(args.application_id, "FIRSTrun");
        assert!(!args.static_);
    }

    #[test]
    fn parses_viewer_importer_flags() {
        let Ok(Command::Import(args)) = cmd(&[
            "--application-id",
            "my-app",
            "--recording-id",
            "rec-1",
            "--entity-path-prefix",
            "robot",
            "--static",
            "--time_sequence",
            "frame=3",
            "--time",
            "sim=1000",
            "--time_timestamp_nanos",
            "wall=1700000000000000000",
            "log.wpilog",
        ]) else {
            panic!("expected importer mode");
        };
        assert_eq!(args.application_id, "my-app");
        assert_eq!(args.recording_id.as_deref(), Some("rec-1"));
        assert_eq!(args.entity_path_prefix.as_deref(), Some("robot"));
        assert!(args.static_);
        assert_eq!(args.time_cells.len(), 3);
        assert_eq!(args.time_cells[0].kind, TimeCellKind::Sequence);
        assert_eq!(args.time_cells[0].name, "frame");
        assert_eq!(args.time_cells[1].kind, TimeCellKind::DurationNanos);
        assert_eq!(args.time_cells[1].value, 1000);
        assert_eq!(args.time_cells[2].kind, TimeCellKind::TimestampNanos);
        assert_eq!(args.time_cells[2].value, 1_700_000_000_000_000_000);
        assert_eq!(args.path, PathBuf::from("log.wpilog"));
    }

    #[test]
    fn parses_live_mode() {
        let Ok(Command::Live(args)) = cmd(&["--nt", "--team", "254", "--save", "out.rrd"]) else {
            panic!("expected live mode");
        };
        assert_eq!(args.team, Some(254));
        assert_eq!(args.sink, LiveSink::Save(PathBuf::from("out.rrd")));
        assert!(!args.quiet);
    }

    #[test]
    fn parses_custom_model_flags() {
        let Ok(Command::Live(args)) = cmd(&[
            "--nt",
            "--field-model",
            "field.glb",
            "--robot-model",
            "robot.glb",
        ]) else {
            panic!("expected live mode");
        };
        assert_eq!(args.field_model, Some(PathBuf::from("field.glb")));
        assert_eq!(args.robot_model, Some(PathBuf::from("robot.glb")));

        // Only the live bridge draws a field, so the importer mode rejects them.
        assert!(cmd(&["--field-model", "field.glb", "log.wpilog"]).is_err());
        assert!(cmd(&["--robot-model", "robot.glb", "log.wpilog"]).is_err());
    }

    #[test]
    fn live_mode_defaults_to_spawning_a_viewer() {
        let Ok(Command::Live(args)) = cmd(&["--nt"]) else {
            panic!("expected live mode");
        };
        assert_eq!(args.sink, LiveSink::Spawn);
        assert_eq!(args.team, None);
    }

    #[test]
    fn rejects_conflicting_sinks_and_network_targets() {
        assert!(cmd(&["--nt", "--save", "a.rrd", "--stdout"]).is_err());
        assert!(cmd(&["--nt", "--team", "1", "--nt-addr", "10.0.0.2"]).is_err());
        assert!(cmd(&["--team", "1"]).is_err());
    }

    #[test]
    fn rejects_unknown_flags_and_bad_values() {
        assert!(cmd(&["--bogus"]).is_err());
        assert!(cmd(&["--time_sequence", "frame"]).is_err());
        assert!(cmd(&["--team", "not-a-number"]).is_err());
        assert!(cmd(&["one.wpilog", "two.wpilog"]).is_err());
    }

    #[test]
    fn help_and_version_are_recognized() {
        assert_eq!(cmd(&["--help"]).unwrap(), Command::Help);
        assert_eq!(cmd(&["-h"]).unwrap(), Command::Help);
        assert_eq!(cmd(&["--version"]).unwrap(), Command::Version);
        assert!(usage().contains("--nt"));
    }
}
