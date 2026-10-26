package org.teamvan.firstrun.example.viz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.teamvan.moneyshift.frc.FirstMetadata;
import org.wpilib.networktables.NetworkTableInstance;

final class RerunVizTest {
  @Test
  void publishesTheFieldCameraAndRobotTopics() {
    new RerunViz().publish();

    String json =
        NetworkTableInstance.getDefault()
            .getEntry(FirstMetadata.TOPIC)
            .getString("");

    assertTrue(json.contains("\"carpet\":true"), "the standard carpet is declared");
    assertTrue(
        json.contains("\"stream\":\"http://127.0.0.1:1181/stream.mjpg\""),
        "the camera is an MJPEG URL, not a topic");
    assertTrue(json.contains("\"/Demo/Drive/Pose2d\""), "the pose topic is declared");
    assertTrue(json.contains("\"/Demo/Drive/SwerveModuleVelocities\""), "the swerve topic is declared");
    assertEquals(32, count(json, "{\"id\":"), "the 2026 field's AprilTags are declared");
  }

  private static int count(String haystack, String needle) {
    int count = 0;
    for (int index = haystack.indexOf(needle); index >= 0; index = haystack.indexOf(needle, index + 1)) {
      count++;
    }
    return count;
  }
}
