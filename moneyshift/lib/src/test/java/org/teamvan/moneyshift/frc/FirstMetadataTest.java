package org.teamvan.moneyshift.frc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableInstance;

import org.teamvan.moneyshift.NtRecorder;

/** The declaration is a plain NT string topic; the bridge parses it verbatim. */
class FirstMetadataTest {
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
    void publishesTheStandardField() {
        FirstMetadata.builder().build().publish(recorder);

        assertEquals(
                "{\"version\":1,\"field\":{\"carpet\":true,\"lines\":["
                        + "{\"points\":[[0.0,0.0],[16.54175,0.0],[16.54175,8.069326],"
                        + "[0.0,8.069326]],\"closed\":true},"
                        + "{\"points\":[[8.270875,0.0],[8.270875,8.069326]],\"closed\":false}],"
                        + "\"tags\":[]},\"robots\":[]}",
                instance.getEntry("/Firstrun/layout").getString(""));
    }

    @Test
    void declaresRobotsCamerasSwerveStatesAndPaths() {
        FirstMetadata metadata =
                FirstMetadata.builder()
                        .field(
                                new FieldGeometry(
                                        false,
                                        List.of(
                                                FieldGeometry.Line.closed(
                                                        new FieldGeometry.Point(0, 0),
                                                        new FieldGeometry.Point(10, 0),
                                                        new FieldGeometry.Point(10, 5))),
                                        List.of(
                                                new FieldGeometry.Tag(3, 1, 2, 1.5, 0.25, 0.1651))))
                        .fieldModel("fields/2027.glb")
                        .robot(254, "/AdvantageKit/RealOutputs/Drive/Pose2d")
                        .robot(1678, "/SmartDashboard/Field/Robot", 0.4, 0.5, 0.6)
                        .swerve(254, "/AdvantageKit/RealOutputs/SwerveStates/Measured")
                        .swerve(
                                1678,
                                "/SmartDashboard/Swerve/States",
                                new double[] {0.3, 0.25},
                                new double[] {0.3, -0.25},
                                new double[] {-0.3, 0.25},
                                new double[] {-0.3, -0.25})
                        .camera(
                                254,
                                "front",
                                0.3,
                                0.0,
                                0.5,
                                0.0,
                                -10.0,
                                0.0,
                                70.0,
                                "http://10.6.32.8:1181/stream.mjpg")
                        .camera(254, "rear", -0.3, 0.0, 0.5, 0.0, 10.0, 180.0, 70.0)
                        .path("auto", "/AdvantageKit/RealOutputs/PathPlanner/ActivePath")
                        .build();

        metadata.publish(recorder);
        String json = instance.getEntry("/Firstrun/layout").getString("");

        assertEquals(metadata.json(), json);
        assertTrue(json.contains("\"carpet\":false"));
        assertFalse(json.contains("length_meters"));
        assertTrue(
                json.contains(
                        "{\"id\":3,\"x\":1.0,\"y\":2.0,\"z\":1.5,\"yaw\":0.25,\"size\":0.1651}"));
        assertTrue(
                json.contains(
                        "{\"team\":254,\"pose_topic\":\"/AdvantageKit/RealOutputs/Drive/Pose2d\","
                                + "\"half_extents\":[0.45,0.45,0.3],"
                                + "\"swerve\":{\"states_topic\":"
                                + "\"/AdvantageKit/RealOutputs/SwerveStates/Measured\"},"
                                + "\"cameras\":["
                                + "{\"name\":\"front\",\"x\":0.3,\"y\":0.0,\"z\":0.5,"
                                + "\"roll\":0.0,\"pitch\":-10.0,\"yaw\":0.0,\"fov\":70.0,"
                                + "\"stream\":\"http://10.6.32.8:1181/stream.mjpg\"},"));
        assertTrue(
                json.contains(
                        "\"module_offsets\":[[0.3,0.25],[0.3,-0.25],[-0.3,0.25],[-0.3,-0.25]]"));
        assertTrue(
                json.contains(
                        ",\"paths\":[{\"name\":\"auto\",\"topic\":"
                                + "\"/AdvantageKit/RealOutputs/PathPlanner/ActivePath\"}]"));
        assertFalse(json.contains("match_"));
    }

    @Test
    void rejectsDeclarationsTheBridgeCannotHonour() {
        assertThrows(IllegalArgumentException.class, () -> FirstMetadata.builder().robot(0, "/x"));
        assertThrows(IllegalArgumentException.class, () -> FirstMetadata.builder().robot(1, "x"));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().robot(1, "/x", 0.5, 0.5));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().robot(1, "/x", 0.5, -0.5, 0.5));
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    FirstMetadata.builder().robot(1, "/x").robot(1, "/y");
                });
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().swerve(254, "Swerve/States"));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().swerve(1, "/states", new double[] {0, 0}));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().camera(1, "a/b", 0, 0, 0, 0, 0, 0, 60));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().camera(1, "front", 0, 0, 0, 0, 0, 0, 180));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        FirstMetadata.builder()
                                .robot(1, "/x")
                                .camera(1, "front", 0, 0, 0, 0, 0, 0, 60, "Camera/front"));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        FirstMetadata.builder()
                                .camera(1, "front", 0, 0, 0, 0, 0, 0, 60, "https://10.6.32.8:1181/x"));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        FirstMetadata.builder()
                                .camera(1, "front", 0, 0, 0, 0, 0, 0, 60, "http:///stream.mjpg"));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        FirstMetadata.builder()
                                .camera(1, "front", 0, 0, 0, 0, 0, 0, 60, "http://a b/stream.mjpg"));
        assertThrows(
                IllegalArgumentException.class,
                () -> FirstMetadata.builder().path("auto", "Auto/Path"));
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    FirstMetadata.builder()
                            .robot(254, "/pose")
                            .swerve(254, "/states")
                            .swerve(254, "/other");
                });
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    FirstMetadata.builder()
                            .robot(254, "/pose")
                            .camera(254, "front", 0, 0, 0, 0, 0, 0, 60)
                            .camera(254, "front", 0, 0, 0, 0, 0, 0, 60);
                });
        // Swerve or camera data for a team that was never declared fails at build().
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    FirstMetadata.builder().robot(254, "/pose").swerve(1678, "/states").build();
                });
        assertThrows(
                IllegalArgumentException.class,
                () -> new FieldGeometry.Line(List.of(new FieldGeometry.Point(0, 0)), false));
        assertThrows(
                IllegalArgumentException.class, () -> new FieldGeometry.Tag(1, 0, 0, 0, 0, 0));
    }
}
