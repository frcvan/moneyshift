package org.teamvan.moneyshift.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.teamvan.moneyshift.proto.common.v1alpha1.StoreKind;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.ArrowMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.BlueprintActivationCommand;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.SetStoreInfo;

class MoneyshiftDemoTest {

    @TempDir Path tempDir;

    @Test
    void writesDecodableRecordingsForEverySink() throws Exception {
        MoneyshiftDemo.Artifacts artifacts = MoneyshiftDemo.writeAll(tempDir, 8);

        Rrd demo = parse(artifacts.demoRecording());
        assertEquals(
                Set.of(StoreKind.STORE_KIND_BLUEPRINT, StoreKind.STORE_KIND_RECORDING),
                new HashSet<>(demo.storeKinds),
                "the shared file must contain a blueprint store and a recording store");
        assertTrue(demo.chunks.size() > 30, "expected chunks for the blueprint, static data and frames");
        assertEquals(1, demo.activations.size(), "the recording must activate its blueprint");
        assertTrue(demo.activations.get(0).getMakeActive());
        assertEquals(
                "bp_sensor_sweep",
                demo.activations.get(0).getBlueprintId().getRecordingId());

        Rrd fileSink = parse(artifacts.fileSinkRecording());
        assertEquals(List.of(StoreKind.STORE_KIND_RECORDING), fileSink.storeKinds);
        assertTrue(fileSink.chunks.size() > 20, "expected a chunk per archetype per frame");
        assertTrue(fileSink.activations.isEmpty());

        Rrd memorySink = parse(artifacts.memoryRecording());
        assertEquals(List.of(StoreKind.STORE_KIND_RECORDING), memorySink.storeKinds);
        assertTrue(memorySink.chunks.size() > 10);
        assertTrue(artifacts.memoryBytes() > 0);
        assertEquals(Files.size(artifacts.memoryRecording()), artifacts.memoryBytes());
    }

    private static Rrd parse(Path path) throws Exception {
        byte[] bytes = Files.readAllBytes(path);
        assertTrue(bytes.length > 12, "RRD file must be non-empty: " + path);
        assertEquals('R', bytes[0]);
        assertEquals('R', bytes[1]);
        assertEquals('F', bytes[2]);
        assertEquals('2', bytes[3]);

        Rrd rrd = new Rrd();
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(12);
        while (buffer.remaining() >= 16) {
            long kind = buffer.getLong();
            long length = buffer.getLong();
            if (kind == 0) {
                break;
            }
            assertTrue(length >= 0 && length <= buffer.remaining(), "frame length out of bounds");
            byte[] payload = new byte[(int) length];
            buffer.get(payload);
            switch ((int) kind) {
                case 1 -> rrd.storeKinds.add(SetStoreInfo.parseFrom(payload)
                        .getInfo()
                        .getStoreId()
                        .getKind());
                case 2 -> rrd.chunks.add(ArrowMsg.parseFrom(payload));
                case 3 -> rrd.activations.add(BlueprintActivationCommand.parseFrom(payload));
                default -> throw new AssertionError("unknown RRD frame kind " + kind);
            }
        }
        return rrd;
    }

    private static final class Rrd {
        final List<StoreKind> storeKinds = new ArrayList<>();
        final List<ArrowMsg> chunks = new ArrayList<>();
        final List<BlueprintActivationCommand> activations = new ArrayList<>();
    }
}
