# moneyshift

A Java SDK for [Rerun](https://rerun.io) 0.38.1. It speaks the same wire format as the Rust,
Python, and C++ SDKs: native Arrow IPC chunks, LZ4-compressed `ArrowMsg`s, RRD framing, and the
`rerun.sdk_comms.v1alpha1.MessageProxyService` gRPC transport.

Everything under `lib/src/main/java/org/teamvan/moneyshift/{archetypes,components,encodings,
blueprint,testing}` is generated from Rerun's own type definitions, so the SDK covers every
archetype, component, datatype, blueprint view, and testing type in that release.

## Usage

```java
import org.teamvan.moneyshift.RecordingStream;
import org.teamvan.moneyshift.RecordingStreamBuilder;
import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.components.Position3D;

try (RecordingStream rec = RecordingStreamBuilder.newRecording("my-app")
        .connectGrpc("rerun+http://127.0.0.1:9876/proxy")
        .build()) {
    rec.setTimeSequence("frame", 0);
    rec.log("world/points", Points3D.builder()
            .withPositions(Position3D.of(1, 2, 3), Position3D.of(4, 5, 6))
            .build());
}
```

Sinks are selected with `connectGrpc(...)`, `save(path)`, `memory()`, or `transport(...)`.
`toRrdBytes()` returns a complete `.rrd` stream for a memory sink.

Static data uses `logStatic`. Timelines can be set with `setTimeSequence`, `setTimeDurationNanos`,
`setTimeTimestampNanosSinceEpoch`, or the `Timeline` overloads.

### Blueprints

```java
RecordingStream blueprint = RecordingStreamBuilder.newBlueprint("my-app")
        .save(Path.of("blueprint.rrd"))
        .build();

blueprint.log("view/0", ViewBlueprint.builder()
        .withClassIdentifier(new ViewClass(new Utf8("3D")))
        .build());

recording.activateBlueprint(blueprint, true, false);
```

## Generating the type surface

```sh
cargo xtask java moneyshift/lib/src/main/java
```

The command reads `re_type_definitions` (the Rerun git tag pinned in `xtask/Cargo.toml`), removes
the previously generated top-level packages, and rewrites them. Hand-written core classes and the
protobuf bindings are never touched.

## Architecture

```
Archetype.serialize()
    -> ComponentBatch (one Arrow ListVector per component)
    -> ChunkEncoder (RowId + index columns + sorted component columns -> Arrow IPC)
    -> LZ4 -> ArrowMsg
    -> Transport (gRPC, RRD file, memory, custom)
```

Values are written directly into Arrow vectors by generated code; there is no reflective value
model or JSON step. A recording stream owns one `RootAllocator`, serializes under a lock, and hands
serialized messages to a transport. The gRPC transport uses a bounded queue and a dedicated writer
thread so logging never blocks on network I/O.

## Verification

```sh
cd moneyshift && ./gradlew :lib:test :lib:build
cargo run --example decode_rrd -- moneyshift/lib/build/moneyshift-verify.rrd
```

The tests cover:

- Arrow IPC round-tripping and Rerun schema metadata.
- Every generated archetype, component, and encoding being constructed and serialized.
- RRD framing and footer decoding.
- A real client-streaming gRPC proxy server.
- Blueprint store kinds and activation messages.

`examples/decode_rrd.rs` decodes the Java-produced RRD with Rerun's own `re_log_encoding` decoder,
which is the same path the viewer uses.
