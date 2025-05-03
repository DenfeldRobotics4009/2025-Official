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
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
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
    private Matrix<N3, N1> curStdDevs;

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
       
        var estimatedRobotPose = photonFrontPoseEstimator.update(result.get(0));
        updateEstimationStdDevs(estimatedRobotPose, result.get(0).getTargets());
        return estimatedRobotPose;
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
    public int bestTarget() {
        PhotonPipelineResult result = frontCam.getLatestResult();

        if (result.hasTargets()) {
            int targetID = result.getBestTarget().getFiducialId();
            SmartDashboard.putNumber("Target ID", targetID);
            return targetID;
        }
        return 0;
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
                positionSample.get().estimatedPose.toPose2d(), Timer.getFPGATimestamp(), getEstimationStdDevs()
            );

            // System.out.println(positionSample.get().estimatedPose.toPose2d());
        }
    } 

     /**
     * Calculates new standard deviations This algorithm is a heuristic that creates dynamic standard
     * deviations based on number of tags, estimation strategy, and distance from the tags.
     *
     * @param estimatedPose The estimated pose to guess standard deviations for.
     * @param targets All targets in this camera frame
     */
    private void updateEstimationStdDevs(
            Optional<EstimatedRobotPose> estimatedPose, List<PhotonTrackedTarget> targets) {
        if (estimatedPose.isEmpty()) {
            // No pose input. Default to single-tag std devs
            curStdDevs = Constants.AprilTagOdometryConstants.kSingleTagStdDevs;

        } else {
            // Pose present. Start running Heuristic
            var estStdDevs = Constants.AprilTagOdometryConstants.kSingleTagStdDevs;
            int numTags = 0;
            double avgDist = 0;

            // Precalculation - see how many tags we found, and calculate an average-distance metric
            for (var tgt : targets) {
                var tagPose = photonFrontPoseEstimator.getFieldTags().getTagPose(tgt.getFiducialId());
                if (tagPose.isEmpty()) continue;
                numTags++;
                avgDist +=
                        tagPose
                                .get()
                                .toPose2d()
                                .getTranslation()
                                .getDistance(estimatedPose.get().estimatedPose.toPose2d().getTranslation());
            }

            if (numTags == 0) {
                // No tags visible. Default to single-tag std devs
                curStdDevs = Constants.AprilTagOdometryConstants.kSingleTagStdDevs;
            } else {
                // One or more tags visible, run the full heuristic.
                avgDist /= numTags;
                // Decrease std devs if multiple targets are visible
                if (numTags > 1) estStdDevs = Constants.AprilTagOdometryConstants.kMultiTagStdDevs;
                // Increase std devs based on (average) distance
                if (numTags == 1 && avgDist > 4)
                    estStdDevs = VecBuilder.fill(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
                else estStdDevs = estStdDevs.times(1 + (avgDist * avgDist / 30));
                double confidence = .5;
                estStdDevs = estStdDevs.times(confidence);
                curStdDevs = estStdDevs;
            }
        }
    }

    /**
     * Returns the latest standard deviations of the estimated pose from {@link
     * #getEstimatedGlobalPose()}, for use with {@link
     * edu.wpi.first.math.estimator.SwerveDrivePoseEstimator SwerveDrivePoseEstimator}. This should
     * only be used when there are targets visible.
     */
    public Matrix<N3, N1> getEstimationStdDevs() {
        return curStdDevs;
    }
}

//WHY DOES THIS NOT WORKKKKKKKKKKKKKKKKKKKKKK -Tanner