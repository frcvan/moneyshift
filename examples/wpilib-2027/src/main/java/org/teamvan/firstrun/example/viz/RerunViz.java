package org.teamvan.firstrun.example.viz;

import java.util.List;
import org.teamvan.firstrun.example.Constants;
import org.teamvan.moneyshift.NtRecorder;
import org.teamvan.moneyshift.frc.FieldGeometry;
import org.teamvan.moneyshift.frc.FirstMetadata;
import org.wpilib.fields.FieldTag;
import org.wpilib.fields.Fields;

/**
 * Tells the FIRSTrun bridge where this robot's data lives.
 *
 * <p>This is the entire robot-side integration: one string at `/Firstrun/layout`. The bridge
 * subscribes to the declared pose and swerve topics, reads the camera's MJPEG stream, and draws
 * the field, the robot, its swerve vectors and its camera frustum in the Rerun recording.
 *
 * <p>The field comes from the same WPILib definition the simulated camera uses, so the bridge's
 * AprilTags and PhotonVision's simulated targets are in one frame.
 */
public final class RerunViz {
  /** Side length of an FRC AprilTag, in meters. */
  private static final double TAG_SIZE_METERS = 0.1651;

  private final FirstMetadata metadata;

  public RerunViz() {
    metadata =
        FirstMetadata.builder()
            .field(field())
            .robot(Constants.TEAM, Constants.POSE_TOPIC)
            .swerve(
                Constants.TEAM,
                Constants.SWERVE_TOPIC,
                new double[] {0.3, 0.3},
                new double[] {0.3, -0.3},
                new double[] {-0.3, 0.3},
                new double[] {-0.3, -0.3})
            .camera(
                Constants.TEAM,
                Constants.CAMERA_NAME,
                0.30,
                0.0,
                0.50,
                0.0,
                -10.0,
                0.0,
                70.0,
                Constants.CAMERA_STREAM)
            .build();
  }

  /** Publishes the declaration; the NetworkTables server keeps it for late bridge connections. */
  public void publish() {
    metadata.publish(NtRecorder.getInstance());
  }

  /** The standard carpet and lines, plus the AprilTags of the demo's WPILib field. */
  private static FieldGeometry field() {
    FieldGeometry standard = FieldGeometry.standard();
    List<FieldGeometry.Tag> tags =
        Fields.FRC_2026_REBUILT_WELDED.loadField().tags.stream()
            .map(RerunViz::tag)
            .toList();
    return new FieldGeometry(standard.carpet(), standard.lines(), tags);
  }

  private static FieldGeometry.Tag tag(FieldTag tag) {
    return new FieldGeometry.Tag(
        tag.getID(),
        tag.getPose().getX(),
        tag.getPose().getY(),
        tag.getPose().getZ(),
        tag.getPose().getRotation().getZ(),
        TAG_SIZE_METERS);
  }
}
