package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

public class LockCube extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;

  double autoTime = 0;
  double timestamp = Timer.getFPGATimestamp();
  double offset = 0;
  
  public LockCube(DriveBase drivesystem, Limelight limelightsystem) {
    driveSystem = drivesystem;
    limelightSystem = limelightsystem;
    addRequirements(drivesystem, limelightsystem);
  }

  @Override
  public void initialize() {
    limelightSystem.setPipeline(2);
    driveSystem.halt();
  }

  @Override
  public void execute() {
    autoTime = (Timer.getFPGATimestamp() - timestamp);
    offset = limelightSystem.getXOffset();
    System.out.println(offset);
    //driveSystem.drive(0, Math.tanh(offset) / 2);
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
