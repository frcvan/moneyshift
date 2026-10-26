package org.teamvan.firstrun.example.drive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.kinematics.ChassisVelocities;

final class SwerveDriveTest {
  private static SwerveDrive loop(SwerveDrive drive, ChassisVelocities velocities, int loops) {
    for (int i = 0; i < loops; i++) {
      drive.driveRobotRelative(velocities);
      drive.periodic();
    }
    return drive;
  }

  @Test
  void drivingForwardMovesThePoseForward() {
    SwerveDrive drive = loop(new SwerveDrive(), new ChassisVelocities(1.0, 0.0, 0.0), 50);

    Pose2d pose = drive.getPose();
    assertTrue(pose.getX() > 0.5, "one second at 1 m/s should cover most of a meter");
    assertEquals(0.0, pose.getY(), 0.05);
    assertEquals(0.0, pose.getRotation().getDegrees(), 1.0);
  }

  @Test
  void spinningInPlaceTurnsTheHeading() {
    SwerveDrive drive = loop(new SwerveDrive(), new ChassisVelocities(0.0, 0.0, 1.0), 25);

    assertTrue(drive.getHeading().getRadians() > 0.2);
  }

  @Test
  void releasingTheSticksStopsTheRotation() {
    SwerveDrive drive = loop(new SwerveDrive(), new ChassisVelocities(2.0, 0.0, 2.0), 50);
    double heading = drive.getHeading().getRadians();

    loop(drive, new ChassisVelocities(0.0, 0.0, 0.0), 100);

    assertEquals(
        heading,
        drive.getHeading().getRadians(),
        1e-9,
        "the demo's gyro follows the command, so no input means no rotation");
  }

  @Test
  void resetPoseRecentersOdometry() {
    SwerveDrive drive = loop(new SwerveDrive(), new ChassisVelocities(1.0, 0.0, 0.0), 50);
    drive.resetPose(new Pose2d(3.0, 2.0, Rotation2d.fromDegrees(90.0)));

    assertEquals(3.0, drive.getPose().getX(), 1e-9);
    assertEquals(2.0, drive.getPose().getY(), 1e-9);
    assertEquals(90.0, drive.getPose().getRotation().getDegrees(), 1e-9);
  }
}
