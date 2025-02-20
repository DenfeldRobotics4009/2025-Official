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
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AprilTagOdometry extends SubsystemBase{
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
