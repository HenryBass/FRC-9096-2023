package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;

import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveBase extends SubsystemBase {
  WPI_VictorSPX victorFL = new WPI_VictorSPX(10);
  WPI_VictorSPX victorFR = new WPI_VictorSPX(11);
  WPI_VictorSPX victorBR = new WPI_VictorSPX(12);
  WPI_VictorSPX victorBL = new WPI_VictorSPX(13);

  CANSparkMax arm = new CANSparkMax(20, MotorType.kBrushless);
  CANSparkMax claw = new CANSparkMax(21, MotorType.kBrushless);
  MotorControllerGroup left = new MotorControllerGroup(victorFL, victorBL);
  MotorControllerGroup right = new MotorControllerGroup(victorFR, victorBR);
  DifferentialDrive drive = new DifferentialDrive(left, right);

  double driveSpeed = 0.0;
  double driveAngle = 0.0;
  double armSpeed = 0.0;
  double clawSpeed = 0.0;

  public DriveBase() {
    left.setInverted(false);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw current", claw.getOutputCurrent());
    drive.arcadeDrive(driveSpeed, driveAngle);
    
    arm.set(armSpeed);
    claw.set(clawSpeed);
  }

  public double getClawCurrent() {
    return claw.getOutputCurrent();
  }

  public double getArmCurrent() {
    return arm.getOutputCurrent();
  }

  public double clawCurrentLimit(double speed) {
    if (getClawCurrent() > 40.0) {
      return 0.0;
    } else {
      return speed;
    }
  }

  public double armCurrentLimit(double speed) {
    if (getArmCurrent() > 40.0) {
      return 0.0;
    } else {
      return speed;
    }
  }

  public void drive(double speed, double rotation) {
    driveSpeed = speed;
    driveAngle = rotation;
  }

  public void setArm(double speed) {
    armSpeed = speed;
  }

  public void setClaw(double speed) {
    clawSpeed = speed;
  }

  public void halt() {
    setClaw(0.0);
    setArm(0.0);
    drive(0.0, 0.0);
  }

}
