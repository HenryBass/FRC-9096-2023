package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;

public final class Autos {
  public static CommandBase getAuto(DriveBase drivesubsystem, Limelight limelightsubsystem) {
    return Commands.sequence(
      new MoveArm(drivesubsystem, limelightsubsystem),
      new DriveBack(drivesubsystem, limelightsubsystem)
    );
  }

}
