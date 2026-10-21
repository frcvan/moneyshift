package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.Scalars;
import org.teamvan.moneyshift.archetypes.TextLog;
import org.teamvan.moneyshift.components.Color;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.components.Radius;
import org.teamvan.moneyshift.components.Scalar;
import org.teamvan.moneyshift.components.Text;
import org.teamvan.moneyshift.components.TextLogLevel;
import org.teamvan.moneyshift.encodings.Rgba32;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.ArrowMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.Encoding;

/** End-to-end coverage for the wire format: Arrow chunks and the RRD stream around them. */
class SdkIntegrationTest {

    @TempDir
    Path tempDir;

    @Test
    void logsArchetypesIntoAValidRrdStream() throws Exception {
        Path rrdPath = tempDir.resolve("integration.rrd");

        try (RecordingStream stream = RecordingStreamBuilder.newRecording("moneyshift-test")
                .recordingId("rec_integration")
                .save(rrdPath)
                .build()) {
            stream.setTimeSequence("frame", 7);
            stream.setTimeDurationNanos("runtime", 1_500_000_000L);
            stream.log(
                    "world/points",
                    Points3D.builder()
                            .withPositions(Position3D.of(1.0f, 2.0f, 3.0f), Position3D.of(4.0f, 5.0f, 6.0f))
                            .withColors(new Color(new Rgba32(0xFF0000FFL)))
                            .withRadii(new Radius(new org.teamvan.moneyshift.encodings.Float32(0.25f)))
                            .withLabels(new Text(new org.teamvan.moneyshift.encodings.Utf8("point")))
                            .build());
            stream.log(
                    "logs/text",
                    TextLog.builder()
                            .withText(new Text(new org.teamvan.moneyshift.encodings.Utf8("hello")))
                            .withLevel(new TextLogLevel(new org.teamvan.moneyshift.encodings.Utf8("INFO")))
                            .build());
            stream.logStatic(
                    "world/scalars",
                    Scalars.builder()
                            .withScalars(new Scalar(new org.teamvan.moneyshift.encodings.Float64(42.0)))
                            .build());
        }

        byte[] bytes = Files.readAllBytes(rrdPath);
        assertTrue(bytes.length > 12 + 16, "recording should contain framed messages");
        assertEquals('R', bytes[0]);
        assertEquals('R', bytes[1]);
        assertEquals('F', bytes[2]);
        assertEquals('2', bytes[3]);
        assertEquals(RerunVersion.MAJOR, bytes[4]);
        assertEquals(RerunVersion.MINOR, bytes[5]);
        assertEquals(1, bytes[8]); // LZ4
        assertEquals(2, bytes[9]); // protobuf

        ParsedRrd parsed = parse(bytes);
        assertEquals(1, parsed.storeInfos, "exactly one SetStoreInfo");
        assertEquals(3, parsed.arrowMessages.size(), "two dynamic chunks and one static chunk");

        boolean sawStatic = false;
        int totalRows = 0;
        for (ArrowMsg arrow : parsed.arrowMessages) {
            assertEquals(Encoding.ENCODING_ARROW_IPC.getNumber(), arrow.getEncodingValue());
            assertTrue(arrow.hasChunkId());
            byte[] ipc = Lz4.decompress(
                    arrow.getPayload().toByteArray(), (int) arrow.getUncompressedSize());
            assertEquals(arrow.getUncompressedSize(), ipc.length);
            ChunkView chunk = readChunk(ipc);
            totalRows += chunk.rows;
            if (arrow.getIsStatic()) {
                sawStatic = true;
                assertEquals("/world/scalars", chunk.entityPath);
                assertNotNull(chunk.schemaMetadata.get("sorbet:version"));
                assertEquals("0.1.3", chunk.schemaMetadata.get("sorbet:version"));
                assertTrue(chunk.fieldNames.stream().anyMatch(n -> n.equals("Scalars:scalars")));
            }
        }
        assertTrue(sawStatic, "static log must produce an is_static ArrowMsg");
        assertEquals(3, totalRows);
    }

    /**
     * Writes a stable fixture under {@code build/} so that the Rust-side cross-check can decode
     * exactly the bytes produced by the Java SDK.
     */
    @Test
    void writesPortableRrdFixture() throws Exception {
        Path fixture = Path.of("build", "moneyshift-verify.rrd").toAbsolutePath();
        Files.createDirectories(fixture.getParent());
        try (RecordingStream stream = RecordingStreamBuilder.newRecording("moneyshift-verify")
                .recordingId("rec_verify")
                .save(fixture)
                .build()) {
            stream.setTimeSequence("frame", 3);
            stream.log(
                    "world/points",
                    Points3D.builder()
                            .withPositions(Position3D.of(1, 2, 3), Position3D.of(4, 5, 6))
                            .withColors(new Color(new Rgba32(0xFF0000FFL)))
                            .withLabels(new Text(new org.teamvan.moneyshift.encodings.Utf8("fixture")))
                            .build());
            stream.logStatic(
                    "world/scalars",
                    Scalars.builder()
                            .withScalars(new Scalar(new org.teamvan.moneyshift.encodings.Float64(3.5)))
                            .build());
        }
        assertTrue(Files.size(fixture) > 0);
    }

    @Test
    void arrowChunkCarriesRerunSchemaMetadata() throws Exception {
        Path rrdPath = tempDir.resolve("metadata.rrd");
        try (RecordingStream stream = RecordingStreamBuilder.newRecording("metadata-test")
                .recordingId("rec_metadata")
                .save(rrdPath)
                .build()) {
            stream.setTimeSequence("frame", 1);
            stream.log(
                    "world/points",
                    Points3D.builder()
                            .withPositions(Position3D.of(1, 2, 3))
                            .withPointShading(org.teamvan.moneyshift.components.PointShading.Gradient)
                            .build());
        }

        ParsedRrd parsed = parse(Files.readAllBytes(rrdPath));
        ArrowMsg arrow = parsed.arrowMessages.get(0);
        byte[] ipc = Lz4.decompress(arrow.getPayload().toByteArray(), (int) arrow.getUncompressedSize());
        ChunkView chunk = readChunk(ipc);

        assertEquals("/world/points", chunk.entityPath);
        assertEquals(1, chunk.rows);
        assertTrue(chunk.fieldNames.contains("rerun.controls.RowId"));
        assertTrue(chunk.fieldNames.contains("frame"));
        assertTrue(chunk.fieldNames.contains("Points3D:positions"));
        assertTrue(chunk.fieldNames.contains("Points3D:point_shading"));
        assertTrue(chunk.fieldMetadata.get("Points3D:positions").containsKey("rerun:component"));
        assertEquals(
                "Points3D:positions",
                chunk.fieldMetadata.get("Points3D:positions").get("rerun:component"));
        assertEquals(
                "rerun.components.Position3D",
                chunk.fieldMetadata.get("Points3D:positions").get("rerun:component_type"));
    }

    private static ChunkView readChunk(byte[] ipc) throws Exception {
        try (BufferAllocator allocator = new RootAllocator();
                ArrowStreamReader reader =
                        new ArrowStreamReader(new ByteArrayInputStream(ipc), allocator)) {
            assertTrue(reader.loadNextBatch());
            VectorSchemaRoot root = reader.getVectorSchemaRoot();
            ChunkView view = new ChunkView();
            view.rows = root.getRowCount();
            view.entityPath = root.getSchema().getCustomMetadata().get("rerun:entity_path");
            view.schemaMetadata.putAll(root.getSchema().getCustomMetadata());
            for (FieldVector vector : root.getFieldVectors()) {
                view.fieldNames.add(vector.getName());
                view.fieldMetadata.put(vector.getName(), vector.getField().getMetadata());
            }
            return view;
        }
    }

    private static ParsedRrd parse(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(12);
        ParsedRrd parsed = new ParsedRrd();
        while (buffer.remaining() >= 16) {
            long kind = buffer.getLong();
            long length = buffer.getLong();
            if (kind == 0) {
                assertEquals(0, length, "test writer emits an empty RrdFooter");
                break;
            }
            assertTrue(length >= 0 && length <= buffer.remaining());
            byte[] payload = new byte[(int) length];
            buffer.get(payload);
            try {
                if (kind == 1) {
                    org.teamvan.moneyshift.proto.log_msg.v1alpha1.SetStoreInfo.parseFrom(payload);
                    parsed.storeInfos++;
                } else if (kind == 2) {
                    parsed.arrowMessages.add(ArrowMsg.parseFrom(payload));
                } else if (kind == 3) {
                    org.teamvan.moneyshift.proto.log_msg.v1alpha1.BlueprintActivationCommand.parseFrom(
                            payload);
                } else {
                    throw new AssertionError("unknown frame kind " + kind);
                }
            } catch (Exception e) {
                throw new AssertionError("could not parse a framed message kind " + kind, e);
            }
        }
        return parsed;
    }

    private static final class ParsedRrd {
        int storeInfos;
        final List<ArrowMsg> arrowMessages = new ArrayList<>();
    }

    private static final class ChunkView {
        int rows;
        String entityPath;
        final java.util.Map<String, String> schemaMetadata = new java.util.HashMap<>();
        final List<String> fieldNames = new ArrayList<>();
        final java.util.Map<String, java.util.Map<String, String>> fieldMetadata =
                new java.util.HashMap<>();
    }
}
