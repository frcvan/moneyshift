package org.teamvan.moneyshift.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.teamvan.moneyshift.NtRecorder;
import org.wpilib.networktables.NetworkTableInstance;

class MoneyshiftDemoTest {

    @AfterEach
    void resetRecorder() {
        NtRecorder.setInstance(null);
    }

    @Test
    void publishesACompleteSensorSweep() {
        NetworkTableInstance instance = NetworkTableInstance.create();
        try {
            NtRecorder.setInstance(instance);
            MoneyshiftDemo.publish(NtRecorder.getInstance(), 8);
            NtRecorder.getInstance().flush();

            assertTopic(instance, "/Rerun/world/robot/sensor/returns/.rerun/archetype",
                    "rerun.archetypes.Points3D");
            assertTopic(instance, "/Rerun/world/robot/sensor/returns/positions/0");
            assertTopic(instance, "/Rerun/world/robot/sensor/returns/colors");
            assertTopic(instance, "/Rerun/world/robot/sensor/scan/.rerun/archetype",
                    "rerun.archetypes.LineStrips3D");
            assertTopic(instance, "/Rerun/world/robot/.rerun/archetype",
                    "rerun.archetypes.Transform3D");
            assertTopic(instance, "/Rerun/plots/amplitude/.rerun/archetype",
                    "rerun.archetypes.Scalars");
            assertTopic(instance, "/Rerun/logs/simulation/text");
            assertTopic(instance, "/Demo/Drive/Speed");

            assertTrue(
                    instance.getEntry("/Rerun/world/annotations/.rerun/static").getBoolean(false),
                    "static entities must be marked");
            assertEquals(
                    96,
                    instance.getEntry("/Rerun/world/robot/sensor/returns/positions/0")
                            .getDoubleArray(new double[0])
                            .length,
                    "one position per sensor return");
            assertEquals(
                    96,
                    instance.getEntry("/Rerun/world/robot/sensor/returns/colors")
                            .getIntegerArray(new long[0])
                            .length,
                    "one color per sensor return");
        } finally {
            instance.close();
        }
    }

    private static void assertTopic(NetworkTableInstance instance, String topic) {
        assertTopic(instance, topic, null);
    }

    private static void assertTopic(NetworkTableInstance instance, String topic, String value) {
        assertTrue(
                instance.getEntry(topic).exists(),
                () -> "missing topic " + topic);
        if (value != null) {
            assertEquals(value, instance.getEntry(topic).getString(null), topic);
        }
    }
}
