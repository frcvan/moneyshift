package org.teamvan.firstrun.example;

import org.wpilib.framework.RobotBase;

/** Entry point. Do not add static state here. */
public final class Main {
  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
