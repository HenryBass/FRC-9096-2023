package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class MoveArm extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;

  float autoDist = 2.6f;
  double autoTime = 0;
  double timestamp = Timer.getFPGATimestamp();
  
  public MoveArm(DriveBase drivesystem, Limelight limelightsystem) {
    driveSystem = drivesystem;
    limelightSystem = limelightsystem;
    addRequirements(drivesystem, limelightsystem);
  }

  @Override
  public void initialize() {
    limelightSystem.setPipeline(0);
    driveSystem.halt();
  }

  @Override
  public void execute() {
    autoTime = (Timer.getFPGATimestamp() - timestamp);

    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Auto Time", autoTime);
    driveSystem.setArm((autoTime > 5.0) ? 0.1 : 0.4);
  }

  @Override
  public void end(boolean interrupted) {
    driveSystem.halt();
  }

  @Override
  public boolean isFinished() {
    return autoTime > 6.5f;
  }
}
