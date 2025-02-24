package frc.robot;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.field.GameField;
import frc.robot.autos.AutoTest;
import frc.robot.autos.OnePieceCageAuto;
import frc.robot.commands.Autos;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;


public class ShuffleBoard extends SubsystemBase {
  public CoralManipulatorSubsystem m_coralManipulatorSubsystem = CoralManipulatorSubsystem.getInstance();
  public SwerveDrive m_swerveDrive = SwerveDrive.getInstance();
  public FunnelSubsystem m_funnelSubsystem = FunnelSubsystem.getInstance();
  public ElevatorSubsystem m_ElevatorSubsystem = ElevatorSubsystem.getInstance();
  public Field2d m_field = new Field2d();
  public PurePursuitSettings config = new PurePursuitSettings(null, Alliance.Blue);

  public ShuffleBoard() {
  }


  
    @Override
    public void periodic() {
    SmartDashboard.putBoolean("Coral Manipulator laser Tripped", m_coralManipulatorSubsystem.getCoralManipulatorSensor());
    SmartDashboard.putNumber("Manipulator Motor Encoder Value", m_coralManipulatorSubsystem.getManipulatorMotorEncoder());
    SmartDashboard.putNumber("Manipulator Motor Speed", m_coralManipulatorSubsystem.getCoralManipulatorMotorSpeed());

    SmartDashboard.putBoolean("Funnel piston up", m_funnelSubsystem.getFunnelDropPiston());

    SmartDashboard.putNumber("Elevator relative encoder", m_ElevatorSubsystem.getElevatorRelativeEncoderValue());
    SmartDashboard.putNumber("Wrist absolute encoder", m_ElevatorSubsystem.getWristAbsoluteEncoderValue());
    SmartDashboard.putNumber("Wrist motor speed", m_ElevatorSubsystem.wristMotorSpeed());
    SmartDashboard.putNumber("Shaft motor speed", m_ElevatorSubsystem.shaftMotorSpeed());
    SmartDashboard.putBoolean("Elevator at bottom", m_ElevatorSubsystem.isAtBottom());
    
    DROPDOWNVARNAME.addOption("name", new AutoTest(config, Alliance.Blue, m_field));
    
    SmartDashboard.putNumber("Odometry Heading", m_swerveDrive.getHeading());

    SmartDashboard.putData("Field", m_field);
    m_field.setRobotPose(m_swerveDrive.getPosition().getX(),m_swerveDrive.getPosition().getY(),Rotation2d.fromDegrees(m_swerveDrive.getHeading()));
    
  }

}
