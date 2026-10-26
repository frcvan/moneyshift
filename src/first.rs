//! The FIRST declaration the bridge reads from NetworkTables.
//!
//! Robot code publishes [`TOPIC`] once with [`FirstMetadata`] on the Java side. The declaration
//! says where each robot's pose comes from and what the field looks like; this module turns it
//! into static field geometry plus a per-frame robot transform, so robots show up on a field
//! without any Rerun-specific robot code.
//!
//! Everything is best-effort: a malformed declaration is reported once and ignored, and a field
//! model that cannot be read only loses that asset.

use std::collections::{HashMap, HashSet};
use std::path::Path as FsPath;

use anyhow::{Context as _, bail};
use nt_client::data::DataType;
use re_log_types::{EntityPath, TimeCell};
use re_sdk::RecordingStream;
use re_sdk::blueprint::{
    Blueprint, BlueprintActivation, BlueprintOpts, Grid, Spatial2DView, Spatial3DView,
};
use re_sdk_types::{
    Rotation3D,
    archetypes::{
        Arrows3D, Asset3D, Boxes2D, Boxes3D, EncodedImage, LineStrips2D, LineStrips3D, Pinhole,
        Transform3D, VideoStream, ViewCoordinates,
    },
    components::{
        FillMode, RotationAxisAngle, RotationQuat, Translation3D, VideoCodec,
        ViewCoordinates as ViewCoordinatesComponent,
    },
    encodings::{Angle, Quaternion},
};
use serde::Deserialize;

/// The plain NetworkTables topic robot code publishes the declaration on.
pub const TOPIC: &str = "/Firstrun/layout";

/// Root entity of the 3D field.
pub const ROOT_3D: &str = "first";

/// Root entity of the 2D field, in WPILib's field frame.
pub const ROOT_2D: &str = "first2d";

/// Length of an FRC carpet, in meters. The field size is fixed; only its contents are declared.
pub const FIELD_LENGTH_METERS: f64 = 16.54175;

/// Width of an FRC carpet, in meters.
pub const FIELD_WIDTH_METERS: f64 = 8.069326;

const CARPET_COLOR: u32 = 0x1B3A26FF;
const LINE_COLOR: u32 = 0xD9D9D9FF;
const TAG_COLOR: u32 = 0xFFB000FF;
const PATH_COLOR: u32 = 0x00B3FFFF;
const ROBOT_LINE_RADIUS: f32 = 0.02;
const PATH_RADIUS: f32 = 0.03;
/// Height above the carpet that field lines are drawn at, so they are not coplanar with a field
/// model's carpet and do not z-fight with it.
const FIELD_LINE_Z: f32 = 0.01;
/// How far above the robot's top face swerve vectors are drawn, so the robot body cannot hide
/// them.
const SWERVE_ARROW_LIFT: f32 = 0.05;
/// Arrow radius in meters; the shaft is drawn at half of it, so this is a 4 cm-thick arrow.
const SWERVE_ARROW_RADIUS: f32 = 0.04;
/// Seconds of travel drawn for a module at its measured speed.
const SWERVE_ARROW_SECONDS: f32 = 0.25;
const CAMERA_WIDTH: f32 = 1280.0;
const CAMERA_HEIGHT: f32 = 720.0;
/// How far from the camera the image plane, and with it the frustum, is drawn in 3D. A meter or
/// two keeps the frustum readable next to a 0.9 m robot on a 16 m field.
const CAMERA_IMAGE_PLANE_DISTANCE: f32 = 2.0;

/// A parsed `/Firstrun/layout` declaration.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Layout {
    pub version: u32,
    pub field: Field,
    #[serde(default)]
    pub robots: Vec<Robot>,
    #[serde(default)]
    pub paths: Vec<Path>,
}

impl Layout {
    /// Parses and validates a declaration published by `FirstMetadata`.
    pub fn parse(text: &str) -> anyhow::Result<Self> {
        let layout: Self = serde_json::from_str(text).context("not a FIRST declaration")?;
        if layout.version != 1 {
            bail!("unsupported declaration version {}", layout.version);
        }
        layout.field.validate()?;
        for robot in &layout.robots {
            if robot.team == 0 {
                bail!("team numbers are positive");
            }
            if !robot.pose_topic.starts_with('/') {
                bail!(
                    "team {}: pose topic \"{}\" is not absolute",
                    robot.team,
                    robot.pose_topic
                );
            }
            if robot
                .half_extents
                .iter()
                .any(|extent| !extent.is_finite() || *extent <= 0.0)
            {
                bail!("team {}: half extents must be positive", robot.team);
            }
        }
        let mut path_names = HashSet::new();
        for path in &layout.paths {
            if !path.topic.starts_with('/') {
                bail!("path \"{}\": topic is not absolute", path.name);
            }
            if !is_segment(&path.name) {
                bail!("path \"{}\" is not a single entity segment", path.name);
            }
            if !path_names.insert(path.name.as_str()) {
                bail!("path \"{}\" is declared twice", path.name);
            }
        }
        let mut streams = HashSet::new();
        for robot in &layout.robots {
            if let Some(swerve) = &robot.swerve {
                if !swerve.states_topic.starts_with('/') {
                    bail!("team {}: swerve topic is not absolute", robot.team);
                }
                if !swerve.module_offsets.is_empty() && swerve.module_offsets.len() != 4 {
                    bail!("team {}: declare four module offsets or none", robot.team);
                }
            }
            let mut camera_names = HashSet::new();
            for camera in &robot.cameras {
                if !is_segment(&camera.name) {
                    bail!("team {}: invalid camera name", robot.team);
                }
                if !camera_names.insert(camera.name.as_str()) {
                    bail!("team {}: duplicate camera \"{}\"", robot.team, camera.name);
                }
                if let Some(stream) = &camera.stream {
                    // PhotonVision serves MJPEG over plain HTTP; the bridge has no TLS client.
                    let Some(host) = stream.strip_prefix("http://") else {
                        bail!(
                            "team {}: camera \"{}\" stream must be an http:// MJPEG url",
                            robot.team,
                            camera.name
                        );
                    };
                    if host.is_empty() || host.starts_with('/') {
                        bail!(
                            "team {}: camera \"{}\" stream has no host",
                            robot.team,
                            camera.name
                        );
                    }
                    if !streams.insert(stream.as_str()) {
                        bail!("stream \"{stream}\" is declared twice");
                    }
                }
                if !(camera.fov > 0.0 && camera.fov < 180.0) {
                    bail!("team {}: camera fov out of range", robot.team);
                }
            }
        }
        Ok(layout)
    }

    /// The robots the declaration draws, keyed by their pose topic.
    #[must_use]
    pub fn robots_by_pose_topic(&self) -> HashMap<String, Robot> {
        self.robots
            .iter()
            .map(|robot| (robot.pose_topic.clone(), robot.clone()))
            .collect()
    }

    /// The robots that publish swerve states, keyed by their states topic.
    #[must_use]
    pub fn robots_by_swerve_topic(&self) -> HashMap<String, Robot> {
        self.robots
            .iter()
            .filter_map(|robot| {
                robot
                    .swerve
                    .as_ref()
                    .map(|swerve| (swerve.states_topic.clone(), robot.clone()))
            })
            .collect()
    }

    /// The declared paths, keyed by the topic they arrive on.
    #[must_use]
    pub fn paths_by_topic(&self) -> HashMap<String, String> {
        self.paths
            .iter()
            .map(|path| (path.topic.clone(), path.name.clone()))
            .collect()
    }

    /// Cameras that stream MJPEG, keyed by their stream URL.
    #[must_use]
    pub fn cameras_by_stream(&self) -> HashMap<String, (u16, Camera)> {
        self.robots
            .iter()
            .flat_map(|robot| {
                robot.cameras.iter().filter_map(|camera| {
                    camera
                        .stream
                        .as_ref()
                        .map(|url| (url.clone(), (robot.team, camera.clone())))
                })
            })
            .collect()
    }

    /// Logs the field and robot models as static data, as the declaration describes them.
    ///
    /// The whole field is timeless, so it survives scrubbing and costs one chunk per entity
    /// regardless of how long the bridge runs.
    pub fn log_static(
        &self,
        stream: &RecordingStream,
        prefix: Option<&EntityPath>,
    ) -> anyhow::Result<()> {
        self.log_static_with_models(stream, prefix, Models::default())
    }

    /// Logs the field and robot models, letting the caller replace the model files.
    pub fn log_static_with_models(
        &self,
        stream: &RecordingStream,
        prefix: Option<&EntityPath>,
        models: Models<'_>,
    ) -> anyhow::Result<()> {
        // FRC's frame is +z up; a 3D view needs to be told, or the field is rolled on its side.
        stream.log_static(
            at(prefix, ROOT_3D.to_owned()),
            &ViewCoordinates::RIGHT_HAND_Z_UP(),
        )?;
        self.field.log_static(stream, prefix, models.field)?;
        // One read for every robot; a broken file falls back to boxes so the robots stay visible.
        let robot_asset = match models.robot {
            Some(path) => match load_asset3d(path) {
                Ok(asset) => Some(asset),
                Err(error) => {
                    re_log::warn!("{error:#}; drawing bounding boxes instead");
                    None
                }
            },
            None => None,
        };
        for robot in &self.robots {
            log_cameras(stream, robot, prefix)?;
            let model_entity = prefixed(
                prefix,
                robot_entity(robot.team, ROOT_3D).join(&EntityPath::from("model")),
            );
            match &robot_asset {
                Some(asset) => stream.log_static(model_entity, &asset.clone())?,
                None => {
                    let (center, half_size) = robot_model(robot.half_extents);
                    stream.log_static(
                        model_entity,
                        &Boxes3D::from_centers_and_half_sizes([center], [half_size])
                            .with_colors([team_color(robot.team)])
                            .with_fill_mode(FillMode::Solid),
                    )?;
                }
            }
        }
        Ok(())
    }
}

/// Model files that replace the ones the declaration names.
#[derive(Debug, Clone, Copy, Default, PartialEq, Eq)]
pub struct Models<'a> {
    /// Replaces the declaration's field model.
    pub field: Option<&'a FsPath>,
    /// Replaces every robot's bounding box.
    pub robot: Option<&'a FsPath>,
}

/// The field part of a declaration.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Field {
    #[serde(default = "default_true")]
    pub carpet: bool,
    #[serde(default)]
    pub lines: Vec<Line>,
    #[serde(default)]
    pub tags: Vec<Tag>,
    #[serde(default)]
    pub model: Option<String>,
}

impl Field {
    fn validate(&self) -> anyhow::Result<()> {
        for line in &self.lines {
            if line.points.len() < 2 {
                bail!("a field line needs at least two points");
            }
        }
        for tag in &self.tags {
            if tag.size.is_nan() || tag.size <= 0.0 {
                bail!("tag {} has a non-positive size", tag.id);
            }
        }
        Ok(())
    }

    fn log_static(
        &self,
        stream: &RecordingStream,
        prefix: Option<&EntityPath>,
        model_override: Option<&FsPath>,
    ) -> anyhow::Result<()> {
        let length = FIELD_LENGTH_METERS as f32;
        let width = FIELD_WIDTH_METERS as f32;

        if self.carpet {
            stream.log_static(
                at(prefix, format!("{ROOT_3D}/field/carpet")),
                &Boxes3D::from_centers_and_half_sizes(
                    [(length / 2.0, width / 2.0, -0.005)],
                    [(length / 2.0, width / 2.0, 0.005)],
                )
                .with_colors([CARPET_COLOR])
                .with_fill_mode(FillMode::Solid),
            )?;
        }

        if !self.lines.is_empty() {
            let strips = self
                .lines
                .iter()
                .map(|line| line.points_3d())
                .collect::<Vec<_>>();
            stream.log_static(
                at(prefix, format!("{ROOT_3D}/field")),
                &LineStrips3D::new(strips)
                    .with_colors([LINE_COLOR])
                    .with_radii([0.02f32]),
            )?;

            let strips_2d = self
                .lines
                .iter()
                .map(|line| line.points_2d())
                .collect::<Vec<_>>();
            stream.log_static(
                at(prefix, format!("{ROOT_2D}/field")),
                &LineStrips2D::new(strips_2d)
                    .with_colors([LINE_COLOR])
                    .with_radii([0.02f32]),
            )?;
        }

        if !self.tags.is_empty() {
            self.log_tags(stream, prefix)?;
        }

        let model = model_override.or(self.model.as_deref().map(FsPath::new));
        if let Some(model) = model {
            stream.log_static(
                at(prefix, format!("{ROOT_3D}/field/model")),
                &load_asset3d(model)?,
            )?;
        }
        Ok(())
    }

    fn log_tags(
        &self,
        stream: &RecordingStream,
        prefix: Option<&EntityPath>,
    ) -> anyhow::Result<()> {
        let mut half_sizes_3d = Vec::with_capacity(self.tags.len());
        let mut centers_3d = Vec::with_capacity(self.tags.len());
        let mut rotations = Vec::with_capacity(self.tags.len());
        let mut centers_2d = Vec::with_capacity(self.tags.len());
        let mut half_sizes_2d = Vec::with_capacity(self.tags.len());
        let mut labels = Vec::with_capacity(self.tags.len());
        for tag in &self.tags {
            let half = tag.size as f32 / 2.0;
            // A tag is a thin plate whose normal is the tag's local +x, like WPILib's layout.
            half_sizes_3d.push((0.01, half, half));
            centers_3d.push((tag.x as f32, tag.y as f32, tag.z as f32));
            rotations.push(RotationAxisAngle::new(
                [0.0, 0.0, 1.0],
                Angle::from_radians(tag.yaw as f32),
            ));
            centers_2d.push((tag.x as f32, tag.y as f32));
            half_sizes_2d.push((half, half));
            labels.push(format!("tag {}", tag.id));
        }

        stream.log_static(
            at(prefix, format!("{ROOT_3D}/field/tags")),
            &Boxes3D::from_half_sizes(half_sizes_3d)
                .with_centers(centers_3d)
                .with_rotation_axis_angles(rotations)
                .with_colors([TAG_COLOR])
                .with_labels(labels.clone()),
        )?;
        stream.log_static(
            at(prefix, format!("{ROOT_2D}/field/tags")),
            &Boxes2D::from_centers_and_half_sizes(centers_2d, half_sizes_2d)
                .with_colors([TAG_COLOR])
                .with_labels(labels),
        )?;
        Ok(())
    }
}

/// One run of field points, in meters.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Line {
    pub points: Vec<[f64; 2]>,
    #[serde(default)]
    pub closed: bool,
}

impl Line {
    fn points_3d(&self) -> Vec<[f32; 3]> {
        let mut points = self
            .points
            .iter()
            .map(|[x, y]| [*x as f32, *y as f32, FIELD_LINE_Z])
            .collect::<Vec<_>>();
        if self.closed && points.len() > 2 {
            points.push(points[0]);
        }
        points
    }

    fn points_2d(&self) -> Vec<[f32; 2]> {
        let mut points = self
            .points
            .iter()
            .map(|[x, y]| [*x as f32, *y as f32])
            .collect::<Vec<_>>();
        if self.closed && points.len() > 2 {
            points.push(points[0]);
        }
        points
    }
}

/// One AprilTag plate.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Tag {
    pub id: u16,
    pub x: f64,
    pub y: f64,
    #[serde(default)]
    pub z: f64,
    #[serde(default)]
    pub yaw: f64,
    pub size: f64,
}

/// A robot the bridge draws: its pose source and bounding-box half-extents.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Robot {
    pub team: u16,
    pub pose_topic: String,
    #[serde(default = "default_half_extents")]
    pub half_extents: [f64; 3],
    #[serde(default)]
    pub swerve: Option<Swerve>,
    #[serde(default)]
    pub cameras: Vec<Camera>,
}

/// A swerve drive: the states topic and, optionally, the module centers in publishing order.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Swerve {
    pub states_topic: String,
    #[serde(default)]
    pub module_offsets: Vec<[f64; 2]>,
}

/// A camera mounted on the robot, in the robot frame; angles are degrees.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Camera {
    pub name: String,
    pub x: f64,
    pub y: f64,
    pub z: f64,
    #[serde(default)]
    pub roll: f64,
    #[serde(default)]
    pub pitch: f64,
    #[serde(default)]
    pub yaw: f64,
    pub fov: f64,
    /// An MJPEG stream the bridge records, e.g. PhotonVision's
    /// `http://10.63.91.11:1181/stream.mjpg`.
    ///
    /// PhotonVision cameras are just links to a `multipart/x-mixed-replace` stream. The bridge
    /// hands the URL to an `ffmpeg` subprocess, which turns it into the H.264 [`VideoStream`]
    /// logged below the camera's pinhole. Streams must be plain `http://`: `ffmpeg` would follow
    /// `https://`, but PhotonVision never serves it.
    #[serde(default)]
    pub stream: Option<String>,
}

/// A planned path, published as `Pose2d[]` or `Translation2d[]` poses on `topic`.
#[derive(Debug, Clone, Deserialize, PartialEq)]
pub struct Path {
    pub name: String,
    pub topic: String,
}

/// A robot pose decoded from NetworkTables.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Pose {
    pub translation: [f64; 3],
    /// Yaw around the field's +z axis, in radians.
    pub yaw: f32,
    /// `[x, y, z, w]`, when the source published a full 3D rotation.
    pub quaternion: Option<[f32; 4]>,
}

impl Pose {
    /// Decodes the pose from a NetworkTables value and its announced type.
    ///
    /// Accepts WPILib's `Pose2d`/`Pose3d` structs and the `double[]`/`float[]` spellings of
    /// `[x, y, yaw]` and `[x, y, z, qw, qx, qy, qz]`.
    #[must_use]
    pub fn from_nt(ty: &DataType, value: &rmpv::Value) -> Option<Self> {
        match ty {
            DataType::Struct(name) | DataType::Unknown(name) | DataType::StructArray(name) => {
                let name = name.strip_prefix("struct:").unwrap_or(name);
                let bytes = match value {
                    rmpv::Value::Binary(bytes) => bytes.as_slice(),
                    rmpv::Value::String(text) => text.as_str()?.as_bytes(),
                    _ => return None,
                };
                match name {
                    "Pose2d" | "Transform2d" => Self::from_struct_2d(bytes),
                    "Pose3d" | "Transform3d" => Self::from_struct_3d(bytes),
                    _ => None,
                }
            }
            DataType::DoubleArray | DataType::FloatArray => {
                let values = value
                    .as_array()?
                    .iter()
                    .map(rmpv::Value::as_f64)
                    .collect::<Option<Vec<_>>>()?;
                match values.as_slice() {
                    [x, y, yaw] => Some(Self::from_2d(*x, *y, *yaw)),
                    [x, y, z, qw, qx, qy, qz] => Some(Self::from_3d(
                        [*x, *y, *z],
                        [*qx as f32, *qy as f32, *qz as f32, *qw as f32],
                    )),
                    _ => None,
                }
            }
            _ => None,
        }
    }

    fn from_struct_2d(bytes: &[u8]) -> Option<Self> {
        if bytes.len() < 24 {
            return None;
        }
        Some(Self::from_2d(
            read_f64(bytes, 0)?,
            read_f64(bytes, 8)?,
            read_f64(bytes, 16)?,
        ))
    }

    fn from_struct_3d(bytes: &[u8]) -> Option<Self> {
        if bytes.len() < 56 {
            return None;
        }
        let translation = [
            read_f64(bytes, 0)?,
            read_f64(bytes, 8)?,
            read_f64(bytes, 16)?,
        ];
        let quaternion = [
            read_f64(bytes, 32)? as f32,
            read_f64(bytes, 40)? as f32,
            read_f64(bytes, 48)? as f32,
            read_f64(bytes, 24)? as f32,
        ];
        Some(Self::from_3d(translation, quaternion))
    }

    fn from_2d(x: f64, y: f64, yaw: f64) -> Self {
        Self {
            translation: [x, y, 0.0],
            yaw: yaw as f32,
            quaternion: None,
        }
    }

    fn from_3d(translation: [f64; 3], quaternion: [f32; 4]) -> Self {
        let [x, y, z, w] = quaternion;
        Self {
            translation,
            yaw: (2.0 * (w * z + x * y)).atan2(1.0 - 2.0 * (y * y + z * z)),
            quaternion: Some(quaternion),
        }
    }
}

/// Logs one robot pose on both the 3D and the 2D timeline.
///
/// The 3D lane logs a `Transform3D` at `first/robots/<team>`, which the static model box
/// inherits. The 2D lane logs the rotated footprint outline directly, because a 2D view cannot
/// render the 3D boxes.
pub fn log_pose(
    stream: &RecordingStream,
    robot: &Robot,
    pose: Pose,
    time: TimeCell,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    stream.set_time("robot_time", time);

    let rotation = match pose.quaternion {
        Some(quaternion) => Rotation3D::Quaternion(RotationQuat(Quaternion::from_xyzw(quaternion))),
        None => Rotation3D::AxisAngle(RotationAxisAngle::new(
            [0.0, 0.0, 1.0],
            Angle::from_radians(pose.yaw),
        )),
    };
    let [x, y, z] = pose.translation;
    stream.log(
        prefixed(prefix, robot_entity(robot.team, ROOT_3D)),
        &Transform3D::from_translation_rotation(
            Translation3D::from([x as f32, y as f32, z as f32]),
            rotation,
        ),
    )?;

    let [hx, hy, _] = robot.half_extents.map(|extent| extent as f32);
    let outline = footprint_2d([x, y], [hx, hy], pose.yaw);
    stream.log(
        prefixed(prefix, robot_entity(robot.team, ROOT_2D)),
        &LineStrips2D::new([outline])
            .with_colors([team_color(robot.team)])
            .with_radii([ROBOT_LINE_RADIUS]),
    )?;
    Ok(())
}

/// The center and half-size of a robot's static model box, in the robot frame.
///
/// A pose is the footprint center on the carpet, so the body spans `0..2hz` in z. Centering the
/// box on the origin would bury half of it in the floor, which is exactly what a solid box at the
/// origin looks like.
#[must_use]
pub fn robot_model(half_extents: [f64; 3]) -> ([f32; 3], [f32; 3]) {
    let [hx, hy, hz] = half_extents.map(|extent| extent as f32);
    ([0.0, 0.0, hz], [hx, hy, hz])
}

/// The z of the swerve vectors in the robot frame: above the body, so it cannot hide them.
#[must_use]
pub fn swerve_arrow_height(robot: &Robot) -> f32 {
    (robot.half_extents[2] * 2.0) as f32 + SWERVE_ARROW_LIFT
}

/// The entity a robot's pose is logged at, before any `--entity-path-prefix` is applied.
#[must_use]
pub fn robot_entity(team: u16, root: &str) -> EntityPath {
    EntityPath::from(format!("{root}/robots/{team}"))
}

/// One swerve module's measured state.
#[derive(Debug, Clone, Copy, PartialEq)]
pub struct SwerveModuleState {
    pub speed_mps: f32,
    /// Steering angle in the robot frame, radians counter-clockwise from +x.
    pub angle: f32,
}

/// Decodes a `SwerveModuleState[]` value published as a WPILib struct array.
///
/// WPILib 2027 renamed the struct to `SwerveModuleVelocity`; both are the same pair of doubles
/// (speed in m/s, steering angle in radians), so both decode the same way.
#[must_use]
pub fn swerve_states_from_nt(ty: &DataType, value: &rmpv::Value) -> Option<Vec<SwerveModuleState>> {
    let name = struct_array_name(ty)?;
    if name != "SwerveModuleState" && name != "SwerveModuleVelocity" {
        return None;
    }
    let bytes = binary(value)?;
    if bytes.len() % 16 != 0 {
        return None;
    }
    bytes
        .as_chunks::<16>()
        .0
        .iter()
        .map(|chunk| {
            Some(SwerveModuleState {
                speed_mps: read_f64(chunk, 0)? as f32,
                angle: read_f64(chunk, 8)? as f32,
            })
        })
        .collect()
}

/// Decodes a pose-array topic (`struct:Pose2d[]` or `struct:Translation2d[]`).
#[must_use]
pub fn poses_from_nt(ty: &DataType, value: &rmpv::Value) -> Option<Vec<Pose>> {
    let name = struct_array_name(ty)?;
    let bytes = binary(value)?;
    match name {
        "Pose2d" | "Transform2d" => {
            if bytes.len() % 24 != 0 {
                return None;
            }
            bytes
                .as_chunks::<24>()
                .0
                .iter()
                .map(|chunk| Pose::from_struct_2d(chunk))
                .collect()
        }
        "Translation2d" => {
            if bytes.len() % 16 != 0 {
                return None;
            }
            bytes
                .as_chunks::<16>()
                .0
                .iter()
                .map(|chunk| Some(Pose::from_2d(read_f64(chunk, 0)?, read_f64(chunk, 8)?, 0.0)))
                .collect()
        }
        _ => None,
    }
}

/// Logs one frame of swerve module vectors in the robot frame.
///
/// Arrow length is the module speed scaled by [`SWERVE_ARROW_SECONDS`], so a full-speed module
/// draws a quarter-second of travel.
pub fn log_swerve(
    stream: &RecordingStream,
    robot: &Robot,
    states: &[SwerveModuleState],
    time: TimeCell,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    if states.is_empty() {
        return Ok(());
    }
    stream.set_time("robot_time", time);

    let offsets = module_offsets(robot);
    let top = swerve_arrow_height(robot);
    let mut origins = Vec::with_capacity(states.len());
    let mut vectors = Vec::with_capacity(states.len());
    for (offset, state) in offsets.iter().zip(states) {
        let length = state.speed_mps * SWERVE_ARROW_SECONDS;
        // Draw above the robot body: a solid box would otherwise swallow the arrows.
        origins.push([offset[0] as f32, offset[1] as f32, top]);
        vectors.push([length * state.angle.cos(), length * state.angle.sin(), 0.0]);
    }
    stream.log(
        at(prefix, format!("{ROOT_3D}/robots/{}/swerve", robot.team)),
        &Arrows3D::from_vectors(vectors)
            .with_origins(origins)
            .with_radii([SWERVE_ARROW_RADIUS])
            .with_colors([swerve_color(robot.team)]),
    )?;
    Ok(())
}

/// Logs a planned path as a line strip on the field and its 2D mirror.
pub fn log_path(
    stream: &RecordingStream,
    name: &str,
    poses: &[Pose],
    time: TimeCell,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    if poses.len() < 2 {
        return Ok(());
    }
    stream.set_time("robot_time", time);

    let strip_3d = poses
        .iter()
        .map(|pose| [pose.translation[0] as f32, pose.translation[1] as f32, 0.02])
        .collect::<Vec<_>>();
    stream.log(
        at(prefix, format!("{ROOT_3D}/paths/{name}")),
        &LineStrips3D::new([strip_3d])
            .with_colors([PATH_COLOR])
            .with_radii([PATH_RADIUS]),
    )?;

    let strip_2d = poses
        .iter()
        .map(|pose| [pose.translation[0] as f32, -(pose.translation[1] as f32)])
        .collect::<Vec<_>>();
    stream.log(
        at(prefix, format!("{ROOT_2D}/paths/{name}")),
        &LineStrips2D::new([strip_2d])
            .with_colors([PATH_COLOR])
            .with_radii([PATH_RADIUS]),
    )?;
    Ok(())
}

/// The media type of an image a camera stream delivered, if the viewer can display it.
///
/// PhotonVision serves MJPEG, whose parts are JPEG frames; PNG is accepted because cscore can
/// serve it too.
#[must_use]
pub fn image_media_type(bytes: &[u8]) -> Option<&'static str> {
    if bytes.starts_with(&[0xFF, 0xD8, 0xFF]) {
        Some("image/jpeg")
    } else if bytes.starts_with(&[0x89, b'P', b'N', b'G']) {
        Some("image/png")
    } else {
        None
    }
}

/// Logs one camera image as an `EncodedImage` on the camera's `image` entity.
///
/// This is for callers that already have pictures (the `first_field` example renders a synthetic
/// clip); a live camera's footage is a `VideoStream`, which Rerun cannot project onto a pinhole in
/// a 3D view at all. Returns `false` when the bytes are not a JPEG or PNG image.
pub fn log_image(
    stream: &RecordingStream,
    team: u16,
    camera: &Camera,
    bytes: &[u8],
    time: TimeCell,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<bool> {
    let Some(media_type) = image_media_type(bytes) else {
        return Ok(false);
    };
    stream.set_time("robot_time", time);
    stream.log(
        at(prefix, camera_image_entity(team, &camera.name)),
        &EncodedImage::new(bytes.to_vec()).with_media_type(media_type),
    )?;
    Ok(true)
}

/// Logs the static codec of a camera whose footage arrives as an H.264 video stream.
///
/// `ffmpeg` re-encodes PhotonVision's MJPEG, because Rerun's `VideoStream` has no MJPEG codec and
/// reloading a JPEG per frame is what makes a live feed flicker. Call this once per camera before
/// logging samples.
pub fn log_video_codec(
    stream: &RecordingStream,
    team: u16,
    camera: &Camera,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    stream.log_static(
        at(prefix, camera_image_entity(team, &camera.name)),
        &VideoStream::new(VideoCodec::H264),
    )?;
    Ok(())
}

/// Logs one H.264 access unit (exactly one frame) on the camera's video stream.
///
/// Samples must be logged in presentation order: Rerun resets a video stream's playback when a
/// sample arrives out of order, which is the other way a live feed can flicker.
pub fn log_video_frame(
    stream: &RecordingStream,
    team: u16,
    camera: &Camera,
    sample: &[u8],
    keyframe: bool,
    time: TimeCell,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    if sample.is_empty() {
        return Ok(());
    }
    stream.set_time("robot_time", time);
    stream.log(
        at(prefix, camera_image_entity(team, &camera.name)),
        &VideoStream::update_fields()
            .with_sample(sample)
            .with_is_keyframe(keyframe),
    )?;
    Ok(())
}

/// Sends a viewer layout that puts every camera stream in a 2D view that can show it.
///
/// Rerun only shows a pinhole's 2D content in a 2D view rooted at that content, and its
/// automatic layout roots a camera's 2D view at the pinhole, which leaves the pane empty and
/// reports that the view's target frame has no pinhole root. This blueprint gives each camera a
/// 2D view at its image entity, next to a 3D view of the field; automatic views stay enabled so
/// every other topic is still graphed.
///
/// Returns `false` when the declaration has no cameras: a recording without camera footage keeps
/// the viewer's automatic layout untouched.
pub fn log_blueprint(
    stream: &RecordingStream,
    layout: &Layout,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<bool> {
    let cameras = layout
        .robots
        .iter()
        .flat_map(|robot| {
            robot.cameras.iter().map(move |camera| {
                Spatial2DView::new(format!("camera {}", camera.name))
                    .with_origin(at(prefix, camera_image_entity(robot.team, &camera.name)))
            })
        })
        .collect::<Vec<_>>();
    if cameras.is_empty() {
        return Ok(false);
    }

    let three_d = Spatial3DView::new("3D").with_origin(at(prefix, ROOT_3D.to_owned()));
    let views = std::iter::once(three_d.into())
        .chain(cameras.into_iter().map(Into::into))
        .collect::<Vec<_>>();
    let blueprint = Blueprint::new(Grid::new(views))
        .with_auto_views(true)
        .with_auto_layout(true);
    stream.send_blueprint_opts(&BlueprintOpts {
        blueprint,
        // Rerun activates a blueprint only when it is also made the app's default, which is
        // what a bridge reconnect needs: the viewer switches to the layout it just received.
        activation: BlueprintActivation::default(),
    })?;
    Ok(true)
}

/// Logs every camera on a robot as a static frustum and transform.
fn log_cameras(
    stream: &RecordingStream,
    robot: &Robot,
    prefix: Option<&EntityPath>,
) -> anyhow::Result<()> {
    for camera in &robot.cameras {
        let entity = at(prefix, camera_entity(robot.team, &camera.name));
        stream.log_static(
            entity.clone(),
            &Transform3D::from_translation_rotation(
                Translation3D::from([camera.x as f32, camera.y as f32, camera.z as f32]),
                Rotation3D::Quaternion(RotationQuat(Quaternion::from_xyzw(euler_quaternion(
                    camera,
                )))),
            ),
        )?;
        let focal = (CAMERA_WIDTH / 2.0) / (camera.fov.to_radians().tan() as f32);
        stream.log_static(
            entity,
            &Pinhole::from_focal_length_and_resolution(
                [focal, focal],
                [CAMERA_WIDTH, CAMERA_HEIGHT],
            )
            .with_camera_xyz(ViewCoordinatesComponent::FLU)
            .with_image_plane_distance(CAMERA_IMAGE_PLANE_DISTANCE),
        )?;
    }
    Ok(())
}

/// The entity a camera's static `Transform3D` and `Pinhole` are logged on.
fn camera_entity(team: u16, name: &str) -> String {
    format!("{ROOT_3D}/robots/{team}/cameras/{name}")
}

/// The entity a camera's footage is logged on, one level below the pinhole.
///
/// Rerun 0.40 only shows a pinhole's 2D content in a 2D view rooted at that content entity itself,
/// so this is also the origin a camera's 2D view needs. Its video visualizer has 2D affinity only,
/// which is why the footage never appears on the frustum in a 3D view.
fn camera_image_entity(team: u16, name: &str) -> String {
    format!("{}/image", camera_entity(team, name))
}

fn module_offsets(robot: &Robot) -> Vec<[f64; 2]> {
    if let Some(swerve) = &robot.swerve
        && !swerve.module_offsets.is_empty()
    {
        return swerve.module_offsets.clone();
    }
    let [hx, hy, _] = robot.half_extents;
    let (x, y) = (hx * 0.75, hy * 0.75);
    vec![[x, y], [x, -y], [-x, y], [-x, -y]]
}

/// ZYX Euler angles in degrees to an `xyzw` quaternion.
fn euler_quaternion(camera: &Camera) -> [f32; 4] {
    let (sr, cr) = (camera.roll.to_radians() / 2.0).sin_cos();
    let (sp, cp) = (camera.pitch.to_radians() / 2.0).sin_cos();
    let (sy, cy) = (camera.yaw.to_radians() / 2.0).sin_cos();
    [
        (sr * cp * cy - cr * sp * sy) as f32,
        (cr * sp * cy + sr * cp * sy) as f32,
        (cr * cp * sy - sr * sp * cy) as f32,
        (cr * cp * cy + sr * sp * sy) as f32,
    ]
}

fn struct_array_name(ty: &DataType) -> Option<&str> {
    match ty {
        DataType::StructArray(name) | DataType::Unknown(name) => {
            let name = name.strip_prefix("struct:").unwrap_or(name);
            name.strip_suffix("[]").or(Some(name))
        }
        _ => None,
    }
}

fn binary(value: &rmpv::Value) -> Option<&[u8]> {
    match value {
        rmpv::Value::Binary(bytes) => Some(bytes),
        rmpv::Value::String(text) => Some(text.as_str()?.as_bytes()),
        _ => None,
    }
}

fn is_segment(value: &str) -> bool {
    !value.is_empty() && !value.contains('/')
}

fn at(prefix: Option<&EntityPath>, path: String) -> EntityPath {
    prefixed(prefix, EntityPath::from(path))
}

fn prefixed(prefix: Option<&EntityPath>, entity: EntityPath) -> EntityPath {
    match prefix {
        Some(prefix) => prefix.join(&entity),
        None => entity,
    }
}

/// A closed rectangle in the 2D field frame.
#[must_use]
pub fn footprint_2d([x, y]: [f64; 2], [half_x, half_y]: [f32; 2], yaw: f32) -> Vec<[f32; 2]> {
    let (sin, cos) = yaw.sin_cos();
    let center = [x as f32, y as f32];
    let mut points = [(-1.0, -1.0), (1.0, -1.0), (1.0, 1.0), (-1.0, 1.0)]
        .into_iter()
        .map(|(sx, sy): (f32, f32)| {
            let local_x = sx * half_x;
            let local_y = sy * half_y;
            [
                center[0] + local_x * cos - local_y * sin,
                center[1] + local_x * sin + local_y * cos,
            ]
        })
        .collect::<Vec<_>>();
    points.push(points[0]);
    points
}

/// A stable per-team color, so two robots on the field never look alike.
#[must_use]
pub fn team_color(team: u16) -> u32 {
    let hue = u32::from(team).wrapping_mul(137) % 360;
    let (r, g, b) = hsv_to_rgb(hue as f32, 0.75, 0.95);
    (u32::from(r) << 24) | (u32::from(g) << 16) | (u32::from(b) << 8) | 0xFF
}

/// A pale tint of the team color for swerve vectors.
///
/// The robot body uses the saturated team color; a pale, bright version of the same hue keeps the
/// vectors readable on top of it instead of blending into the body.
#[must_use]
pub fn swerve_color(team: u16) -> u32 {
    let hue = u32::from(team).wrapping_mul(137) % 360;
    let (r, g, b) = hsv_to_rgb(hue as f32, 0.40, 1.0);
    (u32::from(r) << 24) | (u32::from(g) << 16) | (u32::from(b) << 8) | 0xFF
}

fn hsv_to_rgb(hue: f32, saturation: f32, value: f32) -> (u8, u8, u8) {
    let chroma = value * saturation;
    let sector = hue / 60.0;
    let x = chroma * (1.0 - (sector % 2.0 - 1.0).abs());
    let (r, g, b) = match sector as u32 {
        0 => (chroma, x, 0.0),
        1 => (x, chroma, 0.0),
        2 => (0.0, chroma, x),
        3 => (0.0, x, chroma),
        4 => (x, 0.0, chroma),
        _ => (chroma, 0.0, x),
    };
    let m = value - chroma;
    (
        ((r + m) * 255.0).round() as u8,
        ((g + m) * 255.0).round() as u8,
        ((b + m) * 255.0).round() as u8,
    )
}

fn load_asset3d(path: &FsPath) -> anyhow::Result<Asset3D> {
    let bytes =
        std::fs::read(path).with_context(|| format!("could not read model {}", path.display()))?;
    let media_type = match path.extension().and_then(|extension| extension.to_str()) {
        Some("glb") => Some("model/gltf-binary"),
        Some("gltf") => Some("model/gltf+json"),
        Some("obj") => Some("model/obj"),
        Some("stl") => Some("model/stl"),
        Some("ply") => Some("application/x-ply"),
        _ => None,
    };
    let asset = Asset3D::new(bytes);
    Ok(match media_type {
        Some(media_type) => asset.with_media_type(media_type),
        None => asset,
    })
}

fn read_f64(bytes: &[u8], offset: usize) -> Option<f64> {
    Some(f64::from_le_bytes(
        bytes.get(offset..offset + 8)?.try_into().ok()?,
    ))
}

const fn default_true() -> bool {
    true
}

const fn default_half_extents() -> [f64; 3] {
    [0.45, 0.45, 0.30]
}

#[cfg(test)]
mod tests {
    use super::{
        Layout, Pose, ROOT_2D, ROOT_3D, footprint_2d, image_media_type, log_image, log_path,
        log_pose, log_swerve, poses_from_nt, robot_entity, robot_model, swerve_arrow_height,
        swerve_color, swerve_states_from_nt, team_color,
    };
    use nt_client::data::DataType;

    /// Byte-for-byte what `FirstMetadataTest` asserts on the Java side, so the two languages
    /// cannot drift apart silently.
    const DECLARATION: &str = concat!(
        "{\"version\":1,\"field\":{\"carpet\":true,\"lines\":[",
        "{\"points\":[[0.0,0.0],[16.54175,0.0],[16.54175,8.069326],[0.0,8.069326]],",
        "\"closed\":true},",
        "{\"points\":[[8.270875,0.0],[8.270875,8.069326]],\"closed\":false}],",
        "\"tags\":[]},\"robots\":[{\"team\":254,",
        "\"pose_topic\":\"/AdvantageKit/RealOutputs/Drive/Pose2d\",",
        "\"half_extents\":[0.45,0.45,0.3]}]}"
    );

    const DECLARATION_WITH_TAGS: &str = r#"{"version":1,"field":{"carpet":false,"lines":[],
        "tags":[{"id":3,"x":1.0,"y":2.0,"z":1.5,"yaw":0.25,"size":0.1651}]},"robots":[]}"#;

    const DECLARATION_WITH_DEVICES: &str = r#"{"version":1,"field":{"carpet":true,
        "lines":[{"points":[[0.0,0.0],[16.54175,8.069326]],"closed":false}]},
        "robots":[{"team":254,"pose_topic":"/Pose","half_extents":[0.45,0.45,0.3],
        "swerve":{"states_topic":"/Swerve","module_offsets":[[0.3,0.25],[0.3,-0.25],[-0.3,0.25],[-0.3,-0.25]]},
        "cameras":[{"name":"front","x":0.3,"y":0.0,"z":0.5,"roll":0.0,"pitch":-10.0,"yaw":0.0,"fov":70.0,
        "stream":"http://10.63.91.11:1181/stream.mjpg"}]}],
        "paths":[{"name":"auto","topic":"/Path"}]}"#;

    #[test]
    fn parses_the_declaration_java_publishes() {
        let layout = Layout::parse(DECLARATION).unwrap();

        assert_eq!(layout.version, 1);
        assert!(layout.field.carpet);
        assert_eq!(layout.field.lines.len(), 2);
        assert!(layout.field.tags.is_empty());
        assert_eq!(layout.robots[0].team, 254);
        assert_eq!(
            layout.robots[0].pose_topic,
            "/AdvantageKit/RealOutputs/Drive/Pose2d"
        );
    }

    #[test]
    fn parses_callers_geometry() {
        let layout = Layout::parse(DECLARATION_WITH_TAGS).unwrap();

        assert!(!layout.field.carpet);
        assert_eq!(layout.field.tags[0].id, 3);
        assert_eq!(layout.field.tags[0].z, 1.5);
        assert_eq!(layout.field.tags[0].yaw, 0.25);
    }

    #[test]
    fn indexes_devices_by_topic() {
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();

        assert!(layout.robots_by_pose_topic().contains_key("/Pose"));
        assert!(layout.robots_by_swerve_topic().contains_key("/Swerve"));
        assert_eq!(
            layout.paths_by_topic().get("/Path").map(String::as_str),
            Some("auto")
        );
        assert_eq!(layout.robots[0].cameras[0].name, "front");
        assert_eq!(layout.robots[0].cameras[0].fov, 70.0);
        assert_eq!(
            layout.robots[0].cameras[0].stream.as_deref(),
            Some("http://10.63.91.11:1181/stream.mjpg")
        );
        let streams = layout.cameras_by_stream();
        assert_eq!(streams["http://10.63.91.11:1181/stream.mjpg"].0, 254);
        assert_eq!(
            streams["http://10.63.91.11:1181/stream.mjpg"].1.name,
            "front"
        );
    }

    #[test]
    fn decodes_wpilib_pose2d_structs() {
        let mut bytes = Vec::new();
        for value in [1.0f64, 2.0, 0.5] {
            bytes.extend_from_slice(&value.to_le_bytes());
        }
        let pose = Pose::from_nt(
            &DataType::Struct("Pose2d".to_owned()),
            &rmpv::Value::Binary(bytes),
        )
        .unwrap();

        assert_eq!(pose.translation, [1.0, 2.0, 0.0]);
        assert!((pose.yaw - 0.5).abs() < 1e-6);
        assert!(pose.quaternion.is_none());
    }

    #[test]
    fn decodes_pose3d_structs_and_array_spellings() {
        let mut bytes = Vec::new();
        for value in [
            1.0f64,
            2.0,
            0.0,
            std::f64::consts::FRAC_1_SQRT_2,
            0.0,
            0.0,
            std::f64::consts::FRAC_1_SQRT_2,
        ] {
            bytes.extend_from_slice(&value.to_le_bytes());
        }
        let pose = Pose::from_nt(
            &DataType::Unknown("struct:Pose3d".to_owned()),
            &rmpv::Value::Binary(bytes),
        )
        .unwrap();
        assert_eq!(pose.translation, [1.0, 2.0, 0.0]);
        assert!(pose.quaternion.is_some());
        assert!((pose.yaw - std::f32::consts::FRAC_PI_2).abs() < 1e-4);

        let array = rmpv::Value::Array(vec![3.0f64.into(), 4.0.into(), 0.25.into()]);
        let pose = Pose::from_nt(&DataType::DoubleArray, &array).unwrap();
        assert_eq!(pose.translation, [3.0, 4.0, 0.0]);
        assert!((pose.yaw - 0.25).abs() < 1e-6);
    }

    #[test]
    fn decodes_swerve_states_and_path_poses() {
        let mut bytes = Vec::new();
        for (speed, angle) in [(1.0f64, 0.0f64), (2.0, 0.5)] {
            bytes.extend_from_slice(&speed.to_le_bytes());
            bytes.extend_from_slice(&angle.to_le_bytes());
        }
        let states = swerve_states_from_nt(
            &DataType::Unknown("struct:SwerveModuleState[]".to_owned()),
            &rmpv::Value::Binary(bytes),
        )
        .unwrap();
        assert_eq!(states.len(), 2);
        assert_eq!(states[1].speed_mps, 2.0);
        assert_eq!(states[1].angle, 0.5);

        // WPILib 2027's renamed struct carries the same two doubles.
        let mut bytes = Vec::new();
        for (speed, angle) in [(1.0f64, 0.0f64), (2.0, 0.5)] {
            bytes.extend_from_slice(&speed.to_le_bytes());
            bytes.extend_from_slice(&angle.to_le_bytes());
        }
        let states = swerve_states_from_nt(
            &DataType::Unknown("struct:SwerveModuleVelocity[]".to_owned()),
            &rmpv::Value::Binary(bytes),
        )
        .unwrap();
        assert_eq!(states[0].speed_mps, 1.0);
        assert_eq!(states[1].angle, 0.5);

        let mut bytes = Vec::new();
        for (x, y, yaw) in [(1.0f64, 2.0f64, 0.0f64), (3.0, 4.0, 0.25)] {
            for value in [x, y, yaw] {
                bytes.extend_from_slice(&value.to_le_bytes());
            }
        }
        let poses = poses_from_nt(
            &DataType::StructArray("Pose2d".to_owned()),
            &rmpv::Value::Binary(bytes),
        )
        .unwrap();
        assert_eq!(poses.len(), 2);
        assert_eq!(poses[1].translation, [3.0, 4.0, 0.0]);
        assert!((poses[1].yaw - 0.25).abs() < 1e-6);
    }

    /// A minimal JPEG: the SOI marker plus opaque bytes. The bridge never decodes frames, it only
    /// sniffs the container before handing the bytes to Rerun.
    const JPEG: &[u8] = &[
        0xFF, 0xD8, 0xFF, 0xE0, 0, 0x10, b'J', b'F', b'I', b'F', 0xFF, 0xD9,
    ];

    #[test]
    fn recognizes_camera_images() {
        assert_eq!(image_media_type(JPEG), Some("image/jpeg"));
        assert_eq!(
            image_media_type(&[0x89, b'P', b'N', b'G', 0x0D, 0x0A]),
            Some("image/png")
        );
        assert_eq!(image_media_type(&[0x00, 0x00, 0x01]), None);
        assert_eq!(image_media_type(&[]), None);
    }

    fn chunks(messages: Vec<re_log_msg::LogMsg>) -> Vec<re_chunk::Chunk> {
        messages
            .into_iter()
            .filter_map(|message| match message {
                re_log_msg::LogMsg::ArrowMsg(_, arrow) => {
                    re_chunk::Chunk::from_arrow_msg(&arrow).ok()
                }
                _ => None,
            })
            .collect()
    }

    fn has(chunks: &[re_chunk::Chunk], entity: &str, archetype: &str) -> bool {
        chunks.iter().any(|chunk| {
            chunk.entity_path().to_string() == entity
                && chunk.component_descriptors().any(|descriptor| {
                    descriptor
                        .archetype
                        .as_ref()
                        .is_some_and(|name| name == archetype)
                })
        })
    }

    #[test]
    fn custom_models_replace_the_declared_assets() {
        let dir = std::env::temp_dir().join(format!("firstrun-models-{}", std::process::id()));
        std::fs::create_dir_all(&dir).unwrap();
        let field = dir.join("field.glb");
        let robot = dir.join("robot.glb");
        std::fs::write(&field, b"glTF field").unwrap();
        std::fs::write(&robot, b"glTF robot").unwrap();

        let (stream, storage) = re_sdk::RecordingStreamBuilder::new("first_models_test")
            .memory()
            .unwrap();
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        assert!(layout.field.model.is_none());
        layout
            .log_static_with_models(
                &stream,
                None,
                super::Models {
                    field: Some(field.as_path()),
                    robot: Some(robot.as_path()),
                },
            )
            .unwrap();
        let chunks = chunks(storage.take());
        let _ = std::fs::remove_dir_all(&dir);

        assert!(has(
            &chunks,
            "/first/field/model",
            "rerun.archetypes.Asset3D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/model",
            "rerun.archetypes.Asset3D"
        ));
        assert!(
            !has(
                &chunks,
                "/first/robots/254/model",
                "rerun.archetypes.Boxes3D"
            ),
            "a custom robot model replaces the bounding box"
        );
    }

    #[test]
    fn logs_camera_frames_as_a_video_stream() {
        let (stream, storage) = re_sdk::RecordingStreamBuilder::new("first_video_test")
            .memory()
            .unwrap();
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        let camera = &layout.robots[0].cameras[0];
        let time =
            re_log_types::TimeCell::from_duration_nanos(re_log_types::NonMinI64::new(1).unwrap());

        super::log_video_codec(&stream, 254, camera, None).unwrap();
        super::log_video_frame(
            &stream,
            254,
            camera,
            &[0, 0, 0, 1, 0x09, 0xF0, 0, 0, 0, 1, 0x65, 0x88],
            true,
            time,
            None,
        )
        .unwrap();

        let video = chunks(storage.take());
        assert!(
            has(
                &video,
                "/first/robots/254/cameras/front/image",
                "rerun.archetypes.VideoStream"
            ),
            "camera footage is a video stream on the camera's image entity"
        );
    }

    #[test]
    fn sends_a_camera_layout_only_when_a_camera_is_declared() {
        let (stream, _storage) = re_sdk::RecordingStreamBuilder::new("first_blueprint_test")
            .memory()
            .unwrap();
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        assert!(super::log_blueprint(&stream, &layout, None).unwrap());

        let mut no_cameras = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        for robot in &mut no_cameras.robots {
            robot.cameras.clear();
        }
        assert!(
            !super::log_blueprint(&stream, &no_cameras, None).unwrap(),
            "a recording without camera footage keeps the automatic layout"
        );
    }

    #[test]
    fn draws_the_field_robots_and_devices() {
        let (stream, storage) = re_sdk::RecordingStreamBuilder::new("first_test")
            .memory()
            .unwrap();
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        layout.log_static(&stream, None).unwrap();

        let time =
            re_log_types::TimeCell::from_duration_nanos(re_log_types::NonMinI64::new(1).unwrap());
        let robot = &layout.robots[0];
        let pose = Pose {
            translation: [1.0, 2.0, 0.0],
            yaw: 0.5,
            quaternion: None,
        };
        log_pose(&stream, robot, pose, time, None).unwrap();
        let states = [
            super::SwerveModuleState {
                speed_mps: 1.0,
                angle: 0.0,
            },
            super::SwerveModuleState {
                speed_mps: 2.0,
                angle: 0.5,
            },
            super::SwerveModuleState {
                speed_mps: 3.0,
                angle: -0.5,
            },
            super::SwerveModuleState {
                speed_mps: 4.0,
                angle: 1.0,
            },
        ];
        log_swerve(&stream, robot, &states, time, None).unwrap();
        log_path(&stream, "auto", &[pose, pose], time, None).unwrap();
        let camera = &robot.cameras[0];
        assert!(
            log_image(&stream, robot.team, camera, JPEG, time, None).unwrap(),
            "MJPEG frames are recognized"
        );
        assert!(
            !log_image(&stream, robot.team, camera, &[0x00, 0x01], time, None).unwrap(),
            "anything that is not an image is skipped"
        );

        let chunks = chunks(storage.take());

        assert!(has(
            &chunks,
            "/first/field",
            "rerun.archetypes.LineStrips3D"
        ));
        assert!(has(
            &chunks,
            "/first/field/carpet",
            "rerun.archetypes.Boxes3D"
        ));
        assert!(has(
            &chunks,
            "/first2d/field",
            "rerun.archetypes.LineStrips2D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/model",
            "rerun.archetypes.Boxes3D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254",
            "rerun.archetypes.Transform3D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/cameras/front",
            "rerun.archetypes.Transform3D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/cameras/front",
            "rerun.archetypes.Pinhole"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/swerve",
            "rerun.archetypes.Arrows3D"
        ));
        assert!(has(
            &chunks,
            "/first/robots/254/cameras/front/image",
            "rerun.archetypes.EncodedImage"
        ));
        assert!(has(
            &chunks,
            "/first/paths/auto",
            "rerun.archetypes.LineStrips3D"
        ));
        assert!(has(
            &chunks,
            "/first2d/paths/auto",
            "rerun.archetypes.LineStrips2D"
        ));
        assert!(has(
            &chunks,
            "/first2d/robots/254",
            "rerun.archetypes.LineStrips2D"
        ));
    }

    #[test]
    fn rejects_declarations_the_bridge_cannot_use() {
        let bad_version = DECLARATION.replacen("\"version\":1", "\"version\":2", 1);
        assert!(Layout::parse(&bad_version).is_err());

        let relative_topic =
            DECLARATION.replacen("/AdvantageKit/RealOutputs/Drive/Pose2d", "Drive/Pose2d", 1);
        assert!(Layout::parse(&relative_topic).is_err());

        let zero_team = DECLARATION.replacen("\"team\":254", "\"team\":0", 1);
        assert!(Layout::parse(&zero_team).is_err());

        let negative_size =
            DECLARATION_WITH_TAGS.replacen("\"size\":0.1651", "\"size\":-0.1651", 1);
        assert!(Layout::parse(&negative_size).is_err());

        let relative_swerve = DECLARATION_WITH_DEVICES.replacen("\"/Swerve\"", "\"Swerve\"", 1);
        assert!(Layout::parse(&relative_swerve).is_err());

        let two_offsets = DECLARATION_WITH_DEVICES.replacen(
            "[[0.3,0.25],[0.3,-0.25],[-0.3,0.25],[-0.3,-0.25]]",
            "[[0.3,0.25],[0.3,-0.25]]",
            1,
        );
        assert!(Layout::parse(&two_offsets).is_err());

        let nested_camera =
            DECLARATION_WITH_DEVICES.replacen("\"name\":\"front\"", "\"name\":\"a/b\"", 1);
        assert!(Layout::parse(&nested_camera).is_err());

        let wide_fov = DECLARATION_WITH_DEVICES.replacen("\"fov\":70.0", "\"fov\":200.0", 1);
        assert!(Layout::parse(&wide_fov).is_err());

        let relative_path = DECLARATION_WITH_DEVICES.replacen("\"/Path\"", "\"Path\"", 1);
        assert!(Layout::parse(&relative_path).is_err());

        // The bridge has no TLS client, so an https stream is rejected up front.
        let https_stream = DECLARATION_WITH_DEVICES.replacen(
            "\"stream\":\"http://10.63.91.11:1181/stream.mjpg\"",
            "\"stream\":\"https://10.63.91.11:1181/stream.mjpg\"",
            1,
        );
        assert!(Layout::parse(&https_stream).is_err());

        let hostless_stream = DECLARATION_WITH_DEVICES.replacen(
            "\"stream\":\"http://10.63.91.11:1181/stream.mjpg\"",
            "\"stream\":\"http:///stream.mjpg\"",
            1,
        );
        assert!(Layout::parse(&hostless_stream).is_err());

        // Two cameras cannot share one stream: the frames would be recorded twice.
        let duplicate_stream = DECLARATION_WITH_DEVICES.replacen(
            "\"stream\":\"http://10.63.91.11:1181/stream.mjpg\"}",
            "\"stream\":\"http://10.63.91.11:1181/stream.mjpg\"},{\"name\":\"side\",\"x\":0.3,\
            \"y\":0.0,\"z\":0.5,\"roll\":0.0,\"pitch\":0.0,\"yaw\":90.0,\"fov\":70.0,\
            \"stream\":\"http://10.63.91.11:1181/stream.mjpg\"}",
            1,
        );
        assert!(Layout::parse(&duplicate_stream).is_err());

        assert!(Layout::parse("not json").is_err());
    }

    #[test]
    fn two_dimensional_footprints_follow_the_field_frame() {
        let outline = footprint_2d([1.0, 2.0], [0.5, 0.25], 0.0);

        assert_eq!(outline.len(), 5);
        assert_eq!(outline[0], outline[4]);
        // The 2D lane keeps WPILib's y, so it lines up with every other 2D WPILib topic.
        assert_eq!(outline[0], [0.5, 1.75]);
    }

    #[test]
    fn robots_stand_on_the_carpet_and_swerve_vectors_sit_on_top() {
        let layout = Layout::parse(DECLARATION_WITH_DEVICES).unwrap();
        let robot = &layout.robots[0];

        let (center, half_size) = robot_model(robot.half_extents);
        assert_eq!(center, [0.0, 0.0, 0.3]);
        assert_eq!(half_size, [0.45, 0.45, 0.3]);
        assert_eq!(
            center[2] - half_size[2],
            0.0,
            "the box starts at the carpet"
        );
        assert!(
            swerve_arrow_height(robot) > center[2] + half_size[2],
            "the vectors are drawn above the body"
        );
    }

    #[test]
    fn team_colors_are_stable_and_distinct() {
        assert_eq!(team_color(254), team_color(254));
        assert_ne!(team_color(254), team_color(1678));
    }

    #[test]
    fn swerve_colors_are_pale_tints_of_the_team_color() {
        assert_eq!(swerve_color(254), swerve_color(254));
        assert_ne!(swerve_color(254), team_color(254));
        for team in [254u16, 1678, 971] {
            let pale = swerve_color(team).to_be_bytes();
            assert_eq!(pale[3], 0xFF);
            assert!(
                pale[..3].iter().all(|channel| *channel >= 150),
                "a pale tint stays bright, got {pale:?}"
            );
        }
    }

    #[test]
    fn robot_entities_differ_between_spaces() {
        assert_eq!(
            robot_entity(254, ROOT_3D).to_string(),
            "/first/robots/254".to_owned()
        );
        assert_eq!(
            robot_entity(254, ROOT_2D).to_string(),
            "/first2d/robots/254".to_owned()
        );
    }
}
