package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {
  private Command autonomousCommand;
  private Command driveCommand;
  private RobotContainer robotContainer;
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  double timestamp = 0;
  NetworkTableEntry botpose = table.getEntry("targetpose_cameraspace");

  @Override
  public void robotInit() {
    //victorBL.follow(victorFL);
    //victorBR.follow(victorFR);

    //victorBR.setInverted(InvertType.FollowMaster);

    robotContainer = new RobotContainer();
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
    autonomousCommand = robotContainer.getAutonomousCommand();
    timestamp = Timer.getFPGATimestamp();
    pipeline.setInteger(0);
  }

  @Override
  public void autonomousPeriodic() {
   
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    pipeline.setNumber(1);    
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
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
