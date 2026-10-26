# FIRSTrun on a WPILib 2027 robot

A minimal, runnable WPILib 2027 robot that exercises the whole FIRSTrun path:

- **Command-based v2** (`org.wpilib.command2`), default field-relative joystick drive;
- **AdvantageKit** 27 alpha, logging to NetworkTables through `NT4Publisher`;
- **PhotonVision** 2027 alpha: a real `PhotonCamera` plus a simulated one that sees the WPILib
  field's AprilTags;
- an **extremely basic swerve sim**: four velocity-steered modules, first-order lag, integrated
  gyro, `SwerveDrivePoseEstimator`;
- **FIRSTrun integration**: one `FirstMetadata` declaration at `/Firstrun/layout` carrying the
  field's 32 AprilTags, plus `struct:Pose2d` and `struct:SwerveModuleVelocity[]` topics the bridge
  reads.

It has no hardware layer, no vendor motor controllers and nothing to tune: the point is to watch
the bridge draw a field, a robot, swerve vectors and a camera frustum from data a real 2027 robot
shape produces.

## Run it

```sh
# Once, so the example resolves the SDK from Maven Local.
(cd ../../moneyshift && ./gradlew :lib:publishToMavenLocal)

./gradlew run         # simulator, with the WPILib driver station and GUI
./gradlew build       # compile + the swerve sim tests
./gradlew deploy      # team 6391; change .wpilib/wpilib_preferences.json for yours
```

The simulator opens disabled. Enable teleop in its Robot State panel and drive with the gamepad;
start resets odometry. All three modes work, but there is no real robot to talk to.

## Record it with the bridge

While the robot runs, from the repository root in another terminal:

```sh
cargo run --release --bin rerun-importer-firstrun -- --nt --save /tmp/demo.rrd
rerun /tmp/demo.rrd
```

Every value under `/Demo/...` and the AdvantageKit `RealOutputs` are graphed on their own; the
layout adds `first/field`, `first/robots/6391` (the pose), `first/robots/6391/model`,
`first/robots/6391/swerve` (module vectors), `first/robots/6391/cameras/front` (frustum, with the
video on `.../front/image`) and the `first2d/` top-down mirror. Camera footage in simulation comes from
PhotonVision's own camera server: `VisionSystemSim.addCamera` serves what the
simulated camera sees at `http://127.0.0.1:1181/stream.mjpg`, which is the URL
`Constants.CAMERA_STREAM` names, so frames appear without a coprocessor.

The demo declares its own viewer layout through `FirstMetadata`: a 3D field view, a 2D field
view, and a dataframe of the recorded `Drive/HeadingDegrees` and `Vision/TagCount` topics, with the
cameras arriving as a grid. Change those `.view(...)` calls in `RerunViz` to change what the window
opens with; drop them and the bridge's default layout applies.

## What to look at when something breaks

| Symptom | Where to look |
| --- | --- |
| field and robots missing | `/Firstrun/layout` — the bridge logs the declaration it parses |
| robots missing, field drawn | the pose topic name; it must match `Constants.POSE_TOPIC` |
| no swerve arrows | the swerve topic must be a struct array, and the robot must be enabled |
| no camera frames | the MJPEG URL (`curl -sI http://<coprocessor>:1181/stream.mjpg`) and `ffmpeg` on `PATH`: camera footage is recorded by `ffmpeg`, so without it there is none |
| `2D content has a pinhole transform frame ancestor` in a 2D view | that view is rooted outside the camera. Rerun only shows a pinhole's 2D content in a view rooted at the camera's `image` entity, which is why the bridge sends a `camera front` 2D view with the layout; a view at `first` or `first2d` cannot show camera pixels. |
| 3D frustum looks empty | the footage is drawn on the pinhole plane, `image_plane_distance` (2 m) in front of the lens, so it is only a couple of meters wide in a field-sized 3D view. Zoom in on the robot |
| values missing from `first/...` but graphed elsewhere | the topic type string the bridge saw |
