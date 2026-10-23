package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableInstance;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.components.Color;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.components.Scalar;

/** End-to-end checks of the {@code /Rerun} protocol as seen from NetworkTables. */
class NtRecorderTest {
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
    void publishesArchetypeMetadataAndColumns() {
        recorder.log(
                "world/points",
                Points3D.builder()
                        .withPositions(Position3D.of(1, 2, 3), Position3D.of(4, 5, 6))
                        .withColors(0xFF00E5FFL)
                        .build());

        assertEquals(1L, integer("/Rerun/world/points/.rerun/layout"));
        assertEquals(
                "rerun.archetypes.Points3D", string("/Rerun/world/points/.rerun/archetype"));
        assertEquals(
                "positions=rerun.components.Position3D,colors=rerun.components.Color",
                string("/Rerun/world/points/.rerun/components"));

        assertArrayEquals(new double[] {1, 4}, doubles("/Rerun/world/points/positions/0"));
        assertArrayEquals(new double[] {2, 5}, doubles("/Rerun/world/points/positions/1"));
        assertArrayEquals(new double[] {3, 6}, doubles("/Rerun/world/points/positions/2"));
        assertArrayEquals(
                new long[] {0xFF00E5FFL}, integers("/Rerun/world/points/colors"));

        // Absent optional components are neither announced nor written.
        assertEquals(0, topicsUnder("/Rerun/world/points/radii").length);
        // No instance is null, so the nullable root never publishes a presence mask.
        assertEquals(0, topicsUnder("/Rerun/world/points/.present").length);
        // It is a dynamic entity.
        assertEquals(0, topicsUnder("/Rerun/world/points/.rerun/static").length);
    }

    @Test
    void staticEntitiesPublishTheStaticFlag() {
        recorder.logStatic(
                "world/map",
                Points3D.builder().withPositions(Position3D.of(0, 0, 0)).build());

        assertTrue(bool("/Rerun/world/map/.rerun/static"));
        assertThrows(
                IllegalStateException.class,
                () ->
                        recorder.log(
                                "world/map",
                                Points3D.builder().withPositions(Position3D.of(0, 0, 0)).build()));
    }

    @Test
    void dynamicEntitiesRejectStaticLogs() {
        recorder.log("world/live", Points3D.builder().withPositions(Position3D.of(0, 0, 0)).build());
        assertThrows(
                IllegalStateException.class,
                () ->
                        recorder.logStatic(
                                "world/live",
                                Points3D.builder().withPositions(Position3D.of(0, 0, 0)).build()));
    }

    @Test
    void rawComponentsHaveNoArchetype() {
        recorder.logComponent("debug/origin", "value", List.of(new Scalar(2.5)));

        assertEquals("", string("/Rerun/debug/origin/.rerun/archetype"));
        assertEquals(
                "value=rerun.components.Scalar",
                string("/Rerun/debug/origin/.rerun/components"));
        assertArrayEquals(new double[] {2.5}, doubles("/Rerun/debug/origin/value"));
    }

    @Test
    void severalRawComponentsAccumulate() {
        recorder.logComponent("debug/robot", "value", List.of(new Scalar(2.5)));
        recorder.logComponent("debug/robot", "color", List.of(new Color(0xFF00FF00L)));

        assertEquals(
                "value=rerun.components.Scalar,color=rerun.components.Color",
                string("/Rerun/debug/robot/.rerun/components"));
    }

    @Test
    void escapesEntitySegments() {
        recorder.logComponent(
                "world/my robot.v2/h\u00e9llo=%x", "value", List.of(new Scalar(1)));

        assertTrue(
                instance.getEntry("/Rerun/world/my%20robot%2Ev2/h%C3%A9llo%3D%25x/value").exists());
    }

    @Test
    void nullComponentsPublishPresence() {
        recorder.logComponent(
                "debug/null", "value", java.util.Arrays.asList(new Scalar(1.5), null));

        assertArrayEquals(
                new boolean[] {true, false}, booleans("/Rerun/debug/null/value/.present"));
        assertArrayEquals(new double[] {1.5, 0.0}, doubles("/Rerun/debug/null/value"));
    }

    private double[] doubles(String topic) {
        return instance.getEntry(topic).getDoubleArray(new double[0]);
    }

    private long[] integers(String topic) {
        return instance.getEntry(topic).getIntegerArray(new long[0]);
    }

    private boolean[] booleans(String topic) {
        return instance.getEntry(topic).getBooleanArray(new boolean[0]);
    }

    private boolean bool(String topic) {
        return instance.getEntry(topic).getBoolean(false);
    }

    private long integer(String topic) {
        return instance.getEntry(topic).getInteger(0);
    }

    private String string(String topic) {
        return instance.getEntry(topic).getString("");
    }

    private org.wpilib.networktables.TopicInfo[] topicsUnder(String prefix) {
        return instance.getTopicInfo(prefix);
    }
}
