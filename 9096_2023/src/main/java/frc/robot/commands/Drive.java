package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

public class Drive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  double pow;
  double clawPow;
  
  public Drive(DriveBase system) {
    driveSystem = system;
    addRequirements(system);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    pow = (ctrl.getTrigger() ? 0.9f : 0.6f);
    clawPow = (ctrl.getTrigger() ? 0.75f : 0.63f);
    driveSystem.setArm(armCtrl.getRawAxis(5) * 0.4);
    driveSystem.setSpeed(ctrl.getX() * pow, ctrl.getY() * clawPow);
  
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
