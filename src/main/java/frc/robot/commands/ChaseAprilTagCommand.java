package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.Controls;

public class ChaseAprilTagCommand extends Command{
    private AprilTagOdometry camera;
    private SwerveDrive swerveDrive;

        public void Chase(
            AprilTagOdometry camera,
            SwerveDrive swerveDrive
        ) {
            this.camera = camera;
            this. swerveDrive = swerveDrive;
            addRequirements(camera, swerveDrive);
        }
    

    @Override
    public void end(boolean interrupted) {
        
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void execute() {
        // Gets pose of nearest AprilTag
        Optional<Pose3d> targetAprilTagPose3d = camera.getTargetPose(camera.bestTarget());
        Pose2d targetAprilTagPose2d = camera.convertToPose2d(targetAprilTagPose3d);
        
        // Create new robot target based on AprilTag pose
        Pose2d robotTargetPose2d = new Pose2d();

        // Set drive position to target (left or right)
        swerveDrive.setPosition(robotTargetPose2d);
    }
}
