package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ManipulatorSubsystem;

public class Elastic {
    public void periodic(){
        SmartDashboard.putBoolean("Manipulator Motor Tripped", ManipulatorSubsystem.getInstance().getShortFunnelSensor());
    }
}
