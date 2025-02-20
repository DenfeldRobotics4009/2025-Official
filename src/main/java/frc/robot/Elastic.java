package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;


public class Elastic extends SubsystemBase {
  public ManipulatorSubsystem m_manipulatorSubsystem = ManipulatorSubsystem.getInstance();
  public SwerveDrive m_swerveDrive = SwerveDrive.getInstance();
  public FunnelSubsystem m_funnelSubsystem = FunnelSubsystem.getInstance();

  public Elastic() {
  }


  
    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Manipulator laser Tripped", m_manipulatorSubsystem.getShortFunnelSensor());
        SmartDashboard.putNumber("Manipulator Motor Encoder Value", m_manipulatorSubsystem.getManipulatorMotorEncoder());
        SmartDashboard.putNumber("Manipulator Motor Speed", m_manipulatorSubsystem.getManipulatorMotorSpeed());

        SmartDashboard.putBoolean("Drop piston up", m_funnelSubsystem.getFunnelDropPiston());

        // SmartDashboard.putNumber("Elevator relative encoder", m_ElevatorSubsystem.getElevatorRelativeEncoderValue());
        // SmartDashboard.putNumber("Wrist absolute encoder", m_ElevatorSubsystem.getWristAbsoluteEncoderValue());
        // SmartDashboard.putNumber("Wrist motor speed", m_ElevatorSubsystem.wristMotorSpeed());
        // SmartDashboard.putNumber("Shaft motor speed", m_ElevatorSubsystem.shaftMotorSpeed());
        // SmartDashboard.putBoolean("Elevator at bottom", m_ElevatorSubsystem.isAtBottom());

        SmartDashboard.putNumber("Odometry X", m_swerveDrive.getPosition().getX());
        SmartDashboard.putNumber("Odometry Y", m_swerveDrive.getPosition().getY());
        SmartDashboard.putNumber("Odometry Heading", m_swerveDrive.getHeading());
 System.out.println("elastic periodic running");
    }

}
