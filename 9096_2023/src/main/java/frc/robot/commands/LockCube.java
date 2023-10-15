package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LockCube extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;
  PIDController pid;
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
    //System.out.println(SmartDashboard.getNumber("Kp", 4));
    //pid.setPID(SmartDashboard.getNumber("Kp", 0), SmartDashboard.getNumber("Ki", 0), SmartDashboard.getNumber("Kd", 0));
    offset = limelightSystem.getXOffset();
    driveSystem.drive(0, Math.tanh(offset - 320) * 0.4);
    //driveSystem.drive(0, pid.calculate(offset, 620));
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
