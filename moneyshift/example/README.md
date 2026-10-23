# moneyshift example

A small Java application that publishes a simulated robot "sensor sweep" through the `moneyshift`
SDK and NetworkTables. It exercises the public API: archetypes, unwrapped values, static data,
nested encodings, and plain NT topics alongside the Rerun ones.

## Run it

From `moneyshift/`:

```sh
./gradlew :example:run
```

By default the demo starts an embedded NT4 server on port 5810, publishes 60 frames, prints the
bridge command, and exits. Start the Rust bridge against the same server:

```sh
cargo run --bin rerun-importer-firstrun -- --nt --save /tmp/live.rrd
rerun --load /tmp/live.rrd
```

The bridge connects to `127.0.0.1:5810` by default, so it must be running while the demo publishes.
Use `--linger 60` to keep the embedded server alive and run the bridge at your leisure:

```sh
./gradlew :example:run --args="--linger 60"
```

Other flags:

| Flag | Meaning |
|---|---|
| `--frames <n>` | number of frames to publish (default 60) |
| `--port <n>` | port of the embedded server (default 5810) |
| `--linger <s>` | keep the server alive after publishing |
| `--client <host>` | publish to an existing server instead of starting one |

To use a robot or simulator's NT server instead of the embedded one:

```sh
./gradlew :example:run --args="--client 127.0.0.1"
```

## What it covers

- `NtRecorder.getInstance()` / `setInstance(...)` and `logStatic`, `log`, `logComponent`, `flush`.
- Static data (annotation context and series styling) and per-frame data.
- Archetypes: `Points3D`, `LineStrips3D`, `Boxes3D`, `Transform3D`, `Scalars`, `SeriesLines`,
  `TextLog` and `AnnotationContext`.
- Unwrapped values: builders take primitives and strings directly (`withColors(0xFF00E5FFL)`,
  `withRadii(0.03f)`, `withLevel("INFO")`), and nested encodings are constructed without the
  intermediate wrappers (`new AnnotationInfo(1, "sensor return", CYAN)`,
  `new RotationAxisAngle(Vec3D.of(0, 0, 1), phase)`).
- A plain `/Demo/Drive/Speed` double, which the Rust bridge maps with the same heuristics it uses
  for AdvantageKit logs - no Rerun types required on the robot.

## Verify it

```sh
./gradlew :example:test
```

The test publishes the demo into an in-process NT instance and asserts the resulting topic tree:
metadata topics, per-leaf value arrays, static markers, and the plain topic.
