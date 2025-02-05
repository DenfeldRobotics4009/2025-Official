package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AprilTagOdometry extends SubsystemBase{
    public PhotonCamera frontCam = new PhotonCamera("FrontCam");
    public PhotonCamera backCam = new PhotonCamera("BackCam");
    public AprilTagOdometry(){
    }


    public Pose2d seeAprilTags(){
        PhotonPipelineResult readings = getCameraReadings();
        PhotonPipelineResult relativeTOCamera = processReadings(readings);
        Pose2d pos = getPosition(relativeToCamera);
        return pos;
    }

    private Pose2d getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }


    private void processReadings(PhotonPipelineResult result) {
        boolean hasTargets = result.hasTargets();
        List<PhotonTrackedTarget> targets = result.getTargets();
        PhotonTrackedTarget target = result.getBestTarget();

        double yaw = target.getYaw();
        double pitch = target.getPitch();
        double area = target.getArea();
        double skew = target.getSkew();
        Transform3d pose = target.getBestCameraToTarget();
    }


    private PhotonPipelineResult getCameraReadings(){
        PhotonPipelineResult result = frontCam.getLatestResult();
        return result;
    }
}
