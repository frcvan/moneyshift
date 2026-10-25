# moneyshift

A Java API for publishing [Rerun](https://rerun.io) data from FRC robot code over
**NetworkTables 4**.

The SDK keeps Rerun 0.40.0's archetype/component/encoding type surface (generated from Rerun's own
type definitions) and re-targets it to plain NT topics under `/Rerun`. The Rust
`rerun-importer-firstrun --nt` bridge turns those topics back into Rerun chunks in real time, so
Java never touches Arrow, protobuf, gRPC or RRD.

## Usage

```java
import org.teamvan.moneyshift.NtRecorder;
import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.components.Position3D;

NtRecorder rec = NtRecorder.getInstance();

rec.log("world/points", Points3D.builder()
        .withPositions(Position3D.of(1, 2, 3), Position3D.of(4, 5, 6))
        .withColors(0xFF00E5FFL)
        .withRadii(0.03f)
        .build());

rec.logStatic("world/annotations", /* ... */);
rec.logComponent("debug/origin", "position", List.of(Position3D.of(0, 0, 0)));
rec.flush();
```

`NtRecorder` is a process-wide singleton over `NetworkTableInstance.getDefault()`. It never
connects, starts or stops the NT instance - the team owns the server. Tests and tools can swap the
backing instance with `NtRecorder.setInstance(instance)`; `null` restores the default.

Then run the bridge on the laptop or coprocessor:

```sh
rerun-importer-firstrun --nt --team 254 --spawn
```

### Entities, static data and timelines

- `log(entity, archetype)` publishes a dynamic update; the entity path is relative to `/Rerun`.
- `logStatic(entity, archetype)` publishes the values once and marks the entity static. An entity
  cannot switch between static and dynamic; the recorder throws.
- `logComponent(entity, field, values)` publishes a component without an archetype.
- There are no user timelines. The bridge derives `robot_time` from the NT server timestamp (or
  `--robot-time-topic`) and `re_sdk` adds wall-clock `log_time`.

Entity segments are percent-encoded UTF-8, so spaces, dots, `=` and unicode are safe, and a user
segment can never produce a reserved `.rerun` segment. The root entity is not addressable: every
entity path needs at least one non-empty segment, because a root prefix would also cover every
other entity's topics. `log("", ...)`, `log("/", ...)` and `NtPath.encodeEntityPath("")` throw
`IllegalArgumentException`.

## What is on the wire

For one log call:

```
/Rerun/world/points/.rerun/layout          long    1
/Rerun/world/points/.rerun/archetype       string  rerun.archetypes.Points3D
/Rerun/world/points/.rerun/components      string  positions=rerun.components.Position3D,colors=...
/Rerun/world/points/positions/0            double[]
/Rerun/world/points/positions/1            double[]
/Rerun/world/points/positions/2            double[]
/Rerun/world/points/colors                 long[]
```

The layout is derived from each component's Arrow datatype: primitives become native NT arrays,
fixed-size lists recurse into `/0`, `/1`, …; structs into field names; lists into `/.offsets`
(prefix sums, one entry more than instances) plus flattened `/.items`; dense unions into `/.variant`
plus one payload path per variant; and nullable nodes add a `/.present` mask. Unsigned 64-bit
columns are published as `raw`, eight little-endian bytes per instance, so the full unsigned range
needs no second topic. The `layout` marker is `2` for this layout (`1` was `/.count` plus a
`/.u64` decimal sidecar). Metadata topics (including the `.rerun/components` string, which is built
once per present-component set) are only republished when the archetype or component set changes.

### Unwrapped values

Rerun models every value as a component wrapping a datatype wrapping a primitive, which is noisy to
type. The SDK therefore accepts raw data everywhere:

```java
Points3D.builder()
        .withPositions(Position3D.of(1, 2, 3))
        .withColors(0xFF00E5FFL)          // instead of new Color(new Rgba32(0xFF00E5FFL))
        .withRadii(0.03f, 0.05f)          // instead of new Radius(new Float32(0.03f))
        .withLabels("sensor")             // instead of new Text(new Utf8("sensor"))
        .build();

new AnnotationInfo(1, "sensor return", 0xFF00E5FFL)
new RotationAxisAngle(Vec3D.of(0, 0, 1), 0.5f)
```

Every generated component and encoding has an unwrapped constructor, and every archetype builder
has overloads for the primitive behind a component. The canonical constructors and
`withX(List<Component>)` setters remain available.

## Requirements

WPILib **2027** (`org.wpilib.networktables`). Java language level is **25**; roboRIO deployment of
this SDK is out of scope. The library has no Arrow, gRPC, protobuf or LZ4 dependencies.

## Generating the type surface

```sh
cargo xtask java moneyshift/lib/src/main/java
```

The command reads `re_type_definitions` (the Rerun git tag pinned in `xtask/Cargo.toml`), removes
the previously generated `archetypes/`, `components/`, `encodings/` packages, and rewrites them.
`xtask/src/codegen/java.rs` emits the column writers and the ergonomic overloads as part of that
pass. Unsupported datatypes fail at codegen time rather than dropping data at runtime.

## Building and testing

```sh
./gradlew :lib:test :lib:build
./gradlew :example:run     # see example/README.md
```

Most tests publish to in-process NT instances and assert exact topic paths and values.
`WireFixtureTest` additionally dumps every topic of a fixed set of archetypes to
[`lib/src/test/resources/rerun_wire_fixture.json`](lib/src/test/resources/rerun_wire_fixture.json).
The Rust test `tests/wire_fixture.rs` decodes that same file with Rerun's reflection datatypes, so
the Java writer and the Rust bridge cannot drift apart silently. After an intentional layout
change, regenerate the fixture and commit it with the codegen output:

```sh
./gradlew :lib:test --tests '*WireFixtureTest' -Dfirstrun.writeFixture=true
```

## Publishing

```sh
./gradlew :lib:publishToMavenLocal   # or :lib:publish
```

Each published artifact (jar, sources, javadoc) must stay under 5 MB. Javadoc is generated with
`-no-fonts` because the JDK otherwise bundles ~4 MB of DejaVu webfonts into the docs.
`:lib:checkPublicationSize` enforces the budget and runs as part of `:lib:check`.

## Architecture

```
NtRecorder.log(...)
    -> generated writeColumns() for the archetype
    -> per-leaf NetworkTableEntry writes (native NT arrays)
    -> team's NT4 server
```

There is no serialization buffer, background thread or compression step: one log call is a handful
of NT entry writes under a per-entity lock, followed by the team's normal NT flush cadence.
