package org.teamvan.firstrun.example;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.wpilib.command2.Command;
import org.wpilib.command2.CommandScheduler;

/** The robot lifecycle: AdvantageKit logging, the command scheduler, and the demo subsystems. */
public class Robot extends LoggedRobot {
  private final RobotContainer container;
  private Command autonomousCommand;

  public Robot() {
    // AdvantageKit publishes the log to NetworkTables, so AdvantageScope and the FIRSTrun bridge
    // see live data next to the topics the robot publishes itself.
    Logger.addDataReceiver(new NT4Publisher());
    Logger.start();

    container = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    container.periodic();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = container.autonomousCommand();
    CommandScheduler.getInstance().schedule(autonomousCommand);
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }
}
