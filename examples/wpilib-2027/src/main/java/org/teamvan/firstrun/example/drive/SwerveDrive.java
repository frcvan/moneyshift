package org.teamvan.firstrun.example.drive;

import org.littletonrobotics.junction.Logger;
import org.teamvan.firstrun.example.Constants;
import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
import org.wpilib.command2.button.CommandGamepad;
import org.wpilib.math.estimator.SwerveDrivePoseEstimator;
import org.wpilib.math.geometry.Pose2d;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.math.kinematics.ChassisVelocities;
import org.wpilib.math.kinematics.SwerveDriveKinematics;
import org.wpilib.math.kinematics.SwerveModulePosition;
import org.wpilib.math.kinematics.SwerveModuleVelocity;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.networktables.StructArrayPublisher;
import org.wpilib.networktables.StructPublisher;
import org.wpilib.units.Units;

/**
 * An extremely basic swerve drive: four simulated modules, a gyro, odometry, and the two topics the
 * FIRSTrun bridge reads.
 *
 * <p>There is no hardware layer. Each module's velocity and steering angle are first-order lagged
 * toward the requested {@link SwerveModuleVelocity}, the wheel distances are integrated, and the
 * pose estimator fuses those distances with the integrated gyro angle. Vision measurements are
 * added by {@link org.teamvan.firstrun.example.vision.Vision}.
 */
public class SwerveDrive extends SubsystemBase {
  /** Fixed loop period the integrator assumes; the simulator runs at the standard 50 Hz. */
  private static final double DT = 0.02;

  /** How quickly a module reaches a commanded velocity (1/s). */
  private static final double VELOCITY_RESPONSE = 8.0;

  /** How quickly a module reaches a commanded angle (1/s). */
  private static final double STEER_RESPONSE = 12.0;

  /** Module centers in the robot frame: FL, FR, BL, BR, matching the published order. */
  private static final Translation2d[] MODULE_OFFSETS = {
    new Translation2d(0.3, 0.3),
    new Translation2d(0.3, -0.3),
    new Translation2d(-0.3, 0.3),
    new Translation2d(-0.3, -0.3)
  };

  private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(MODULE_OFFSETS);

  /** Measured module velocities, in the order the bridge draws them. */
  private final SwerveModuleVelocity[] velocities = new SwerveModuleVelocity[4];

  private final SwerveModulePosition[] positions = new SwerveModulePosition[4];

  private final SwerveDrivePoseEstimator poseEstimator;

  private final StructPublisher<Pose2d> posePublisher;

  private final StructArrayPublisher<SwerveModuleVelocity> swervePublisher;

  /** Integrated heading; the demo has no gyro hardware, so odometry drives it. */
  private Rotation2d heading = new Rotation2d();

  public SwerveDrive() {
    for (int i = 0; i < MODULE_OFFSETS.length; i++) {
      velocities[i] = new SwerveModuleVelocity();
      positions[i] = new SwerveModulePosition(0.0, new Rotation2d());
    }
    poseEstimator =
        new SwerveDrivePoseEstimator(kinematics, heading, positions, new Pose2d(0.0, 0.0, heading));

    NetworkTableInstance nt = NetworkTableInstance.getDefault();
    posePublisher = nt.getStructTopic(Constants.POSE_TOPIC, Pose2d.struct).publish();
    swervePublisher =
        nt.getStructArrayTopic(Constants.SWERVE_TOPIC, SwerveModuleVelocity.struct).publish();

    posePublisher.setDefault(new Pose2d());
    swervePublisher.setDefault(velocities);
  }

  /** Field-relative teleop drive, with the sticks' up/left mapped to +x/+y. */
  public Command joystickDrive(CommandGamepad gamepad) {
    return run(
            () -> {
              double maxSpeed = Constants.MAX_SPEED.in(Units.MetersPerSecond);
              double maxOmega = Constants.MAX_ANGULAR_SPEED.in(Units.RadiansPerSecond);
              driveFieldRelative(
                  -gamepad.getLeftY() * maxSpeed,
                  -gamepad.getLeftX() * maxSpeed,
                  -gamepad.getRightX() * maxOmega);
            })
        .withName("Joystick drive");
  }

  /** Drives at field-relative velocities, in m/s and radians per second. */
  public void driveFieldRelative(double vx, double vy, double omega) {
    double cos = heading.getCos();
    double sin = heading.getSin();
    driveRobotRelative(
        new ChassisVelocities(vx * cos + vy * sin, -vx * sin + vy * cos, omega));
  }

  /** Drives at robot-relative velocities, in m/s and radians per second. */
  public void driveRobotRelative(ChassisVelocities requested) {
    SwerveModuleVelocity[] desired = kinematics.toSwerveModuleVelocities(requested);
    desired =
        SwerveDriveKinematics.desaturateWheelVelocities(
            desired, Constants.MAX_SPEED.in(Units.MetersPerSecond));

    for (int i = 0; i < desired.length; i++) {
      SwerveModuleVelocity target = desired[i].optimize(velocities[i].angle);
      double velocity =
          approach(velocities[i].velocity, target.velocity, VELOCITY_RESPONSE * DT);
      double angle =
          velocities[i].angle.getRadians()
              + angleError(velocities[i].angle, target.angle) * Math.min(1.0, STEER_RESPONSE * DT);

      velocities[i] = new SwerveModuleVelocity(velocity, new Rotation2d(angle));
      positions[i] =
          new SwerveModulePosition(
              positions[i].distance + velocity * DT, velocities[i].angle);
    }

    // This demo has no gyro hardware. A real swerve's gyro tracks the commanded yaw rate within
    // a loop or two, so the command is the heading sensor here. Recovering the yaw rate from the
    // lagged module vectors instead is wrong in a model with no chassis inertia: the wheels keep
    // their speed for half a second after the sticks are released, and re-aim while rolling, so
    // the robot spins on for nearly a second with no input.
    heading = heading.plus(Rotation2d.fromRadians(requested.omega * DT));
    poseEstimator.update(heading, positions);
  }

  /** The estimated field pose, which is exactly what {@link #posePublisher} publishes. */
  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

  /** The integrated heading. */
  public Rotation2d getHeading() {
    return heading;
  }

  /** Fuses a camera pose estimate at {@code timestampSeconds}. */
  public void addVisionMeasurement(Pose2d pose, double timestampSeconds) {
    poseEstimator.addVisionMeasurement(pose, timestampSeconds);
  }

  /** Recenters odometry on {@code pose}; useful when a simulation scenario restarts. */
  public void resetPose(Pose2d pose) {
    heading = pose.getRotation();
    for (int i = 0; i < positions.length; i++) {
      positions[i] = new SwerveModulePosition(positions[i].distance, heading);
    }
    poseEstimator.resetPosition(heading, positions, pose);
  }

  @Override
  public void periodic() {
    Pose2d pose = getPose();
    posePublisher.set(pose);
    swervePublisher.set(velocities);

    // AdvantageKit records the same values, so a log replay reproduces the Rerun recording.
    Logger.recordOutput("Drive/Pose2d", pose);
    Logger.recordOutput("Drive/HeadingDegrees", heading.getDegrees());
    Logger.recordOutput("Drive/SwerveModuleVelocities", velocities);
  }

  /** Linear move from {@code current} toward {@code target} by at most {@code step}. */
  private static double approach(double current, double target, double step) {
    double delta = target - current;
    if (Math.abs(delta) <= step) {
      return target;
    }
    return current + Math.copySign(step, delta);
  }

  /** Shortest signed difference between two angles, in radians. */
  private static double angleError(Rotation2d current, Rotation2d target) {
    return target.minus(current).getRadians();
  }
}
