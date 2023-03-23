package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class RobotContainer {
  private final DriveBase driveSubsystem = new DriveBase();

  public RobotContainer() {
    driveSubsystem.setDefaultCommand(new ParallelCommandGroup(new Drive(driveSubsystem)));
  }

  public Command getAutonomousCommand() {
    return Autos.getAuto(driveSubsystem);
  }

}
