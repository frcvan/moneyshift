# moneyshift example

A small Java application that exercises the `moneyshift` SDK through its public API. It simulates
a robot doing a sensor sweep and logs the result as a Rerun recording.

## Run it

From `moneyshift/`:

```sh
./gradlew :example:run
```

The demo writes three `.rrd` files under `example/build/moneyshift-example/`:

| File             | What it shows                                                                 |
|------------------|-------------------------------------------------------------------------------|
| `demo.rrd`       | Blueprint store + recording store in one file, followed by an activation command |
| `recording.rrd`  | The `save(Path)` file sink                                                    |
| `memory.rrd`     | The `memory()` sink, retrieved with `toRrdBytes()`                            |

Open the result with the Rerun viewer:

```sh
rerun moneyshift/example/build/moneyshift-example/demo.rrd
```

Pass `--out <dir>` to write somewhere else, or `--connect` to also stream live to a running viewer
over gRPC:

```sh
./gradlew :example:run --args="--connect rerun+http://127.0.0.1:9876/proxy"
```

## What it covers

- Recording, blueprint and memory streams with `RecordingStreamBuilder`.
- A custom `Transport` that puts two stores into a single RRD file, and blueprint activation with
  `RecordingStream.activateBlueprint`.
- Timelines of all three kinds: sequence (`frame`), duration (`sim_time`) and timestamp
  (`wall_time`).
- Static data and per-frame data.
- Archetypes: `Points3D`, `LineStrips3D`, `Boxes3D`, `Transform3D`, `Scalars`, `SeriesLines`,
  `TextLog`, `ViewBlueprint` and `AnnotationContext`.
- Unwrapped values: builders take primitives and strings directly (`withColors(0xFF00E5FFL)`,
  `withRadii(0.03f)`, `withLevel("INFO")`), and nested encodings are constructed without the
  intermediate wrappers (`new AnnotationInfo(1, "sensor return", CYAN)`,
  `new RotationAxisAngle(Vec3D.of(0, 0, 1), phase)`).

## Verify it

```sh
./gradlew :example:test
```

The test runs the demo into a temporary directory and parses the RRD framing to check that the
shared file contains both stores, that every sink produced Arrow chunks, and that the blueprint
activation command is present.
