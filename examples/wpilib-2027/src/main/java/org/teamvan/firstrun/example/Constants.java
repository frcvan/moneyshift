package org.teamvan.firstrun.example;

import org.wpilib.framework.RobotBase;
import org.wpilib.units.Units;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.LinearVelocity;

/**
 * The demo robot's constants.
 *
 * <p>Everything is deliberately small: a square 0.6 m swerve base, one front camera, and the
 * NetworkTables topic names the FIRSTrun bridge reads. The camera stream is PhotonVision's MJPEG
 * URL, which is all a camera is to the bridge.
 */
public final class Constants {
  private Constants() {}

  /** The team number this demo declares to the bridge and deploys as. */
  public static final int TEAM = 6391;

  /** Runtime mode, mirroring AdvantageKit's convention. */
  public enum Mode {
    /** Running on a real robot. */
    REAL,
    /** Running a physics simulator. */
    SIM,
    /** Replaying from a log file. */
    REPLAY
  }

  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  /** Which implementations the robot builds are not swappable here: the demo is sim-only. */
  public static final boolean isSimulation = currentMode == Mode.SIM;

  /** Distance between the left and right module centers. */
  public static final Distance TRACK_WIDTH = Units.Meters.of(0.6);

  /** Distance between the front and back module centers. */
  public static final Distance WHEEL_BASE = Units.Meters.of(0.6);

  /** Free speed of the drive, so the joystick commands something realistic. */
  public static final LinearVelocity MAX_SPEED = Units.MetersPerSecond.of(4.5);

  /** Full-stick rotation, in radians per second. */
  public static final AngularVelocity MAX_ANGULAR_SPEED =
      Units.RadiansPerSecond.of(2.0 * Math.PI);

  /** The topic the bridge reads a `struct:Pose2d` from. */
  public static final String POSE_TOPIC = "/Demo/Drive/Pose2d";

  /** The topic the bridge reads a `struct:SwerveModuleVelocity[]` from. */
  public static final String SWERVE_TOPIC = "/Demo/Drive/SwerveModuleVelocities";

  /** PhotonVision camera name, which is also its NetworkTables table name. */
  public static final String CAMERA_NAME = "front";

  /**
   * PhotonVision's MJPEG stream for {@link #CAMERA_NAME}.
   *
   * <p>A camera is just a link: `http://<coprocessor>:1181/stream.mjpg` for camera 0 and
   * `:1182` for camera 1. The bridge reads that stream itself, so no external process is needed
   * for footage; in simulation `VisionSystemSim` serves this URL with what the simulated camera
   * sees.
   */
  public static final String CAMERA_STREAM = "http://127.0.0.1:1181/stream.mjpg";
}
