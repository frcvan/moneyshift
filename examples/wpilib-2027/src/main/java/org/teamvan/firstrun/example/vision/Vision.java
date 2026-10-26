package org.teamvan.firstrun.example.vision;

import org.littletonrobotics.junction.Logger;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.teamvan.firstrun.example.Constants;
import org.teamvan.firstrun.example.drive.SwerveDrive;
import org.wpilib.fields.Field;
import org.wpilib.fields.Fields;
import org.wpilib.math.geometry.Rotation3d;
import org.wpilib.math.geometry.Transform3d;
import org.wpilib.math.geometry.Translation3d;

/**
 * One PhotonVision camera, with the simulation hooks that make it see AprilTags.
 *
 * <p>The camera is a name and an MJPEG stream: PhotonVision publishes its results over
 * NetworkTables and serves the picture at `http://<coprocessor>:1181/stream.mjpg`, which is the
 * URL the FIRSTrun layout declares. In simulation there is no coprocessor, so {@link
 * VisionSystemSim} renders the tags from the WPILib field definition instead.
 */
public final class Vision {
  /** Where the camera sits on the robot: front, centered, half a meter up, ten degrees down. */
  private static final Transform3d ROBOT_TO_CAMERA =
      new Transform3d(
          new Translation3d(0.30, 0.0, 0.50),
          new Rotation3d(0.0, Math.toRadians(-10.0), 0.0));

  /** The WPILib field definition whose AprilTags the simulated camera can see. */
  private static final Fields FIELD = Fields.FRC_2026_REBUILT_WELDED;

  private final PhotonCamera camera = new PhotonCamera(Constants.CAMERA_NAME);
  private final PhotonPoseEstimator poseEstimator;
  private final SwerveDrive drive;

  /** The simulated camera rig, or {@code null} outside simulation. */
  private final VisionSystemSim sim;

  public Vision(SwerveDrive drive) {
    this.drive = drive;
    Field field = FIELD.loadField();
    poseEstimator = new PhotonPoseEstimator(field, ROBOT_TO_CAMERA);

    if (Constants.isSimulation) {
      sim = new VisionSystemSim("demo");
      sim.addAprilTags(field);
      sim.addCamera(
          new PhotonCameraSim(
              camera,
              new SimCameraProperties()
                  .setCalibration(
                      1280, 800, org.wpilib.math.geometry.Rotation2d.fromDegrees(70.0))),
          ROBOT_TO_CAMERA);
    } else {
      sim = null;
    }
  }

  /** Adds every unread camera measurement to the drive pose estimator, then renders the sim. */
  public void periodic() {
    for (PhotonPipelineResult result : camera.getAllUnreadResults()) {
      if (!result.hasTargets()) {
        continue;
      }
      poseEstimator
          .estimateCoprocMultiTagPose(result)
          .or(() -> poseEstimator.estimateLowestAmbiguityPose(result))
          .ifPresent(this::addMeasurement);
    }

    if (sim != null) {
      sim.update(drive.getPose());
    }
  }

  private void addMeasurement(EstimatedRobotPose estimate) {
    drive.addVisionMeasurement(estimate.estimatedPose.toPose2d(), estimate.timestampSeconds);
    Logger.recordOutput("Vision/Pose2d", estimate.estimatedPose.toPose2d());
    Logger.recordOutput("Vision/TagCount", (long) estimate.targetsUsed.size());
  }
}
