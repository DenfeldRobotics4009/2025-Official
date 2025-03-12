package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.SwerveDrive;

  /**
   * Only run this in teleopInit.
   */

public class AutoResetOdometry extends Command {

    @Override
    public void initialize() {
        if (AprilTagOdometry.getInstance().bestTarget() != null) {
            double targetAprilTagYaw = AprilTagOdometry.getInstance().bestTarget().getYaw();
            if (DriverStation.Alliance.valueOf(getName()).equals(DriverStation.Alliance.Blue)) {
                SwerveDrive.getInstance().m_gyro.setAngleAdjustment(targetAprilTagYaw - 180);
            } else {
                SwerveDrive.getInstance().m_gyro.setAngleAdjustment(targetAprilTagYaw);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
