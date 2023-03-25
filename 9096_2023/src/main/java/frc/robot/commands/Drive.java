package frc.robot.commands;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

public class Drive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  private final Limelight limelightSystem;

  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  double pow;
  double clawPow;
  
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

  @Override
  public void execute() {
    pow = (ctrl.getTrigger() ? 0.9f : 0.6f);
    clawPow = (ctrl.getTrigger() ? 0.75f : 0.63f);
    driveSystem.setArm(armCtrl.getRawAxis(5) * -0.4);
    driveSystem.drive(ctrl.getX() * pow, ctrl.getY() * pow);
  
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
