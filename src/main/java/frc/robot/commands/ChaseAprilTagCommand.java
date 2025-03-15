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
    private final PIDController xController = new PIDController(0.5, 0, 0); // TODO: tune PID
    private final PIDController yController = new PIDController(0.5, 0, 0);
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
        Optional<Pose3d> targetAprilTagPose3d = camera.getTargetPose(camera.bestTarget());

        if (targetAprilTagPose3d != null) {
            Pose2d targetAprilTagPose2d = AprilTagOdometry.convertToPose2d(targetAprilTagPose3d);

            // Calculate target position relative to the tag
            Translation2d targetTranslation = targetAprilTagPose2d.getTranslation().minus(new Translation2d(targetX, targetY));

            // Move robot to the calculated position
            driveToPosition(new Pose2d(targetTranslation, targetAprilTagPose2d.getRotation()));
        }
    }
    @Override
    public boolean isFinished() {
        return atTargetPosition();
    }

    @Override
    public void execute() {
        System.out.println(targetPose.getY());
        SmartDashboard.putNumber("Target Pose X", targetPose.getX());
        SmartDashboard.putNumber("Target Pose Y", targetPose.getY());

        if(targetPose == null){
        return;
        }

        if(atTarget){
        targetPose = null;
        }

        if (SwerveDrive.getInstance().getPosition() != null && targetPose != null) {
        ChassisSpeeds speeds = holonomicController.calculate(SwerveDrive.getInstance().getPosition(), targetPose, 0.25, targetPose.getRotation()); // TODO: change desired linear velocity
        // SwerveModuleState[] moduleStates = Constants.DriveConstants.kDriveKinematics.toSwerveModuleStates(speeds);
        SwerveDrive.getInstance().driveRobotRelative(new ChassisSpeeds(-speeds.vxMetersPerSecond, speeds.vyMetersPerSecond, speeds.omegaRadiansPerSecond));
        atTarget = xController.atSetpoint() && yController.atSetpoint() && thetaController.atGoal();
        }
    }
}
