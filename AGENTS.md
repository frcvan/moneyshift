# AGENTS.md

## What this repo is

**FIRSTrun** turns FRC robot telemetry into [Rerun](https://rerun.io) recordings. It has two
independent surfaces:

- a Rust crate (`firstrun`) that parses WPILog (`*.wpilog`) files into Rerun chunks, compiled for
  native (console importer) and `wasm32` (browser importer), plus a static viewer app in `web/`;
- a Java SDK in `moneyshift/` that exposes Rerun's archetype/component type surface (generated
  from Rerun 0.38.1) and publishes it to NetworkTables.

Rerun is pinned to **0.38.1** everywhere (`Cargo.toml`, `xtask/Cargo.toml`). The Java type surface
is generated from the `re_type_definitions` git tag `0.38.1`.

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
| `src/importer.rs`, `src/cli.rs`                  | WPI-log to RRD importer and the hand-written CLI parser                            |
| `xtask/`                                         | Code generator: `re_type_definitions` → Java sources (`xtask/src/codegen/java.rs`) |
| `moneyshift/lib/`                                | Java library (`org.teamvan.moneyshift`)                                            |
| `moneyshift/example/`                            | Runnable Java demo                                                                 |
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

- `EntryLog` stores one Arrow array per `(entity, timestamp)` and tracks a changed set. It also
  retains WPILog type strings and struct schemas, which the heuristics need.
- `conv/components.rs` recognizes WPILib structs by type name (`Pose2d`, `Translation3d`,
  `ChassisSpeeds`, `SwerveModuleState`, …), including `struct:` prefixes, arrays, and
  fully-qualified class names, and turns them into Rerun archetypes. It also honors
  AdvantageKit's `.type` / `.components` metadata topics.
- Primitive values map to `Scalars` (numbers, bools), `TextDocument` (strings), and `Blob`.
- Struct arrays are expanded under stable indices (`<root>/length`, `<root>/0/...`), matching the
  WPILog convention.
- `test_data/*.wpilog` is a real AdvantageKit file; tests assert specific archetypes/entities from
  it. Treat the fixture as read-only.

## Java library

- Hand-written runtime: `NtRecorder`, `NtEntityWriter`, `NtPath`, `Archetype`, `NtComponent`,
  `NtEncoding`. The old wire-protocol classes (Arrow, protobuf, gRPC, RRD, blueprints) are gone;
  do not reintroduce them.
- Tests publish to in-process NT instances and assert exact topic paths and values.
- Published artifacts (jar, sources, javadoc) must each stay under **5 MB**;
  `:lib:checkPublicationSize` enforces it and runs from `:lib:check`. Javadoc uses `-no-fonts`
  because the JDK's DejaVu webfonts alone blew the budget.

## Rerun external importer contract (0.38.1)

Useful when changing the console binary:

- The viewer discovers executables named `rerun-importer-*` (legacy `rerun-loader-*`) on `PATH`.
- It runs `<path> --application-id … --recording-id … [--opened-application-id …]
[--opened-recording-id …] [--entity-path-prefix …] [--timeless --static]
[--time_sequence name=value] [--time_duration_nanos name=value]` (plus legacy aliases).
- **stdout is the RRD stream**, stderr is for logs. Never print to stdout in importer mode.
- Exit code **66** means "this file is not mine"; any other non-zero code is a failure.
- The child process is spawned with `RERUN_APP_ONLY` removed.
- Implementation reference: `re_importer-0.38.1/src/importer_external.rs`.

## NetworkTables migration invariants

- Root namespace is exactly `/Rerun`; entity segments are percent-encoded UTF-8
  (`[A-Za-z0-9_-]` unreserved, `/` separator).
- Metadata lives under `<entity>/.rerun/` (`layout`, `archetype`, `components`, `static`).
- Component columns are native NT arrays (`double[]`, `long[]`, `boolean[]`, `string[]`, `raw`)
  laid out per the datatype table in TASK.md. No JSON envelopes for values.
- Java publishes through the `NtRecorder` singleton: `NtRecorder.getInstance()` uses
  `NetworkTableInstance.getDefault()`; `NtRecorder.setInstance(inst)` swaps it (null resets to the
  default). The recorder never connects/starts/stops the NT instance, and the topic root is the
  fixed constant `/Rerun`.
- The Rust decoder uses `re_sdk_types::reflection::reflection()` to get component Arrow datatypes;
  do not add per-type Rust codegen.
- Live mode maps `/Rerun/...` with the protocol decoder and everything else through the existing
  `EntryLog` + `conv` heuristics. `robot_time` comes from the NT server timestamp by default;
  `log_time` is the SDK's wall clock.

## Gotchas

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
