package frc.robot.subsystems;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AprilTagOdometry extends SubsystemBase{
    private static AprilTagOdometry instance;

    public static  AprilTagOdometry getInstance() {
        if (instance == null) {
          instance = new AprilTagOdometry();
        }
        return instance;
        }
    public PhotonCamera frontCam = new PhotonCamera("FrontCam");
    public PhotonCamera backCam = new PhotonCamera("BackCam");
    Transform3d robotToFrontCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.
    AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

// Construct PhotonPoseEstimator
PhotonPoseEstimator photonFrontPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, robotToFrontCam);// private final Controls m_controlsSubsystem = new Controls();
    public AprilTagOdometry(){
    }
    public Optional<EstimatedRobotPose> getFrontEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        photonFrontPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        return photonFrontPoseEstimator.update(frontCam.getLatestResult());
    }

        public Optional<Pose3d> getTargetPose(PhotonTrackedTarget target) {
        int fiducialId = target.getFiducialId();
        Optional<Pose3d> tagPose = aprilTagFieldLayout.getTagPose(fiducialId);
        // Ensure the existence of this tag id, print warning
        if (tagPose.isEmpty()) {
            DriverStation.reportWarning("Fiducial id " + fiducialId + " not recognized", false);
        }
        // Return with optional null value
        return tagPose;
    }

    // Converts an Optional<Pose3d> to a Pose2d
    public Pose2d convertToPose2d(Optional<Pose3d> pose3dOptional) {
        if (pose3dOptional.isPresent()) {
            Pose3d pose3d = pose3dOptional.get();

            // Convert Rotation3d to Rotation2d
            Rotation3d rotation3d = pose3d.getRotation();
            Rotation2d rotation2d = rotation3d.toRotation2d();
            
            return new Pose2d(pose3d.getX(), pose3d.getY(), rotation2d);
        } else {
            return new Pose2d(0, 0, new Rotation2d(0));
        }
    }
    
    // Gets distance from robot to apriltag
    public double getDistanceToTarget(PhotonTrackedTarget target) {
        Optional<Pose3d> tagPose = getTargetPose(target);
        // Ensure the existence of this tag id
        if (tagPose.isEmpty()) {return -1;}

        Transform3d cameraToTarget = target.getBestCameraToTarget();
        return Math.hypot(cameraToTarget.getX(), cameraToTarget.getY());
    }

    // Gets ID of the nearest AprilTag
    public PhotonTrackedTarget bestTarget() {
        PhotonPipelineResult result = frontCam.getLatestResult();
        PhotonTrackedTarget targetID = result.getBestTarget();
        return targetID;
    }

    @Override
    public void periodic() {
        Optional<EstimatedRobotPose> positionSample = getFrontEstimatedGlobalPose(SwerveDrive.getInstance().getPosition());
        if (positionSample.isPresent()) {
            SwerveDrive.getInstance().addVisionMeasurement(
                positionSample.get().estimatedPose.toPose2d(), Timer.getFPGATimestamp()
            );
        }
    }
}
