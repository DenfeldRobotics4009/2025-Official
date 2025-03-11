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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.Controls;

public class ChaseAprilTagCommand extends Command{
    private AprilTagOdometry camera;
    private SwerveDrive swerveDrive;
    private double targetX;
    private double targetY;
    private Integer fiducialId = null;

    /**
     * This constructor passes in values for our swerve drive, our camera, 
     * and our robot's x and y offset when we don't give it an ID to go to.
     * @param swerveDrive The swerve drive subsystem.
     * @param camera The April Tag camera.
     * @param offsetX The x offset relative to the April Tag.
     * @param offsetY The y offset relative to the April Tag.
     */
    public ChaseAprilTagCommand(SwerveDrive swerveDrive, AprilTagOdometry camera, double offsetX, double offsetY) {
        this.swerveDrive = swerveDrive;
        this.camera = camera;
        this.targetX = offsetX;
        this.targetY = offsetY;
        addRequirements(camera, swerveDrive);
    } 
    /**
     * This constructor passes in values for our swerve drive, our camera, 
     * and our robot's x and y offset when we give it an ID to go to.
     * @param swerveDrive The swerve drive subsystem.
     * @param camera The April Tag camera.
     * @param offsetX The x offset relative to the April Tag.
     * @param offsetY The y offset relative to the April Tag.
     * @param fiducialId The April Tag Id.
     */
    public ChaseAprilTagCommand(SwerveDrive swerveDrive, AprilTagOdometry camera, double offsetX, double offsetY, int fiducialId){
        this.swerveDrive = swerveDrive;
        this.camera = camera;
        this.targetX = offsetX;
        this.targetY = offsetY;
        this.fiducialId = fiducialId;
        addRequirements(camera, swerveDrive);
    }

    @Override
    public void end(boolean interrupted) {
        
    }
    
    /**
     * If there's no April Tag passed into the command, go towards the best target.
     * If there's an April Tag passed into the command, go towards that ID.
     * If you pass in an invalid April Tag Id, a warning shows up saying you did so.
     * 
     * Converts the April Tag's position to a 2d position on the field.
     */
    @Override
    public void initialize() {
        Optional<Pose3d> targetAprilTagPose3d;
        if (fiducialId == null) {
            // Get AprilTag Pose
            targetAprilTagPose3d = camera.getTargetPose(camera.bestTarget());
        } else {
            targetAprilTagPose3d = camera.aprilTagFieldLayout.getTagPose(fiducialId);
            if (!targetAprilTagPose3d.isPresent()) {
                throw new IllegalArgumentException("April Tag Id " + fiducialId + " doesn't exist on the given field.");
            }
        }

        if (targetAprilTagPose3d.isPresent()) {
            Pose2d tagPose = AprilTagOdometry.convertToPose2d(targetAprilTagPose3d);

            // Calculate target position relative to the tag
            Translation2d targetTranslation = new Translation2d(targetX, targetY).rotateBy(tagPose.getRotation());
            Pose2d targetPose = new Pose2d(tagPose.getTranslation().plus(targetTranslation), tagPose.getRotation().minus(Rotation2d.fromDegrees(180)));

            // Move robot to the calculated position
            swerveDrive.driveToPosition(targetPose);
        }
    }
    @Override
    public boolean isFinished() {
        return swerveDrive.atTargetPosition();
    }
    @Override
    public void execute() {

    }
}
