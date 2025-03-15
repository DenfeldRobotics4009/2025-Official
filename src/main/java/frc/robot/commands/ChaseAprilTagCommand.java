package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.Constants;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.Controls;

public class ChaseAprilTagCommand extends Command{
    // PID controllers for auto align
    private final PIDController xController = new PIDController(4, 0, 0.5); // TODO: tune PID
    private final PIDController yController = new PIDController(4, 0, 0.5);
    private final ProfiledPIDController thetaController = new ProfiledPIDController(3, 0, 0, new edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints(Math.PI, Math.PI / 2));
    private AprilTagOdometry camera;
    private SwerveDrive swerveDrive;
    private double targetX;
    private double targetY;
    private Pose2d targetPose;
    private boolean atTarget = false;
    private final HolonomicDriveController holonomicController = 
    new HolonomicDriveController(xController, yController, thetaController);
    
    public void driveToPosition(Pose2d target) {
      targetPose = target;
      atTarget = false;
    }
  
    public boolean atTargetPosition() {
      return atTarget;
    }

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
        // Optional<Pose3d> targetAprilTagPose3d = camera.getTargetPose(camera.bestTarget());

        // if (targetAprilTagPose3d != null) {
        //     Pose2d tagPose = AprilTagOdometry.convertToPose2d(targetAprilTagPose3d);

        //     // Calculate target position relative to the tag
        //     Translation2d targetTranslation = new Translation2d(targetX, targetY).rotateBy(tagPose.getRotation());
        //     Pose2d targetPose = new Pose2d(tagPose.getTranslation().plus(targetTranslation), tagPose.getRotation().minus(Rotation2d.fromDegrees(180)));

        //     // Move robot to the calculated position
        //     swerveDrive.driveToPosition(targetPose);
        // }
    }
    @Override
    public boolean isFinished() {
        // return swerveDrive.atTargetPosition();
        return false;
    }

    @Override
    public void execute() {

        if(targetPose == null){
        return;
        }

        if(atTarget){
        targetPose = null;
        }

        if (SwerveDrive.getInstance().getPosition() != null && targetPose != null) {
        ChassisSpeeds speeds = holonomicController.calculate(SwerveDrive.getInstance().getPosition(), targetPose, 0.25, targetPose.getRotation()); // TODO: change desired linear velocity
        SwerveModuleState[] moduleStates = Constants.DriveConstants.kDriveKinematics.toSwerveModuleStates(speeds);
        SwerveDrive.getInstance().driveRobotRelative(speeds);
        atTarget = xController.atSetpoint() && yController.atSetpoint() && thetaController.atGoal();
        }
    }
}
