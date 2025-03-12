package frc.robot.commands;

import java.util.Optional;

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

            // Optional<Alliance> alliance = DriverStation.getAlliance();

            // if (alliance.isPresent()) {
            //     if (alliance.get() == Alliance.Red) {
            //         SwerveDrive.getInstance().m_gyro.setAngleAdjustment(targetAprilTagYaw - 180);
            //     }
            //     if (alliance.get() == Alliance.Blue) {
            //         SwerveDrive.getInstance().m_gyro.setAngleAdjustment(targetAprilTagYaw);
            //     }
            // }
            // else {
            //     SwerveDrive.getInstance().m_gyro.setAngleAdjustment(0);
            // }
            SwerveDrive.getInstance().m_gyro.setAngleAdjustment(0);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
