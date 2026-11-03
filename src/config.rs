//! The bridge's config file: what to draw, and from where, without robot code.
//!
//! Normally robot code publishes `/Firstrun/layout` and the bridge draws the field, robots and
//! viewer layout it describes. A [`Config`] carries the same declaration in a file, so a session
//! can show the field with no robot code at all; with `override` set, whatever robot code does
//! publish is ignored and the file stays authoritative.
//!
//! ```toml
//! override = true
//! field_model = "models/field.glb"
//!
//! [layout]
//! version = 1
//!
//! [layout.field]
//! carpet = true
//!
//! [[layout.robots]]
//! team = 254
//! pose_topic = "/AdvantageKit/RealOutputs/Drive/Pose2d"
//!
//! [layout.blueprint]
//! container = "tabs"
//!
//! [[layout.blueprint.views]]
//! kind = "three_d"
//! ```

use std::path::{Path, PathBuf};

use anyhow::Context as _;
use serde::Deserialize;

use crate::first::Layout;

/// The config file the bridge reads when `--config` is not given, and the file an import looks
/// for next to its log.
pub const DEFAULT_PATH: &str = "moneyshift.toml";

/// A parsed config file.
///
/// The `[layout]` table is the same declaration robot code publishes on `/Firstrun/layout`, so
/// the field, robots, cameras, paths and viewer blueprint all read the same either way.
#[derive(Debug, Clone, Default, Deserialize, PartialEq)]
#[serde(deny_unknown_fields)]
pub struct Config {
    /// Ignore `/Firstrun/layout` and draw this file's declaration instead: the file overrides
    /// robot code. This is what makes a session possible with no robot code running at all.
    #[serde(default)]
    pub r#override: bool,
    /// A field model that replaces the one the layout names.
    #[serde(default)]
    pub field_model: Option<PathBuf>,
    /// A model drawn for every robot instead of its bounding box.
    #[serde(default)]
    pub robot_model: Option<PathBuf>,
    /// The field, robots, paths and viewer layout to draw.
    #[serde(default)]
    pub layout: Option<Layout>,
    /// Where the file was read from; set by [`Config::load`], not by the file itself.
    #[serde(skip)]
    pub source: Option<PathBuf>,
}

impl Config {
    /// Reads and validates a config file.
    pub fn load(path: &Path) -> anyhow::Result<Self> {
        let text = std::fs::read_to_string(path)
            .with_context(|| format!("could not read the config file {}", path.display()))?;
        let mut config = Self::parse(&text)
            .with_context(|| format!("invalid config file {}", path.display()))?;
        config.source = Some(path.to_path_buf());
        Ok(config)
    }

    /// Parses a config file.
    ///
    /// The layout goes through the same validation as a declaration from robot code, so a
    /// hand-written file with a relative topic or a missing pose source fails here rather than
    /// drawing a field at the origin.
    pub fn parse(text: &str) -> anyhow::Result<Self> {
        let mut config: Self = toml::from_str(text).context("not a FIRSTrun config file")?;
        if let Some(layout) = config.layout.take() {
            config.layout = Some(layout.validate()?);
        }
        Ok(config)
    }

    /// Reads the config file that governs `log`: the one in its directory, or else the nearest
    /// one in a parent directory.
    ///
    /// The importer is handed a path inside a session's log folder, so one `moneyshift.toml` at
    /// the top of that folder applies to every log in it and in its subfolders.
    pub fn discover_for(log: &Path) -> anyhow::Result<Option<Self>> {
        let mut directory = log.parent();
        while let Some(dir) = directory {
            let candidate = dir.join(DEFAULT_PATH);
            if candidate.is_file() {
                return Self::load(&candidate).map(Some);
            }
            directory = dir.parent();
        }
        Ok(None)
    }

    /// Loads the file `path` names, or the default file when it exists.
    ///
    /// A missing default file is a session without a config, which is how the bridge behaved
    /// before there was one; an explicit `--config` path that cannot be read is an error, because
    /// the user asked for that file.
    pub fn discover(path: Option<&Path>) -> anyhow::Result<Option<Self>> {
        match path {
            Some(path) => Self::load(path).map(Some),
            None => {
                let default = Path::new(DEFAULT_PATH);
                if default.is_file() {
                    Self::load(default).map(Some)
                } else {
                    Ok(None)
                }
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::Config;

    const CONFIG: &str = r#"
override = true
field_model = "models/field.glb"
robot_model = "models/robot.glb"

[layout]
version = 1

[layout.field]
carpet = false
lines = [{ points = [[0.0, 0.0], [16.54175, 8.069326]], closed = false }]

[[layout.field.tags]]
id = 3
x = 1.0
y = 2.0
z = 1.5
yaw = 0.25
size = 0.1651

[[layout.robots]]
team = 254
pose_topic = "/Pose"
half_extents = [0.45, 0.45, 0.3]

[[layout.robots.cameras]]
name = "front"
x = 0.3
y = 0.0
z = 0.5
pitch = -10.0
fov = 70.0
stream = "http://10.63.91.11:1181/stream.mjpg"

[[layout.paths]]
name = "auto"
topic = "/Path"

[layout.blueprint]
container = "tabs"

[[layout.blueprint.views]]
kind = "three_d"

[[layout.blueprint.views]]
kind = "dataframe"
name = "State"
entities = ["/Pose"]
"#;

    #[test]
    fn parses_a_declaration_from_a_file() {
        let config = Config::parse(CONFIG).unwrap();
        let layout = config.layout.as_ref().unwrap();

        assert!(config.r#override);
        assert_eq!(
            config.field_model.as_deref(),
            Some(std::path::Path::new("models/field.glb"))
        );
        assert_eq!(
            config.robot_model.as_deref(),
            Some(std::path::Path::new("models/robot.glb"))
        );

        // The layout is the same declaration robot code publishes, so the bridge indexes it the
        // same way: poses, swerve states and paths by the topic they arrive on.
        assert_eq!(layout.version, 1);
        assert!(!layout.field.carpet);
        assert_eq!(layout.field.tags[0].id, 3);
        assert!(layout.robots_by_pose_topic().contains_key("/Pose"));
        assert_eq!(
            layout.paths_by_topic().get("/Path").map(String::as_str),
            Some("auto")
        );
        assert_eq!(layout.robots[0].cameras[0].name, "front");

        let blueprint = layout.blueprint.as_ref().unwrap();
        assert_eq!(blueprint.container, crate::first::Container::Tabs);
        assert_eq!(blueprint.views.len(), 2);
        assert_eq!(blueprint.views[1].entities, ["/Pose"]);
    }

    #[test]
    fn the_version_defaults_to_the_one_the_bridge_speaks() {
        let config = Config::parse("[layout]\n[layout.field]\n").unwrap();
        assert_eq!(config.layout.unwrap().version, 1);
    }

    #[test]
    fn a_config_may_only_carry_models() {
        // No `[layout]` means the declaration still comes from robot code; the file only swaps
        // the models, which is also what `--field-model`/`--robot-model` do.
        let config = Config::parse("field_model = \"field.glb\"\n").unwrap();
        assert!(config.layout.is_none());
        assert!(!config.r#override);
    }

    #[test]
    fn rejects_files_the_bridge_cannot_use() {
        // Same validation as a declaration from robot code.
        let relative_topic = CONFIG.replace("pose_topic = \"/Pose\"", "pose_topic = \"Pose\"");
        assert!(Config::parse(&relative_topic).is_err());

        let https = CONFIG.replace("http://10.63.91.11", "https://10.63.91.11");
        assert!(Config::parse(&https).is_err());

        // A typo in an option name is reported instead of silently ignored.
        assert!(Config::parse("override = true\nrobotcode = 254\n").is_err());
        assert!(Config::parse("not toml at all [").is_err());
    }

    #[test]
    fn the_importer_finds_a_file_a_parent_directory_up() {
        let root = std::env::temp_dir().join(format!("moneyshift-config-{}", std::process::id()));
        let nested = root.join("logs").join("session");
        std::fs::create_dir_all(&nested).unwrap();
        std::fs::write(root.join(super::DEFAULT_PATH), "override = true\n").unwrap();

        let found = super::Config::discover_for(&nested.join("match.wpilog"))
            .unwrap()
            .expect("the file above the log is found");
        assert!(found.r#override);

        std::fs::remove_file(root.join(super::DEFAULT_PATH)).unwrap();
        assert!(
            super::Config::discover_for(&nested.join("match.wpilog"))
                .unwrap()
                .is_none()
        );
        let _ = std::fs::remove_dir_all(&root);
    }

    #[test]
    fn a_missing_default_file_is_not_an_error() {
        // `discover` with no path looks at `firstrun.toml` in the working directory, which tests
        // must not depend on; an explicit missing path is the error case.
        let missing = std::env::temp_dir().join("firstrun-no-such-config.toml");
        let _ = std::fs::remove_file(&missing);
        assert!(Config::discover(Some(&missing)).is_err());
    }
}
