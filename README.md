# FIRSTrun

Turn FRC robot telemetry into [Rerun](https://rerun.io) recordings and live streams.

FIRSTrun has four surfaces that all speak Rerun 0.40.0:

| Surface | What it does |
|---|---|
| `rerun-importer-firstrun` | Rerun **external importer**: `*.wpilog` in, RRD out on stdout |
| `rerun-importer-firstrun --nt` | **Live bridge**: NetworkTables topics in, Rerun stream out |
| `moneyshift/` | **Java SDK** that publishes Rerun data to NetworkTables from robot code |
| `web/` | Static browser importer (the wasm build of the same `*.wpilog` importer) |

## Install the importer

Build and put the binary on `PATH` (the Rerun viewer discovers executables named
`rerun-importer-*`):

```sh
cargo build --release --bin rerun-importer-firstrun
install -m 755 target/release/rerun-importer-firstrun ~/.local/bin/
```

Then open a log in the viewer as usual - `rerun match.wpilog` - and the importer handles it. The
contract is Rerun's external-importer protocol: RRD on stdout, logs on stderr, exit code **66**
when the file is not a WPI log, **2** for bad arguments. `--static`, `--entity-path-prefix`,
`--time_sequence` and `--time_duration_nanos` (and the legacy `--sequence`/`--time` aliases) are
honored.

You can also import without a viewer:

```sh
rerun-importer-firstrun match.wpilog > match.rrd
rerun-importer-firstrun match.wpilog --entity-path-prefix robot --static > scene.rrd
```

## Live mode

The live bridge subscribes to every topic on the team's NetworkTables server and maps:

- `/Rerun/...` topics published by the Java `NtRecorder` through Rerun's own type reflection;
- everything else through the same WPILib-struct heuristics the WPILog importer uses, so plain
  robot code, AdvantageKit and dashboard topics are graphed without extra work.

Robot code can also publish one `/Firstrun/layout` string (see
[`FirstMetadata`](moneyshift/README.md#first-field-and-robot-overlays)). It declares which topics
carry each robot's pose, swerve module states and planned paths, and what the field and cameras
look like; the bridge then adds the carpet, field lines, tags, robot bounding boxes, swerve
vectors, camera frustums, paths and a 2D top-down mirror under `first/` and `first2d/` - no
`/Rerun` topics required on the robot.

A camera can also name the MJPEG URL PhotonVision serves for it
(`http://<coprocessor>:1181/stream.mjpg`). The bridge opens that `multipart/x-mixed-replace` stream
itself over a plain socket and logs one `EncodedImage` per JPEG frame on
`first/robots/<team>/cameras/<name>/image`, so everything the camera sends is recorded and there is
nothing extra to install. Only plain `http://` is supported: a URL that needs TLS, redirects or a
proxy is reported and retried rather than followed.

A camera's 2D view has to be rooted at that `image` entity: Rerun only shows a pinhole's 2D content
inside that content's own 2D space, and a view rooted at the field reports that its target frame
has no pinhole root. The bridge sends a viewer layout with a 2D view per camera, whether or not
robot code declared a layout of its own, so a live session always opens with a pane that works.

The same frames also land on the pinhole plane in the 3D view, `image_plane_distance` in front of
the lens. That plane is only a couple of meters across, so on a field-wide view the picture is a
small patch next to the robot rather than a full-size pane; zoom in on the robot to read it.

### The viewer layout

Robot code decides what the Rerun window opens with. `FirstMetadata` takes an optional `blueprint`
section: `container(Container.GRID | TABS | HORIZONTAL | VERTICAL)` and any number of
`view(kind, name, origin, entities…)` calls, where `kind` is a `ViewKind` of `THREE_D`, `TWO_D` or
`DATAFRAME`.
A dataframe view lists exactly the entity paths you name, so tabulating an opmode string is a
decision on the robot, not a guess in the bridge; with none named it shows the recording's
timeline alone.

Declare nothing and the bridge sends its default: a 3D view of the field, a 2D view of it, a
dataframe of the timeline, and — when the declaration has cameras — a grid of their 2D views.
Declared views replace that default, but the cameras keep a 2D view either way. The camera panes
follow the order you declared them in, so a layout never shuffles between runs; when two robots
mount a camera with the same name, the title carries the team (`camera front (6391)`).

The bridge also turns the viewer's automatic views off, because its data spans several independent
trees (`first/`, `first2d/` and every NetworkTables path). Rerun's automatic layout answers that
with a view rooted at `/`, which sweeps in the cameras and reports that a pinhole cannot be drawn
in it. Topics no view names are still recorded and browsable in the entity tree; name the ones you
want in a view to get a pane for them.

`cargo run --example first_field -- <model.glb> <out.rrd> [clip.h264]` renders a field with two
swerve robots, camera video and a planned path for checking the overlays without a robot. Without
a clip it renders a synthetic one with `ffmpeg`, and skips video when `ffmpeg` is missing.

[`examples/wpilib-2027`](examples/wpilib-2027/README.md) is a runnable WPILib 2027 robot with
AdvantageKit, PhotonVision and a simulated swerve drive that publishes the layout, so
`--nt --save` records a real robot end to end.

```sh
rerun-importer-firstrun --nt --team 254 --spawn
rerun-importer-firstrun --nt --nt-addr 10.0.0.2 --save /tmp/live.rrd
rerun-importer-firstrun --nt --robot-time-topic /FMSInfo/MatchTime --connect rerun+http://127.0.0.1:9876/proxy
```

Sinks are mutually exclusive: `--spawn` (default, needs `rerun` on `PATH`), `--connect <url>`,
`--save <path.rrd>`, `--stdout`. `robot_time` comes from the NT server timestamp unless
`--robot-time-topic` is set (`double` seconds or `int` microseconds); `log_time` is wall clock.
Ctrl-C flushes and exits 0. The bridge reconnects both sides and keeps the entity metadata cache
across reconnects: NetworkTables is dialed again with backoff, and a Rerun viewer that was closed
is reopened (`--spawn`) or retried (`--connect`) and gets the field, robots, cameras and layout
again. `--save` and `--stdout` have no connection to lose and are never restarted.

`--field-model <path>` and `--robot-model <path>` select your own glTF/GLB/OBJ/STL models
instead of waiting for the declaration to name a field model or settling for robot boxes. The
field model is drawn in the field frame; a robot model is drawn in the robot frame (`+x` forward,
`+y` left, `+z` up) with its origin at the footprint center on the carpet, and replaces the box on
every declared robot. A robot model that cannot be read only falls back to the boxes.

On the robot side, see [`moneyshift/README.md`](moneyshift/README.md):

```java
NtRecorder rec = NtRecorder.getInstance();
rec.log("world/points", Points3D.builder()
        .withPositions(Position3D.of(1, 2, 3))
        .withColors(0xFF00E5FFL)
        .build());
```

## Browser importer

`web/` loads the wasm build of the WPILog importer and Rerun's web viewer. Build the artifact with:

```sh
cargo build --release --lib --target wasm32-unknown-unknown
wasm-bindgen --target web --out-dir web/dist target/wasm32-unknown-unknown/release/firstrun.wasm
```

## Development

```sh
cargo test                                            # Rust: importer, decoder, live logic
cargo test --test importer_cli                        # external-importer protocol smoke tests
cargo test --test wire_fixture                        # decodes the Java SDK's wire fixture
cd moneyshift && ./gradlew :lib:test :example:test     # Java
cargo run --example decode_rrd -- /tmp/live.rrd        # decode any RRD with Rerun's decoder
```

`test_data/FRC_TBD_d225b5377c70a88d.wpilog` is a real AdvantageKit log used by the tests; treat it
as read-only.

The Java type surface is generated: edit `xtask/src/codegen/java.rs` and run
`cargo xtask java moneyshift/lib/src/main/java`. Generated files are checked in and marked
`DO NOT EDIT`.

The `/Rerun` wire format is shared by the Java writer and the Rust bridge, so it is pinned by a
checked-in fixture: `moneyshift/lib/src/test/resources/rerun_wire_fixture.json` is produced by the
Java `WireFixtureTest` and decoded by `tests/wire_fixture.rs`. Regenerate it after an intentional
layout change with `./gradlew :lib:test --tests '*WireFixtureTest' -Dfirstrun.writeFixture=true`
and commit it together with the codegen output.

## Layout

| Path | Purpose |
|---|---|
| `src/importer.rs`, `src/cli.rs`, `src/main.rs` | Console importer and CLI dispatch |
| `src/nt/protocol.rs` | `/Rerun` NetworkTables column decoder |
| `src/nt/native.rs` | Plain NT topic adapter |
| `src/live.rs` | Live subscription, debounce, timestamps, sinks, reconnect |
| `src/conv/`, `src/log.rs`, `src/values/`, `src/wpilog/` | WPILog parsing and Rerun conversion heuristics |
| `moneyshift/` | Java SDK and example |
| `web/` | Browser importer |
| `xtask/` | Java code generator |
