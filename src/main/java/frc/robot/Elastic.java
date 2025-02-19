package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;


public class Elastic extends SubsystemBase {



  public ManipulatorSubsystem m_manipulatorSubsystem;
  public Elastic(ManipulatorSubsystem subsystem) {
    this.m_manipulatorSubsystem = subsystem;
  }
    
  public SwerveDrive m_swerveDrive;
  public Elastic(SwerveDrive subsystem) {
    this.m_swerveDrive = subsystem;
  }

  public FunnelSubsystem m_funnelSubsystem;
  public Elastic(FunnelSubsystem subsystem) {
    this.m_funnelSubsystem = subsystem;
  }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Manipulator Motor Tripped", m_manipulatorSubsystem.getShortFunnelSensor());
        SmartDashboard.putNumber("Manipulator Motor Encoder Value", m_manipulatorSubsystem.getManipulatorMotorEncoder());
        SmartDashboard.putNumber("Manipulator Motor Speed", m_manipulatorSubsystem.getManipulatorMotorSpeed());

        SmartDashboard.putBoolean("Funnel Sensor Tripped", m_funnelSubsystem.getWideFunnelSensor());
        SmartDashboard.putBoolean("Drop piston up", m_funnelSubsystem.getFunnelDropPiston());

        SmartDashboard.putNumber("Odometry X", m_swerveDrive.getPosition().getX());
        SmartDashboard.putNumber("Odometry Y", m_swerveDrive.getPosition().getY());
        SmartDashboard.putNumber("Odometry Heading", m_swerveDrive.getHeading());
 System.out.println("elastic periodic running");
    }

}
