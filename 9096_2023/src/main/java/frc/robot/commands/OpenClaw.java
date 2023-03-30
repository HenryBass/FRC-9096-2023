package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;

public class OpenClaw extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  double clawTime = 0;
  double timestamp = 0;
  
  public OpenClaw(DriveBase drivesystem) {
    driveSystem = drivesystem;
    addRequirements(drivesystem);
  }

  @Override
  public void initialize() {
    driveSystem.halt();
    timestamp = Timer.getFPGATimestamp();
  }

  @Override
  public void execute() {
    clawTime = (Timer.getFPGATimestamp() - timestamp);
    driveSystem.setArm(0.01);
    driveSystem.setClaw(-0.3);
  }

  @Override
  public void end(boolean interrupted) {
    driveSystem.halt();
  }

  @Override
  public boolean isFinished() {
    return clawTime > 2.0;
  }
}
