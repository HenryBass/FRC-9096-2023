package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class RobotContainer {
  private final DriveBase driveSubsystem = new DriveBase();
  private final Limelight limelightSubsystem = new Limelight();

  public RobotContainer() {
    driveSubsystem.setDefaultCommand(new ParallelCommandGroup(new Drive(driveSubsystem, limelightSubsystem)));
  }

  public Command getAutonomousCommand() {
    return Autos.getAuto(driveSubsystem, limelightSubsystem);
  }

}
