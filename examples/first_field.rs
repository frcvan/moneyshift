//! Renders a FIRST field, two robots with swerve states and cameras, and a planned path into an
//! `.rrd` file.
//!
//! Usage: `cargo run --example first_field -- <model.glb> <out.rrd>`
//!
//! The field model is expected to be an AdvantageScope asset: y-up, centered on the field, and
//! including the driver stations. The example logs a `Transform3D` on the model entity to bring
//! it into the WPILib corner frame (`+x` toward red, `+y` toward blue's left, `+z` up). Models
//! exported directly in that frame need no transform.
//!
//! Camera frames are logged as `EncodedImage`s on each camera's `image` entity, which the 3D view
//! projects onto the pinhole plane. The frames are a synthetic test pattern rendered with `ffmpeg`,
//! so the example has no assets; without `ffmpeg` it logs no camera footage.

use std::f64::consts::{FRAC_PI_2, PI};

use firstrun::first::{
    self, Camera, FIELD_LENGTH_METERS, FIELD_WIDTH_METERS, Field, Layout, Line, Path, Pose, Robot,
    Swerve, SwerveModuleState,
};
use re_log_types::{NonMinI64, TimeCell};
use re_sdk::RecordingStreamBuilder;
use re_sdk_types::{
    Rotation3D,
    archetypes::Transform3D,
    components::{RotationAxisAngle, Translation3D},
    encodings::Angle,
};

const FRAMES: i64 = 750;
const FRAME_NANOS: i64 = 20_000_000;

fn main() -> anyhow::Result<()> {
    let mut args = std::env::args().skip(1);
    let Some(model) = args.next() else {
        usage();
        return Ok(());
    };
    let Some(out) = args.next() else {
        usage();
        return Ok(());
    };
    let cameras = vec![
        Camera {
            name: "front".to_owned(),
            x: 0.30,
            y: 0.0,
            z: 0.50,
            roll: 0.0,
            pitch: -10.0,
            yaw: 0.0,
            fov: 70.0,
            stream: None,
        },
        Camera {
            name: "rear".to_owned(),
            x: -0.30,
            y: 0.0,
            z: 0.50,
            roll: 0.0,
            pitch: 10.0,
            yaw: 180.0,
            fov: 70.0,
            stream: None,
        },
    ];

    // The URLs a robot would declare: each PhotonVision camera is an MJPEG stream.
    let mut cameras_254 = cameras.clone();
    cameras_254[0].stream = Some("http://10.63.91.11:1181/stream.mjpg".to_owned());
    let mut cameras_1678 = cameras;
    cameras_1678[0].stream = Some("http://10.63.91.11:1182/stream.mjpg".to_owned());

    let layout = Layout {
        version: 1,
        field: Field {
            // The 2026 model ships its own carpet.
            carpet: false,
            lines: vec![
                Line {
                    points: vec![
                        [0.0, 0.0],
                        [FIELD_LENGTH_METERS, 0.0],
                        [FIELD_LENGTH_METERS, FIELD_WIDTH_METERS],
                        [0.0, FIELD_WIDTH_METERS],
                    ],
                    closed: true,
                },
                Line {
                    points: vec![
                        [FIELD_LENGTH_METERS / 2.0, 0.0],
                        [FIELD_LENGTH_METERS / 2.0, FIELD_WIDTH_METERS],
                    ],
                    closed: false,
                },
            ],
            tags: vec![],
            model: Some(model),
        },
        robots: vec![
            Robot {
                team: 254,
                pose_topic: "/Test/254/Pose2d".to_owned(),
                half_extents: [0.45, 0.45, 0.30],
                swerve: Some(Swerve {
                    states_topic: "/Test/254/SwerveStates".to_owned(),
                    module_offsets: vec![],
                }),
                cameras: cameras_254,
            },
            Robot {
                team: 1678,
                pose_topic: "/Test/1678/Pose2d".to_owned(),
                half_extents: [0.45, 0.45, 0.30],
                swerve: Some(Swerve {
                    states_topic: "/Test/1678/SwerveStates".to_owned(),
                    module_offsets: vec![],
                }),
                cameras: cameras_1678,
            },
        ],
        blueprint: None,
        paths: vec![Path {
            name: "auto".to_owned(),
            topic: "/Test/PathPlanner/ActivePath".to_owned(),
        }],
    };

    let stream = RecordingStreamBuilder::new("FIRSTrun first field")
        .recording_name("FIRST field test")
        .save(&out)?;

    layout.log_static(&stream, None)?;
    align_advantagescope_model(&stream)?;

    let path = planned_path();
    first::log_path(
        &stream,
        &layout.paths[0].name,
        &path,
        TimeCell::from_duration_nanos(NonMinI64::new(0).unwrap_or_default()),
        None,
    )?;

    let frames = render_camera_frames(FRAMES, FRAME_NANOS);
    // One camera sample every 50 ms, matching the 10 fps clip.
    let frames_per_sample = 5;

    for frame in 0..FRAMES {
        let seconds = frame as f64 / (1_000_000_000.0 / FRAME_NANOS as f64);
        let time =
            TimeCell::from_duration_nanos(NonMinI64::new(frame * FRAME_NANOS).unwrap_or_default());

        // 254 drives a wide ellipse; 1678 traces a figure eight through the middle.
        let angle = seconds * 0.5;
        let pose = Pose {
            translation: [
                FIELD_LENGTH_METERS / 2.0 + 5.0 * angle.cos(),
                FIELD_WIDTH_METERS / 2.0 + 3.0 * angle.sin(),
                0.0,
            ],
            yaw: (angle + FRAC_PI_2) as f32,
            quaternion: None,
        };
        first::log_pose(&stream, &layout.robots[0], pose, time, None)?;
        first::log_swerve(
            &stream,
            &layout.robots[0],
            &swerve_states(&[0.15, -0.10, -0.20, 0.05], seconds),
            time,
            None,
        )?;

        let t = seconds * 0.4;
        let pose = Pose {
            translation: [
                FIELD_LENGTH_METERS / 2.0 + 4.0 * t.sin(),
                FIELD_WIDTH_METERS / 2.0 + 3.0 * (t * 2.0).sin(),
                0.0,
            ],
            yaw: (3.0 * 2.0 * (t * 2.0).cos()).atan2(4.0 * t.cos()) as f32,
            quaternion: None,
        };
        first::log_pose(&stream, &layout.robots[1], pose, time, None)?;
        first::log_swerve(
            &stream,
            &layout.robots[1],
            &swerve_states(&[-0.05, 0.20, 0.10, -0.15], seconds + 0.7),
            time,
            None,
        )?;

        if frame % frames_per_sample == 0
            && let Some(sample) = usize::try_from(frame / frames_per_sample)
                .ok()
                .and_then(|index| frames.get(index))
        {
            for robot in &layout.robots {
                first::log_image(&stream, robot.team, &robot.cameras[0], sample, time, None)?;
            }
        }
    }

    stream.flush_blocking()?;
    println!("wrote {out}");
    Ok(())
}

fn swerve_states(steering: &[f32; 4], seconds: f64) -> [SwerveModuleState; 4] {
    std::array::from_fn(|index| SwerveModuleState {
        speed_mps: 2.5 + 1.5 * (seconds * 0.8 + index as f64).sin().abs() as f32,
        angle: steering[index] + 0.1 * (seconds * 1.3).sin() as f32,
    })
}

/// A gentle S-curve from the blue side to the red side.
fn planned_path() -> Vec<Pose> {
    (0..=80)
        .map(|step| {
            let t = f64::from(step) / 80.0;
            Pose {
                translation: [
                    2.0 + (FIELD_LENGTH_METERS - 4.0) * t,
                    FIELD_WIDTH_METERS / 2.0 + 2.5 * (t * PI).sin(),
                    0.0,
                ],
                yaw: 0.0,
                quaternion: None,
            }
        })
        .collect()
}

/// Moves an AdvantageScope field model from its centered, y-up frame into the WPILib field frame.
fn align_advantagescope_model(stream: &re_sdk::RecordingStream) -> anyhow::Result<()> {
    stream.log_static(
        "first/field/model",
        &Transform3D::from_translation_rotation(
            Translation3D::from([
                (FIELD_LENGTH_METERS / 2.0) as f32,
                (FIELD_WIDTH_METERS / 2.0) as f32,
                0.0,
            ]),
            Rotation3D::AxisAngle(RotationAxisAngle::new(
                [1.0, 0.0, 0.0],
                Angle::from_radians(FRAC_PI_2 as f32),
            )),
        ),
    )?;
    Ok(())
}

/// Renders one JPEG per camera frame with `ffmpeg`, so the demo shows camera footage without
/// assets. Returns an empty list when `ffmpeg` is not available.
fn render_camera_frames(frames: i64, frame_nanos: i64) -> Vec<Vec<u8>> {
    let seconds = frames as f64 * frame_nanos as f64 / 1e9;
    let dir = std::env::temp_dir().join("firstrun-camera-frames");
    let _ = std::fs::remove_dir_all(&dir);
    if std::fs::create_dir_all(&dir).is_err() {
        return Vec::new();
    }
    let pattern = dir.join("frame_%04d.jpg");
    let status = std::process::Command::new("ffmpeg")
        .args([
            "-hide_banner",
            "-loglevel",
            "error",
            "-y",
            "-f",
            "lavfi",
            "-i",
            &format!("testsrc=size=640x360:rate=10:duration={seconds}"),
            "-q:v",
            "4",
            "-f",
            "image2",
        ])
        .arg(&pattern)
        .status();
    if !status.is_ok_and(|status| status.success()) {
        eprintln!("ffmpeg is unavailable; logging the field without camera footage");
        return Vec::new();
    }

    let mut frames = Vec::new();
    for index in 1.. {
        match std::fs::read(dir.join(format!("frame_{index:04}.jpg"))) {
            Ok(bytes) => frames.push(bytes),
            Err(_) => break,
        }
    }
    frames
}

fn usage() {
    eprintln!("usage: cargo run --example first_field -- <model.glb> <out.rrd>");
}
