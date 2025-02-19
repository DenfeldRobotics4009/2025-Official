// package frc.robot.subsystems;

// import java.util.List;

// import org.photonvision.PhotonCamera;
// import org.photonvision.targeting.PhotonPipelineResult;
// import org.photonvision.targeting.PhotonTrackedTarget;
// import org.photonvision.targeting.TargetCorner;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

// public class AprilTagOdometry extends SubsystemBase{
//     public PhotonCamera frontCam = new PhotonCamera("FrontCam");
//     public PhotonCamera backCam = new PhotonCamera("BackCam");
//     // private final Controls m_controlsSubsystem = new Controls();
//     public AprilTagOdometry(){
//     }


//     public Pose2d seeAprilTags(){
//         PhotonPipelineResult readings = getCameraReadings();
//         PhotonPipelineResult relativeTOCamera = processReadings();
//         Pose2d pos = getPosition(relativeToCamera);
//         return pos;
//     }

//     private Pose2d getPosition() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
//     }


//     private void processReadings(PhotonPipelineResult result) {
//         boolean hasTargets = result.hasTargets();
//         List<PhotonTrackedTarget> targets = result.getTargets();
//         PhotonTrackedTarget target = result.getBestTarget();

//         double yaw = target.getYaw();
//         double pitch = target.getPitch();
//         double area = target.getArea();
//         double skew = target.getSkew();
//         Transform3d pose = target.getBestCameraToTarget();
//     }


//     private PhotonPipelineResult getCameraReadings(){
//         PhotonPipelineResult result = frontCam.getLatestResult();
//         return result;
//     }


//     // @Override
//     // public void periodic() {
//     //     // Calculate drivetrain commands from Joystick values

//     //     double forward = -m_controlsSubsystem.driveController.getLeftY() * Constants.DriveConstants.kMaxSpeedMetersPerSecond;

//     //     double strafe = -m_controlsSubsystem.driveController.getLeftX() * Constants.DriveConstants.kMaxSpeedMetersPerSecond;

//     //     double turn = -m_controlsSubsystem.driveController.getRightX() * Constants.DriveConstants.kMaxAngularSpeed;
        
//     //     // Read in relevant data from the Camera

//     //     boolean targetVisible = false;

//     //     double targetYaw = 0.0;

//     //     var results = frontCam.getAllUnreadResults();

//     //     if (!results.isEmpty()) {

//     //         // Camera processed a new frame since last

//     //         // Get the last one in the list.

//     //         var result = results.get(results.size() - 1);

//     //         if (result.hasTargets()) {

//     //             // At least one AprilTag was seen by the camera

//     //             for (var target : result.getTargets()) {

//     //                 if (target.getFiducialId() == 7) {

//     //                     // Found Tag 7, record its information

//     //                     targetYaw = target.getYaw();

//     //                     targetVisible = true;

//     //                 }

//     //             }

//     //         }

//     //     }
//     //     // Auto-align when requested

//     //     if (m_controlsSubsystem.driveController.getBButton() && targetVisible) {

//     //         // Driver wants auto-alignment to tag 7

//     //         // And, tag 7 is in sight, so we can turn toward it.

//     //         // Override the driver's turn command with an automatic one that turns toward the tag.

//     //         turn = -1.0 * targetYaw * VISION_TURN_kP * Constants.DriveConstants.kMaxAngularSpeed;

//     //     }
//     //     // Command drivetrain motors based on target speeds

//     //     drivetrain.drive(forward, strafe, turn);


//     //     // Put debug information to the dashboard

//     //     SmartDashboard.putBoolean("Vision Target Visible", targetVisible);
//     // }
// }
