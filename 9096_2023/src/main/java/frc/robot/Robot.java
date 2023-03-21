package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Joystick;

import org.opencv.features2d.FastFeatureDetector;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DigitalOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  WPI_VictorSPX victorFL = new WPI_VictorSPX(10);
  WPI_VictorSPX victorFR = new WPI_VictorSPX(11);
  WPI_VictorSPX victorBR = new WPI_VictorSPX(12);
  WPI_VictorSPX victorBL = new WPI_VictorSPX(13);
  Joystick armCtrl = new Joystick(1);
  Joystick ctrl = new Joystick(0);
  CANSparkMax arm = new CANSparkMax(20, MotorType.kBrushless);
  CANSparkMax claw = new CANSparkMax(21, MotorType.kBrushless);
  MotorControllerGroup left = new MotorControllerGroup(victorFL, victorBL);
  MotorControllerGroup right = new MotorControllerGroup(victorFR, victorBR);
  DifferentialDrive drive = new DifferentialDrive(left, right);
  DigitalOutput dout = new DigitalOutput(0);
  RelativeEncoder clawEncoder = claw.getAlternateEncoder(42);
  float pow;
  float clawPow;
  float aPow;
  float autoDist = 2.6f;
  double autoTime = 0;
  int autoState = 0;
  private RobotContainer m_robotContainer;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  double timestamp = 0;
  NetworkTableEntry botpose = table.getEntry("targetpose_cameraspace");

  @Override
  public void robotInit() {
    //victorBL.follow(victorFL);
    //victorBR.follow(victorFR);

    //victorBR.setInverted(InvertType.FollowMaster);

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    left.setInverted(true);
    timestamp = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
   
    autoTime = (Timer.getFPGATimestamp() - timestamp);
    double v = tv.getDouble(0.0);
    double[] pose = botpose.getDoubleArray(new double[6]);

    SmartDashboard.putNumber("Apriltag distance", pose[2]);
    SmartDashboard.putNumber("Apriltag angle", pose[4]);
    SmartDashboard.putNumber("Time", Timer.getFPGATimestamp());
    SmartDashboard.putNumber("Auto Time", autoTime);
    SmartDashboard.putData(drive);
    if (pose[2] < autoDist & v > 0) {
      drive.arcadeDrive(0,  0.0);     
      //drive.arcadeDrive(0.5, Math.tanh(pose[4]) / 2);
    }
    else {
      drive.arcadeDrive(0,  0.0);
    }

    if (autoTime < 7.0f) {
      arm.set(0.2);
    } else if (autoTime > 8.0f && autoTime < 9.5f) {
      claw.set(-0.2);
      arm.set(0);
    } else {
      arm.set(0);
      claw.set(0);
    }
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    left.setInverted(false);
  }

  @Override
  public void teleopPeriodic() {

    arm.set((armCtrl.getY() * Math.sqrt(Math.abs(armCtrl.getY()))) * 0.4);
    dout.set(armCtrl.getTrigger());
    pow = (ctrl.getTrigger() ? 0.9f : 0.6f);
    clawPow = (ctrl.getTrigger() ? 0.75f : 0.63f);
    drive.arcadeDrive(ctrl.getX() * pow, ctrl.getY() * clawPow);

   // if (claw.getOutputCurrent() < 30 & armCtrl.getRawAxis(5) < 0) {
      claw.set(armCtrl.getRawAxis(5) * 0.4);
   // } else if (armCtrl.getRawAxis(5) < 0) {
    //  claw.set(armCtrl.getRawAxis(5) * 0.2);
    //} else {
    //  claw.set(0);
    //}

    SmartDashboard.putNumber("Claw current", claw.getOutputCurrent());
    SmartDashboard.putNumber("Claw rotations", claw.getOutputCurrent());
  }

  @Override
  public void testInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {
  }
}
