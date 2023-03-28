package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTable grip = NetworkTableInstance.getDefault().getTable("GRIP/Contours");

  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  NetworkTableEntry botpose = table.getEntry("targetpose_cameraspace");
  NetworkTableEntry cx = grip.getEntry("CenterX");
  double v = tv.getNumber(0).doubleValue();
  double[] pose = botpose.getDoubleArray(new double[6]);

  public Limelight() {
  }

  public Number getPipeline() {
    return pipeline.getNumber(0);
  }

  public void setPipeline(Number newPipeline) {
    pipeline.setNumber(newPipeline);
  }

  public double getPose(int index) {
    return pose[index];
  }

  public double getAngle() {
    return pose[4];
  }

  public double getDist() {
    return pose[2];
  }

  public double getVisible() {
    return tv.getDouble(0.0);
  }

  public double getXOffset() {
    return tx.getDouble(0.0);
  }

  public double getEntry(String entry) {
    return table.getEntry(entry).getDouble(0.0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Apriltag distance", pose[2]);
    SmartDashboard.putNumber("Apriltag angle", pose[4]);
    SmartDashboard.putNumber("GRIP", cx.getDouble(0.0));
    SmartDashboard.putNumber("Visible", getVisible());
  }

}
