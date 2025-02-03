package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AprilTagOdometry extends SubsystemBase{
    public PhotonCamera frontCam = new PhotonCamera("FrontCam");
    public PhotonCamera backCam = new PhotonCamera("BackCam");
    public AprilTagOdometry(){

    }
}
