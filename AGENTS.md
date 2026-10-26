# AGENTS.md

## What this repo is

**FIRSTrun** turns FRC robot telemetry into [Rerun](https://rerun.io) recordings. It has two
independent surfaces:

- a Rust crate (`firstrun`) that parses WPILog (`*.wpilog`) files into Rerun chunks, compiled for
  native (console importer) and `wasm32` (browser importer), plus a static viewer app in `web/`;
- a Java SDK in `moneyshift/` that exposes Rerun's archetype/component type surface (generated
  from Rerun 0.40.0) and publishes it to NetworkTables.

Rerun is pinned to **0.40.0** everywhere (`Cargo.toml`, `xtask/Cargo.toml`). The Java type surface
is generated from the `re_type_definitions` git tag `0.40.0`.

## Repository map

| Path                                             | Purpose                                                                            |
| ------------------------------------------------ | ---------------------------------------------------------------------------------- |
| `src/lib.rs`                                     | Wasm-safe library entry: `import_to_rrd` / `import_wpilog`                         |
| `src/main.rs`                                    | `rerun-importer-firstrun`: CLI dispatch for the importer and `--nt` modes          |
| `src/log.rs`                                     | `EntryLog`: timestamped raw values keyed by `EntityPath`, with change tracking     |
| `src/values/`                                    | WPILog value parsing (primitive arrays, structs, WPILib schemas)                   |
| `src/wpilog/`                                    | WPILog container parser (records, payloads)                                        |
| `src/conv.rs`, `src/conv/components.rs`          | Heuristics that turn raw values into Rerun archetypes and chunks                   |
| `src/nt/`                                        | `/Rerun` column decoder (`protocol.rs`) and plain-topic adapter (`native.rs`)      |
| `src/live.rs`                                    | Live NT subscription, debounce, timestamps, sinks and reconnect                    |
| `src/first.rs`                                   | `/Firstrun/layout` declaration: field, robot and pose overlays for the live bridge |
| `src/importer.rs`, `src/cli.rs`                  | WPI-log to RRD importer and the hand-written CLI parser                            |
| `xtask/`                                         | Code generator: `re_type_definitions` → Java sources (`xtask/src/codegen/java.rs`) |
| `moneyshift/lib/`                                | Java library (`org.teamvan.moneyshift`)                                            |
| `moneyshift/example/`                            | Runnable Java demo                                                                 |
| `examples/wpilib-2027/`                           | Runnable WPILib 2027 robot (CommandSV2, AKit, PhotonVision) for integration checks |
| `test_data/FRC_TBD_d225b5377c70a88d.wpilog`      | Small AdvantageKit log used by tests                                               |
| `web/`                                           | Static site that loads the wasm importer + Rerun web viewer                        |
| `examples/decode_rrd.rs`, `examples/dump_rrd.rs` | Decode RRD files with Rerun's own decoder                                          |

## Commands

```sh
# Rust (nightly toolchain from rust-toolchain.toml; see .cargo/config.toml for wasm flags)
cargo test
cargo build --release --bin rerun-importer-firstrun
cargo build --release --lib --target wasm32-unknown-unknown

# Decode an RRD produced anywhere in this repo
cargo run --example decode_rrd -- path/to/file.rrd

# Java
cd moneyshift
./gradlew :lib:test :lib:build
./gradlew :lib:publishToMavenLocal
./gradlew :example:run

# WPILib 2027 example robot (resolves moneyshift from Maven Local)
cd examples/wpilib-2027
./gradlew build
```

The wasm artifact is built with `wasm-bindgen` **0.2.126** and then `wasm-opt -Oz` (see
`.github/workflows/pages.yml`); the site expects `web/dist/firstrun.js` and `firstrun_bg.wasm`.

## Code generation

- `cargo xtask java moneyshift/lib/src/main/java` regenerates the Java packages
  `archetypes/`, `components/`, `encodings/`, `blueprint/` from Rerun's type definitions. It
  deletes and rewrites those top-level packages; hand-written core classes, protobuf bindings, and
  tests are never touched.
- Generated files are checked in and start with `// DO NOT EDIT: generated from Rerun type
definitions.` **Never edit them by hand.** Fix the generator (`xtask/src/codegen/java.rs`) and
  rerun the command.
- `--rerun-worktree <path>` points the generator at a local Rerun checkout for development.
- The generator owns the ergonomic extras (unwrapped constructors, builder overloads); keep them
  intact when changing codegen.

## Rust conversion pipeline

`wpilog bytes → WpiLogFile::parse → EntryLog → conv::log_changes_to_chunks_with_options → Chunk`

Struct schemas live in `StructSchemas` (`src/values/parse/wpistruct.rs`): it keeps the raw
schemas and a memoized cache of resolved layouts (nested structs are shared through `Arc`), so
samples only clone an `Arc`. Schema parsing **fails closed** — a schema that cannot be fully
understood is rejected instead of truncated, because a truncated schema silently changes the byte
layout of every value. Resolution rejects self-referential schemas. Decoded values are
`EntryValue::Struct`/`StructArray`, carrying the shared schema plus a `Vec` of field values.
Unknown struct types are queued until their schema arrives, capped by `MAX_QUEUED_STRUCTS`.

- `EntryLog` stores one Arrow array per `(entity, timestamp)` and tracks a changed set. It also
  retains WPILog type strings and struct schemas, which the heuristics need.
- `conv/components.rs` recognizes WPILib structs by type name (`Pose2d`, `Translation3d`,
  `ChassisSpeeds`, `SwerveModuleState`, …), including `struct:` prefixes, arrays, and
  fully-qualified class names, and turns them into Rerun archetypes. It also honors
  AdvantageKit's `.type` / `.components` metadata topics.
- Primitive values map to `Scalars` (numbers), `TextDocument` (strings), and `Blob`; booleans stay
  booleans as a dynamic `value` component (Rerun has no bool archetype), never float64 0/1.
- Struct arrays are expanded under stable indices (`<root>/length`, `<root>/0/...`), matching the
  WPILog convention.
- `test_data/*.wpilog` is a real AdvantageKit file; tests assert specific archetypes/entities from
  it. Treat the fixture as read-only.

## Java library

- Hand-written runtime: `NtRecorder`, `NtEntityWriter`, `NtPath`, `Archetype`, `NtComponent`,
  `NtEncoding`. The old wire-protocol classes (Arrow, protobuf, gRPC, RRD, blueprints) are gone;
  do not reintroduce them.
- `org.teamvan.moneyshift.frc.FirstMetadata` publishes the `/Firstrun/layout` declaration the
  bridge reads; `src/first.rs` owns the drawing. Robot bodies span `0..2h` above the carpet (a
  pose is on the floor) and swerve vectors are drawn above the body. A camera's optional ninth
  argument is PhotonVision's MJPEG URL. There is no NT transport for camera frames, and no HTTP or
  MJPEG code in the bridge: `src/mjpeg.rs` runs one `ffmpeg` per camera on that URL (`-f mpjpeg`).
- A camera's footage is H.264 `VideoStream` samples on `<camera>/image`, one level below the
  static `Transform3D` and `Pinhole`. Do not re-add per-frame JPEGs to the live path as a workaround
  for 3D (it is what made the feed flicker). `VideoStream` *is* projected onto the pinhole plane in a
  3D view — Rerun's docs list `Spatial3DView` for it "if logged under a projection", and a bare
  `Pinhole` carrying a stream renders there. In this bridge's layout it does not: the camera hangs
  under the field and robot transforms, and Rerun rejects the content when the stream's transform
  tree root is not the pinhole itself (the 2D view rooted at the stream still plays it). That is the
  open bug; verify any fix by screenshotting a 3D view of a recording with footage.
- In simulation the camera URL is live: `VisionSystemSim.addCamera` starts PhotonVision's camera
  server, so `http://127.0.0.1:1181/stream.mjpg` serves the simulated camera rendering the field and
  its AprilTags as a 3D scene. Simulation is therefore a real end-to-end camera source — the bridge
  runs `ffmpeg` against it exactly as it does on a robot.
- In simulation PhotonVision serves the camera itself: `VisionSystemSim.addCamera` starts a camera
  server that streams what the simulated camera sees at `http://127.0.0.1:1181/stream.mjpg`, so the
  demo's `Constants.CAMERA_STREAM` URL carries real frames in sim and the bridge records them.
- `ffmpeg` reads the MJPEG URL itself; nothing in the crate parses HTTP or multipart. It needs
  `-probesize 32 -analyzeduration 0`, or it analyzes seconds of input before its first output and
  the camera looks dead. Samples must carry strictly increasing `robot_time` (`frame_time` clamps
  them), or the viewer restarts playback and the feed flickers.
- Rerun only renders a pinhole's 2D content in a 2D view rooted at *that content*: a view rooted at
  the camera or the field reports "the 2D view's target frame doesn't have a pinhole root". When a
  declaration names a camera, `first::log_blueprint` sends a viewer layout with a 3D view of the
  field plus one 2D view per camera at `<camera>/image` (automatic views stay on). It is sent for
  live sessions only, once per layout arrival.
- The 2D lane (`first2d/...`) keeps WPILib's field frame (`+x` toward red, `+y` toward blue's
  left, never negated) so it lines up with every other 2D WPILib topic in the recording; Rerun 2D
  views themselves put `+y` down the screen.
- WPILib 2027 renamed `SwerveModuleState` to `SwerveModuleVelocity` and `ChassisSpeeds` to
  `ChassisVelocities`. The bridge accepts both struct names; the example project publishes the
  2027 shape.
- Tests publish to in-process NT instances and assert exact topic paths and values.
- Published artifacts (jar, sources, javadoc) must each stay under **5 MB**;
  `:lib:checkPublicationSize` enforces it and runs from `:lib:check`. Javadoc uses `-no-fonts`
  because the JDK's DejaVu webfonts alone blew the budget.

## Rerun external importer contract (0.40.0)

Useful when changing the console binary:

- The viewer discovers executables named `rerun-importer-*` (legacy `rerun-loader-*`) on `PATH`.
- It runs `<path> --application-id … --recording-id … [--opened-application-id …]
[--opened-recording-id …] [--entity-path-prefix …] [--timeless --static]
[--time_sequence name=value] [--time_duration_nanos name=value]
[--time_timestamp_nanos name=value]` (plus legacy aliases).
- **stdout is the RRD stream**, stderr is for logs. Never print to stdout in importer mode.
- Exit code **66** means "this file is not mine"; any other non-zero code is a failure.
- The child process is spawned with `RERUN_APP_ONLY` removed.
- Implementation reference: `re_importer-0.40.0/src/importer_external.rs`.

## NetworkTables migration invariants

- Root namespace is exactly `/Rerun`; entity segments are percent-encoded UTF-8
  (`[A-Za-z0-9_-]` unreserved, `/` separator).
- Metadata lives under `<entity>/.rerun/` (`layout`, `archetype`, `components`, `static`).
- Component columns are native NT arrays (`double[]`, `long[]`, `boolean[]`, `string[]`, `raw`)
  laid out per the datatype table in TASK.md. No JSON envelopes for values. `UInt64` leaves are
  `long[]` carrying two's-complement bits (the decoder reinterprets them as unsigned), and `List`
  nodes publish `/.offsets` prefix sums (one more entry than instances) plus flattened `/.items`.
- Nullable nodes publish their mask at `<path>/.present` only when a batch contains nulls (or when
  the mask was published before, so it can be refreshed). The generator emits the mask lazily and
  `NtRecorder.writePresent` fills it for all-present batches; absent masks decode as all-valid.
  Skip writing a mask for a nullable-but-always-present node rather than adding a constant
  all-true column.
- Fixed-size-list component columns are published per element component (`positions/0`), but
  Rerun stores fixed-size lists contiguously per instance. The decoder must interleave them; a
  concat of the columns is only correct for single-component lists.
- The decoder resolves each published column into a per-component trie of column segments
  (`ComponentPlan`) and then walks it, so no path string is built or hashed per datatype node.
  Keep new wire columns as trie children instead of looking them up by string.
- Struct values in WPILog data are parsed straight into `EntryLog` (`add_struct_bytes`); the
  `EntryValue::Struct` tree is the reference implementation and must stay in sync.
- `moneyshift/lib/src/test/resources/rerun_wire_fixture.json` pins the wire layout. It is produced
  by the Java `WireFixtureTest` and decoded by `tests/wire_fixture.rs`; regenerate it (see
  `moneyshift/README.md`) when a layout change is intentional.
- Java publishes through the `NtRecorder` singleton: `NtRecorder.getInstance()` uses
  `NetworkTableInstance.getDefault()`; `NtRecorder.setInstance(inst)` swaps it (null resets to the
  default). The recorder never connects/starts/stops the NT instance, and the topic root is the
  fixed constant `/Rerun`.
- The Rust decoder uses `re_sdk_types::reflection::reflection()` to get component Arrow datatypes;
  do not add per-type Rust codegen. `plan_entity` resolves descriptors and datatypes once per
  metadata change (`live.rs` caches the plan per entity); `decode_planned` then builds the arrays.
- Live mode maps `/Rerun/...` with the protocol decoder and everything else through the existing
  `EntryLog` + `conv` heuristics. `robot_time` comes from the NT server timestamp by default;
  `log_time` is the SDK's wall clock.

## Gotchas

- Viewer behavior (layouts, 2D/3D view rules, per-view warnings) can be checked without a GUI:
  `~/.cargo/bin/rerun --screenshot-to /tmp/shot.png some.rrd` writes a PNG of the rendered viewer.
  Pane contents are only readable after downscaling a very large screenshot. This is how the
  camera-pinhole layout rules above were verified.

- `nt_client` is client-only and tokio-based; live code must be `#[cfg(not(target_arch = "wasm32"))]`.
  The same applies to `re_sdk` and `tokio` in `Cargo.toml`.
- `re_sdk::RecordingStreamBuilder::spawn` shells out to a `rerun` executable on `PATH`; it does not
  embed a viewer. `native_viewer` must not come back.
- `ntcore-java`/`ntcore-jni` versions must match; the release repo's JNI artifacts lag the Java
  artifacts. Pin one version for all four WPILib artifacts.
- Tests that use NT must add the Linux x86-64 JNI classifiers as `testRuntimeOnly`; the native
  library is loaded through `wpiutil-java`'s `RuntimeLoader`.
- The release profile is size-optimized (`lto`, `opt-level = "z"`, `panic = "abort"`), so avoid
  pulling heavy dependencies into the binary.
- `.cargo/config.toml` sets `clang` + `lld` on Linux and the `xtask` alias. The wasm target has
  extra feature flags; keep them in sync with `wasm-opt` in CI.
- When changing user-facing behavior, update the relevant README (`moneyshift/README.md`,
  `example/README.md`, or the top-level docs) in the same change.
