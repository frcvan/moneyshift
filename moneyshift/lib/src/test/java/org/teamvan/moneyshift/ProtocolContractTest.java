package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableEntry;
import org.wpilib.networktables.NetworkTableInstance;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.components.Position3D;

/**
 * Checks the pieces of the {@code /Rerun} protocol that are easy to get subtly wrong: entity
 * escaping, root-entity rejection, and the per-entity topic caches.
 */
class ProtocolContractTest {
    private NetworkTableInstance instance;
    private NtRecorder recorder;

    @BeforeEach
    void setUp() {
        instance = NetworkTableInstance.create();
        NtRecorder.setInstance(instance);
        recorder = NtRecorder.getInstance();
    }

    @AfterEach
    void tearDown() {
        NtRecorder.setInstance(null);
        instance.close();
    }

    @Test
    void escapesEntitySegmentsExactlyOnce() {
        recorder.log("world/my robot.v2/héllo", Points3D.of(List.of(Position3D.of(1f, 2f, 3f))));

        // Metadata, metadata markers and columns must all live under the same topic prefix.
        String prefix = "/Rerun/world/my%20robot%2Ev2/h%C3%A9llo";
        assertEquals("rerun.archetypes.Points3D", string(prefix + "/.rerun/archetype"));
        assertEquals(1L, integer(prefix + "/.rerun/layout"));
        assertEquals(1.0, doubleArray(prefix + "/positions/0")[0], 0.0);
        // Nothing is published at a double-escaped path.
        assertEquals(0, instance.getTopics("/Rerun/world/my%25").length);
    }

    @Test
    void rejectsTheRootEntity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> recorder.log("", Points3D.of(List.of(Position3D.of(0f, 0f, 0f)))));
        assertThrows(
                IllegalArgumentException.class,
                () -> recorder.log("/", Points3D.of(List.of(Position3D.of(0f, 0f, 0f)))));
        assertThrows(IllegalArgumentException.class, () -> NtPath.encodeEntityPath(""));
    }

    @Test
    void reusesColumnEntriesAndWriters() {
        recorder.log("world/points", Points3D.of(List.of(Position3D.of(1f, 2f, 3f))));
        NtRecorder.EntityState state = recorder.state("/world/points");
        NetworkTableEntry first = recorder.columnEntry(state, "positions/0");
        assertSame(first, recorder.columnEntry(state, "positions/0"));
        assertSame(state.writer(), state.writer());
    }

    private long integer(String topic) {
        return instance.getEntry(topic).getInteger(0L);
    }

    private String string(String topic) {
        return instance.getEntry(topic).getString("");
    }

    private double[] doubleArray(String topic) {
        return instance.getEntry(topic).getDoubleArray(new double[0]);
    }
}
