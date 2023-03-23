package frc.robot.commands;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Joystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class MoveArm extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveBase driveSystem;
  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  double pow;
  double clawPow;
  float aPow;
  float autoDist = 2.6f;
  double autoTime = 0;
  int autoState = 0;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  double timestamp = 0;
  NetworkTableEntry botpose = table.getEntry("targetpose_cameraspace");
  
  public MoveArm(DriveBase system) {
    driveSystem = system;
    addRequirements(system);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    autoTime = (Timer.getFPGATimestamp() - timestamp);
    double v = tv.getDouble(0.0);
    double[] pose = botpose.getDoubleArray(new double[6]);

    SmartDashboard.putNumber("Apriltag distance", pose[2]);
    SmartDashboard.putNumber("Apriltag angle", pose[4]);
    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Auto Time", autoTime);
    if (pose[2] < autoDist & v > 0) {
      //drive.arcadeDrive(0,  0.0);     
      //drive.arcadeDrive(0.5, Math.tanh(pose[4]) / 2);
    }
    else {
      //drive.arcadeDrive(0,  0.0);
    }

    if (autoTime < 7.0f) {
      driveSystem.setArm(0.2);
    } else if (autoTime > 8.0f && autoTime < 9.5f) {
      driveSystem.setClaw(-0.2);
      driveSystem.setArm(0.0);
    } else {
      driveSystem.setClaw(0.0);
      driveSystem.setArm(0.0);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
