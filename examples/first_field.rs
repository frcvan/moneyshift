//! Renders a FIRST field, two robots with swerve states and cameras, and a planned path into an
//! `.rrd` file.
//!
//! Usage: `cargo run --example first_field -- <model.glb> <out.rrd> [clip.h264]`
//!
//! The field model is expected to be an AdvantageScope asset: y-up, centered on the field, and
//! including the driver stations. The example logs a `Transform3D` on the model entity to bring
//! it into the WPILib corner frame (`+x` toward red, `+y` toward blue's left, `+z` up). Models
//! exported directly in that frame need no transform.
//!
//! Camera footage is logged with `VideoStream` on each camera entity. Pass an Annex-B H.264 clip
//! as the third argument; when it is omitted the example renders a synthetic clip with `ffmpeg`
//! (skipping video when `ffmpeg` is not installed).

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
    let clip = args.next();

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
            video_topic: None,
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
            video_topic: None,
        },
    ];

    let mut cameras_254 = cameras.clone();
    cameras_254[0].video_topic = Some("/Test/254/Camera/front".to_owned());
    let mut cameras_1678 = cameras;
    cameras_1678[0].video_topic = Some("/Test/1678/Camera/front".to_owned());

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

    let frames = load_camera_clip(clip.as_deref(), FRAMES, FRAME_NANOS);
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
                first::log_frame(&stream, robot.team, &robot.cameras[0], sample, time, None)?;
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

/// The per-frame samples of an Annex-B H.264 clip, or an empty list when no clip is available.
fn load_camera_clip(clip: Option<&str>, frames: i64, frame_nanos: i64) -> Vec<Vec<u8>> {
    let bytes = match clip {
        Some(path) => match std::fs::read(path) {
            Ok(bytes) => bytes,
            Err(error) => {
                eprintln!("could not read the camera clip {path}: {error}");
                return Vec::new();
            }
        },
        None => match render_synthetic_clip(frames, frame_nanos) {
            Some(bytes) => bytes,
            None => return Vec::new(),
        },
    };

    let units = first::split_annex_b_units(&bytes);
    if units.is_empty() {
        eprintln!("the camera clip has no H.264 access units");
    }
    units
}

/// Renders a short test pattern with `ffmpeg`, so the demo shows camera video without assets.
fn render_synthetic_clip(frames: i64, frame_nanos: i64) -> Option<Vec<u8>> {
    let seconds = frames as f64 * frame_nanos as f64 / 1e9;
    let out = std::env::temp_dir().join("firstrun-camera-demo.h264");
    let args = [
        "-hide_banner".to_owned(),
        "-loglevel".to_owned(),
        "error".to_owned(),
        "-y".to_owned(),
        "-f".to_owned(),
        "lavfi".to_owned(),
        "-i".to_owned(),
        format!("testsrc=size=640x360:rate=10:duration={seconds}"),
        "-c:v".to_owned(),
        "libx264".to_owned(),
        "-preset".to_owned(),
        "ultrafast".to_owned(),
        "-tune".to_owned(),
        "zerolatency".to_owned(),
        "-g".to_owned(),
        "10".to_owned(),
        "-pix_fmt".to_owned(),
        "yuv420p".to_owned(),
        "-f".to_owned(),
        "h264".to_owned(),
    ];
    let status = std::process::Command::new("ffmpeg")
        .args(args)
        .arg(&out)
        .status();
    match status {
        Ok(status) if status.success() => std::fs::read(&out).ok(),
        _ => {
            eprintln!("no camera clip given and ffmpeg is unavailable; skipping camera video");
            None
        }
    }
}

fn usage() {
    eprintln!("usage: cargo run --example first_field -- <model.glb> <out.rrd> [clip.h264]");
}
