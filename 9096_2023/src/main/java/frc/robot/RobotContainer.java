package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.commands.LockCube;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

public class RobotContainer {
  private final DriveBase driveSubsystem = new DriveBase();
  private final Limelight limelightSubsystem = new Limelight();
  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  Trigger engageCubeLock = new JoystickButton(ctrl, 7);

  public RobotContainer() {
    driveSubsystem.setDefaultCommand(new Drive(driveSubsystem, limelightSubsystem));
    configureButtonBindings();
  }

  public Command getAutonomousCommand() {
    return Autos.getAuto(driveSubsystem, limelightSubsystem);
  }

  private void configureButtonBindings() {
    engageCubeLock.toggleOnTrue(new LockCube(driveSubsystem, limelightSubsystem));
  }

}