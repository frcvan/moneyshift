package org.teamvan.firstrun.example;

import org.teamvan.firstrun.example.drive.SwerveDrive;
import org.teamvan.firstrun.example.vision.Vision;
import org.teamvan.firstrun.example.viz.RerunViz;
import org.wpilib.command2.Command;
import org.wpilib.command2.button.CommandGamepad;
import org.wpilib.math.geometry.Pose2d;

/** Everything the robot owns, wired together. */
public final class RobotContainer {
  private final CommandGamepad driver = new CommandGamepad(0);
  private final SwerveDrive drive = new SwerveDrive();
  private final Vision vision = new Vision(drive);
  private final RerunViz viz = new RerunViz();

  public RobotContainer() {
    // Field-relative joystick drive whenever no other command needs the drivetrain.
    drive.setDefaultCommand(drive.joystickDrive(driver));

    // Start recenters odometry, which is also handy when comparing a recording to the sim.
    driver.start().onTrue(drive.runOnce(() -> drive.resetPose(new Pose2d())));

    viz.publish();
  }

  /** Reads the camera, which also advances the simulated vision system. */
  public void periodic() {
    vision.periodic();
  }

  /** A drive forward for a second and a half, so an autonomous recording has motion. */
  public Command autonomousCommand() {
    return drive
        .run(() -> drive.driveFieldRelative(1.5, 0.0, 0.0))
        .withTimeout(1.5)
        .withName("Drive forward");
  }

  public SwerveDrive drive() {
    return drive;
  }
}
