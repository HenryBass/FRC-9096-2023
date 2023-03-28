package frc.robot.commands;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;

  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  double pow;
  double armPow;
  double armInput;
  
  public Drive(DriveBase drivesystem, Limelight limelightsystem) {
    driveSystem = drivesystem;
    limelightSystem = limelightsystem;
    addRequirements(drivesystem, limelightsystem);
  }

  @Override
  public void initialize() {
    limelightSystem.setPipeline(1);
    driveSystem.halt();
  }

  double forceCurve(double x) {
    return Math.pow(x, 2) * (x / Math.abs(x));
  }

  @Override
  public void execute() {
    armInput = armCtrl.getRawAxis(5);

    pow = (ctrl.getTrigger() ? 0.9f : 0.6f);
    armPow = (ctrl.getTrigger() ? 0.75f : 0.63f);
    driveSystem.setClaw(driveSystem.clawCurrentLimit(armInput * 0.4));
    driveSystem.setArm(armCtrl.getY() * 0.4);
    driveSystem.drive(forceCurve(ctrl.getX()) * pow, forceCurve(ctrl.getY()) * pow);

    if (Math.abs(armInput) > 0.05) {
      SmartDashboard.putString("Claw:", (armInput < 0) ? "Out" : "In");
    } else {
      SmartDashboard.putString("Claw:", "Not moving");
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
