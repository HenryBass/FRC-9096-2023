package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight extends SubsystemBase {

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTable grip = NetworkTableInstance.getDefault().getTable("Contours");

  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");
  NetworkTableEntry pipeline = table.getEntry("pipeline");
  NetworkTableEntry botpose = table.getEntry("targetpose_cameraspace");
  NetworkTableEntry gripEntry = grip.getEntry("centerX");
  double v = tv.getNumber(0).doubleValue();

  public Limelight() {
  }

  public Number getPipeline() {
    return pipeline.getNumber(0);
  }

  public void setPipeline(Number newPipeline) {
    pipeline.setNumber(newPipeline);
  }

  public double getPose(int index) {
    double[] pose = botpose.getDoubleArray(new double[6]);
    return pose[index];
  }

  public double getAngle() {
    double[] pose = botpose.getDoubleArray(new double[6]);
    return pose[4];
  }

  public double getDist() {
    double[] pose = botpose.getDoubleArray(new double[6]);
    return pose[2];
  }

  public double getVisible() {
    return tv.getDouble(0.0);
  }

  public double getXOffset() {
    double[] res = gripEntry.getDoubleArray(new double[] {});
    if (res.length > 0) {
      return res[0];
    } else {
      return 0.0;
    }
  }

  public double getEntry(String entry) {
    return table.getEntry(entry).getDouble(0.0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Apriltag distance", getPose(2));
    SmartDashboard.putNumber("Apriltag angle", getPose(4));
    SmartDashboard.putNumber("GRIP", getXOffset());
    SmartDashboard.putNumber("Visible", getVisible());
  }

}
