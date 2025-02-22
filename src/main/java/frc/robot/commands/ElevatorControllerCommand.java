package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorControllerCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    public ElevatorControllerCommand(ElevatorSubsystem elevator){
        m_elevator = elevator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
    }
    @Override
    public void execute() {
        if(m_elevator.isAtBottom() && m_elevator.getElevatorTarget().equals(ElevatorSubsystem.ElevatorSetpoint.ZERO)){
            m_elevator.setWristTarget(ElevatorSubsystem.WristAngle.DOWN);
        }
        double elevatorSpeed = m_elevator.getElevatorPid().calculate(m_elevator.getElevatorRelativeEncoderValue());
        double wristSpeed = m_elevator.getWristPid().calculate(m_elevator.getWristAbsoluteEncoderValue());
        SmartDashboard.putNumber("elevator speed", elevatorSpeed);
        if(Math.abs(elevatorSpeed) < .005){
            elevatorSpeed = 0+Constants.ElevatorSubsystemConstants.Elevatorf;
        }

        if(m_elevator.getElevatorTarget().equals(ElevatorSubsystem.ElevatorSetpoint.ZERO) && !m_elevator.isAtBottom() && elevatorSpeed > -0.05){
            elevatorSpeed = -0.05;
        }
        m_elevator.runElevatorMotor(elevatorSpeed+(m_elevator.isAtBottom() ? 0 : Constants.ElevatorSubsystemConstants.Elevatorf));
        m_elevator.runWristMotor(wristSpeed);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        m_elevator.runElevatorMotor(0);
        m_elevator.runWristMotor(0);
    }
    
}
