package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.Controls;

public class ChaseAprilTagCommand extends Command{
    private AprilTagOdometry camera;
    private SwerveDrive swerveDrive;
    private double targetX, targetY;

        public ChaseAprilTagCommand(SwerveDrive swerveDrive, AprilTagOdometry camera, double offsetX, double offsetY) {
            this.swerveDrive = swerveDrive;
            this.camera = camera;
            this.targetX = offsetX;
            this.targetY = offsetY;
            addRequirements(camera, swerveDrive);
        } 

    @Override
    public void end(boolean interrupted) {
        
    }
    @Override
    public void initialize() {
        // Get AprilTag Pose
        Optional<Pose3d> targetAprilTagPose3d = camera.getTargetPose(camera.bestTarget());
        Pose2d targetAprilTagPose2d = AprilTagOdometry.convertToPose2d(targetAprilTagPose3d);

        if (targetAprilTagPose2d != null) {
            // Calculate target position relative to the tag
            Translation2d targetTranslation = targetAprilTagPose2d.getTranslation().plus(new Translation2d(targetX, targetY));
            
            // Move robot to the calculated position
            swerveDrive.driveToPosition(new Pose2d(targetTranslation, targetAprilTagPose2d.getRotation().minus(Rotation2d.fromDegrees(180))));
        }
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void execute() {
    }
}
