package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ManipulatorSubsystem;


public class Elastic extends SubsystemBase {

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Manipulator Motor Tripped", ManipulatorSubsystem.getInstance().getShortFunnelSensor());
    }
  
}
