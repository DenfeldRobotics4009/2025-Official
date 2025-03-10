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
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AprilTagOdometry extends SubsystemBase{
    //creates a singleton for the AprilTagOdometry subsystem
    private static AprilTagOdometry instance;

    public static  AprilTagOdometry getInstance() {
        if (instance == null) {
          instance = new AprilTagOdometry();
        }
        return instance;
        }
        //creates the variables for our objects
    public PhotonCamera frontCam = new PhotonCamera("BackCam");
    // public PhotonCamera backCam = new PhotonCamera("BackCam");
    Transform3d robotToFrontCam = new Transform3d(new Translation3d(0.26035, 0.250825, 0.22225), new Rotation3d(0,0,0)); //Cam mounted facing forward, half a meter forward of center, half a meter up from center.
    AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);

    // Construct PhotonPoseEstimator MULTI_TAG_PNP_ON_COPROCESSOR or CLOSEST_TO_REFERENCE_POSE
    PhotonPoseEstimator photonFrontPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, robotToFrontCam);// private final Controls m_controlsSubsystem = new Controls();

    public AprilTagOdometry(){
        PortForwarder.add(5800, "photon4009.local", 5800);
    }

    public Optional<EstimatedRobotPose> getFrontEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        photonFrontPoseEstimator.setReferencePose(prevEstimatedRobotPose);
        List<PhotonPipelineResult> result = frontCam.getAllUnreadResults();
        if (result.size() == 0){
            return Optional.empty();
        }
        return photonFrontPoseEstimator.update(result.get(0));
    }
    public Optional<Pose3d> getTargetPose(PhotonTrackedTarget target) {
        if (target != null) {
            int fiducialId = target.getFiducialId();
            Optional<Pose3d> tagPose = aprilTagFieldLayout.getTagPose(fiducialId);
            // Ensure the existence of this tag id, print warning
                if (tagPose.isEmpty()) {
                    DriverStation.reportWarning("Fiducial id " + fiducialId + " not recognized", false);
                }
            // Return with optional null value
            return tagPose;
        }
        return null;
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

        if (result.hasTargets()) {
            PhotonTrackedTarget targetID = result.getBestTarget();
            SmartDashboard.putNumber("Target ID", targetID.getFiducialId());
            return targetID;
        }
        return null;
    }

    // Convert an <Optional>Pose3d to a Pose2d
    public static Pose2d convertToPose2d(Optional<Pose3d> optionalPose3d) {
        if (optionalPose3d.isEmpty()) {
            return new Pose2d();
        }

        Pose3d pose3d = optionalPose3d.get();
        double x = pose3d.getX();
        double y = pose3d.getY();
        Rotation2d rotation2d = new Rotation2d(pose3d.getRotation().getZ());

        return new Pose2d(x, y, rotation2d);
    }
    
    @Override
    public void periodic() {
        // uses the getFrontEstimatedGlobalPose to make a pose sample with the swerve drive
        Optional<EstimatedRobotPose> positionSample = getFrontEstimatedGlobalPose(SwerveDrive.getInstance().getPosition());
        if (positionSample.isPresent()) {
            SwerveDrive.getInstance().addVisionMeasurement(
                positionSample.get().estimatedPose.toPose2d(), Timer.getFPGATimestamp()
            );
            System.out.println(positionSample.get().estimatedPose.toPose2d());
        }
    } 
}

//WHY DOES THIS NOT WORKKKKKKKKKKKKKKKKKKKKKK -Tanner