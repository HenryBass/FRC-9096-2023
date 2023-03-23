package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  public static CommandBase getAuto(DriveBase subsystem) {
    return Commands.sequence(new MoveArm(subsystem));
  }

}
