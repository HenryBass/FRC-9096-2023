package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class DriveBack extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;
  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  double pow;
  double clawPow;
  float aPow;
  float autoDist = 2.6f;
  double autoTime = 0;
  int autoState = 0;
  double timestamp = 0;
  
  public DriveBack(DriveBase drivesystem, Limelight limelightsystem) {
    driveSystem = drivesystem;
    limelightSystem = limelightsystem;
    addRequirements(drivesystem, limelightsystem);
  }

  @Override
  public void initialize() {
    limelightSystem.setPipeline(0);
  }

  @Override
  public void execute() {
    autoTime = (Timer.getFPGATimestamp() - timestamp);

    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Auto Time", autoTime);
    if (limelightSystem.getDist() < autoDist) {
      driveSystem.drive(0.5, Math.tanh(limelightSystem.getAngle()) / 2);
    }
    else {
      driveSystem.halt();
    }

  }

  @Override
  public void end(boolean interrupted) {
    driveSystem.halt();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
