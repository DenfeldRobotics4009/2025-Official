package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.SwerveDrive;

  /**
   * Only run this in autonomousInit.
   */

public class AutoResetOdometry extends Command {

    @Override
    public void initialize() {
        if (AprilTagOdometry.getInstance().bestTarget() != null) {
            double targetAprilTagYaw = AprilTagOdometry.getInstance().bestTarget().getYaw();
            SwerveDrive.getInstance().m_gyro.setAngleAdjustment(targetAprilTagYaw - 180);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
