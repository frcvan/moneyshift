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

## FIRST field and robot overlays

`org.teamvan.moneyshift.frc.FirstMetadata` declares FIRST data for the bridge. It is not Rerun
protocol data: one plain NetworkTables string at `/Firstrun/layout`.

```java
FirstMetadata.builder()
        .robot(254, "/AdvantageKit/RealOutputs/Drive/Pose2d")
        .swerve(254, "/AdvantageKit/RealOutputs/SwerveStates/Measured")
        .camera(254, "front", 0.30, 0.0, 0.50, 0.0, -10.0, 0.0, 70.0,
                "http://10.6.32.8:1181/stream.mjpg")
        .path("auto", "/AdvantageKit/RealOutputs/PathPlanner/ActivePath")
        .build()
        .publish(NtRecorder.getInstance());
```

The pose topic may publish `struct:Pose2d`, `struct:Pose3d`, or a `double[]` of `[x, y, yaw]`; the
swerve topic publishes `struct:SwerveModuleState[]` (WPILib 2027 calls it
`struct:SwerveModuleVelocity[]`, which the bridge also accepts); path topics publish
`struct:Pose2d[]` or `struct:Translation2d[]`.

A camera optionally takes a ninth argument, PhotonVision's MJPEG URL for it
(`http://<coprocessor>:1181/stream.mjpg` for camera 0, `:1182` for camera 1). A camera is only a
link to that `multipart/x-mixed-replace` stream: the bridge reads it itself and logs one
`EncodedImage` per JPEG on the camera's `image` entity at the recording's `robot_time`, so there is
no external process to install and every frame the camera sends is recorded.

A 2D view of a camera must be rooted at its `image` entity: Rerun only shows a pinhole's 2D content
inside that content's own 2D space, so a view rooted at the field reports that its target frame has
no pinhole root. When the declaration names a camera the bridge always sends a viewer layout with a
2D view for it, whether or not you declared views of your own. The 3D view shows the same frames on
the pinhole plane, a couple of meters in front of the lens, so zoom in on the robot to see it there.

The viewer layout is part of the same declaration:

```java
FirstMetadata.builder()
        .robot(254, Constants.POSE_TOPIC)
        .container(FirstMetadata.Container.GRID)
        .view(FirstMetadata.ViewKind.THREE_D, "Field", "first")
        .view(FirstMetadata.ViewKind.TWO_D, "Field 2D", "first2d")
        .view(FirstMetadata.ViewKind.DATAFRAME, "Robot state", "", "/AdvantageKit/RealOutputs/OpMode")
        .build();
```

`kind` is a `FirstMetadata.ViewKind` (`THREE_D`, `TWO_D`, `DATAFRAME`) and `container` a
`FirstMetadata.Container` (`GRID`, `TABS`, `HORIZONTAL`, `VERTICAL`), so neither can be misspelled;
`origin` is the entity path the view is rooted at, and `entities` are the columns a dataframe view
lists (omit them for the timeline alone). Declare
no views and the bridge keeps its default: 3D field, 2D field, a timeline dataframe, and a grid of
the declared cameras.

From that declaration the bridge draws:

| Entity | Contents |
| --- | --- |
| `first/field` | field lines, from the declared geometry |
| `first/field/carpet` | the carpet plane |
| `first/field/tags` | AprilTag plates |
| `first/field/model` | optional glTF/GLB field model (`fieldModel(...)`, or the bridge's `--field-model`) |
| `first/robots/<team>` | `Transform3D` driven by the declared pose topic |
| `first/robots/<team>/model` | static bounding box (`Boxes3D`, default 0.9 m square), or the bridge's `--robot-model` |
| `first/robots/<team>/swerve` | per-module speed and steering vectors (`Arrows3D`) |
| `first/robots/<team>/cameras/<name>` | camera transform and pinhole frustum (`Transform3D`, `Pinhole`) |
| `first/robots/<team>/cameras/<name>/image` | the camera's footage, one `EncodedImage` per frame; a 2D view of the camera is rooted here |
| `first/paths/<name>` | planned path as a line strip |
| `first2d/...` | a top-down mirror of the field, tags, paths and robot footprints |

`FieldGeometry.standard()` is the season-neutral carpet, perimeter and center line; pass your own
`FieldGeometry` with `lines` and `tags` for a season. Nothing about a particular game is baked into
the SDK, and the field's 16.541 m by 8.069 m size is not configurable. The 2D lane keeps WPILib's
field frame (`+x` toward red, `+y` toward blue's left, no flip), so it lines up with any other 2D
WPILib data in the same recording. Rerun 2D views have `+y` pointing down the screen.

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
columns stay native `long[]` topics carrying their two's-complement bits (NetworkTables has no
unsigned type); the bridge reinterprets those bits, so the full unsigned range needs no second
topic. The `layout` marker is `2` for this layout (`1` was `/.count` plus a
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
