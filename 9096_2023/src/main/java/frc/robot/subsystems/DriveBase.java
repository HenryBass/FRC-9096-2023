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

  public DriveBase() {
    left.setInverted(false);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw current", claw.getOutputCurrent());
  }

  public double getClawCurrent() {
    return claw.getOutputCurrent();
  }

  public double getArmCurrent() {
    return arm.getOutputCurrent();
  }

  public void drive(double speed, double rotation) {
    drive.arcadeDrive(speed, rotation);
  }

  public void setArm(double speed) {
    arm.set(speed);
  }

  public void setClaw(double speed) {
    claw.set(speed);
  }

  public void halt() {
    claw.set(0.0);
    arm.set(0.0);
  }

}
