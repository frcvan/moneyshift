package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableEvent;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.networktables.NetworkTableListener;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.TextLog;
import org.teamvan.moneyshift.components.Position3D;

/** The per-entity metadata cache must keep the steady-state log path free of topic writes. */
class MetadataCacheTest {
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
    void componentsMetadataIsWrittenOncePerComponentSet() {
        AtomicInteger writes = new AtomicInteger();
        try (NetworkTableListener listener =
                NetworkTableListener.createListener(
                        instance,
                        new String[] {"/Rerun/cache/.rerun/components"},
                        EnumSet.of(NetworkTableEvent.Kind.VALUE_LOCAL),
                        event -> writes.incrementAndGet())) {
            Points3D points =
                    Points3D.builder().withPositions(Position3D.of(1, 2, 3)).build();
            recorder.log("cache", points);
            listener.waitForQueue(1.0);
            assertEquals(1, writes.get(), "the first log publishes the component set");

            recorder.log("cache", points);
            listener.waitForQueue(1.0);
            assertEquals(1, writes.get(), "an unchanged component set is not republished");

            recorder.log("cache", Points3D.builder().withPositions(Position3D.of(4, 5, 6)).build());
            listener.waitForQueue(1.0);
            assertEquals(1, writes.get(), "positions-only logs never change the set");

            recorder.log(
                    "cache",
                    Points3D.builder()
                            .withPositions(Position3D.of(7, 8, 9))
                            .withColors(0xFF00E5FFL)
                            .build());
            listener.waitForQueue(1.0);
            assertEquals(2, writes.get(), "adding colors changes the component set");

            recorder.log("cache", TextLog.builder().withText("hello").build());
            listener.waitForQueue(1.0);
            assertEquals(3, writes.get(), "a different archetype changes the component set");
        }
    }

    @Test
    void layoutIsWrittenOnce() {
        AtomicInteger writes = new AtomicInteger();
        try (NetworkTableListener listener =
                NetworkTableListener.createListener(
                        instance,
                        new String[] {"/Rerun/cache-layout/.rerun/layout"},
                        EnumSet.of(NetworkTableEvent.Kind.VALUE_LOCAL),
                        event -> writes.incrementAndGet())) {
            for (int i = 0; i < 5; i++) {
                recorder.log(
                        "cache-layout",
                        Points3D.builder().withPositions(Position3D.of(i, 0, 0)).build());
            }
            listener.waitForQueue(1.0);
            assertEquals(1, writes.get());
        }
    }
}
