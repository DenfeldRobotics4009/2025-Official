package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;


public class Elastic extends SubsystemBase {

  private final ManipulatorSubsystem m_manipulatorSubsystem;
    public Elastic(ManipulatorSubsystem subsystem) {
    this.m_manipulatorSubsystem = subsystem;
    }
    
    // private final SwerveDrive m_swerveDrive;
    // public Elastic(SwerveDrive subsystem) {
    // this.m_swerveDrive = subsystem;
    // }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Manipulator Motor Tripped", m_manipulatorSubsystem.getShortFunnelSensor());
        SmartDashboard.putNumber("Manipulator Motor Encoder Value", m_manipulatorSubsystem.getManipulatorMotorEncoder());
        SmartDashboard.putNumber("Odometry X", SwerveDrive.getInstance().getPosition().getX());
        SmartDashboard.putNumber("Odometry Y", SwerveDrive.getInstance().getPosition().getY());
        SmartDashboard.putNumber("Odometry Heading", SwerveDrive.getInstance().getHeading());
 System.out.println("elastic periodic running");
    }

}
