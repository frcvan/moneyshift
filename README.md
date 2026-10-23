# FIRSTrun

Turn FRC robot telemetry into [Rerun](https://rerun.io) recordings and live streams.

FIRSTrun has four surfaces that all speak Rerun 0.38.1:

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

```sh
rerun-importer-firstrun --nt --team 254 --spawn
rerun-importer-firstrun --nt --nt-addr 10.0.0.2 --save /tmp/live.rrd
rerun-importer-firstrun --nt --robot-time-topic /FMSInfo/MatchTime --connect rerun+http://127.0.0.1:9876/proxy
```

Sinks are mutually exclusive: `--spawn` (default, needs `rerun` on `PATH`), `--connect <url>`,
`--save <path.rrd>`, `--stdout`. `robot_time` comes from the NT server timestamp unless
`--robot-time-topic` is set (`double` seconds or `int` microseconds); `log_time` is wall clock.
Ctrl-C flushes and exits 0. The bridge reconnects with backoff and keeps the entity metadata cache
across reconnects.

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
cd moneyshift && ./gradlew :lib:test :example:test     # Java
cargo run --example decode_rrd -- /tmp/live.rrd        # decode any RRD with Rerun's decoder
```

`test_data/FRC_TBD_d225b5377c70a88d.wpilog` is a real AdvantageKit log used by the tests; treat it
as read-only.

The Java type surface is generated: edit `xtask/src/codegen/java.rs` and run
`cargo xtask java moneyshift/lib/src/main/java`. Generated files are checked in and marked
`DO NOT EDIT`.

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
