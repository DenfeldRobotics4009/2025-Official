package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorControllerCommand extends Command{
    //creates an elevator subsystem from the original file
    private final ElevatorSubsystem m_elevator;
    public ElevatorControllerCommand(ElevatorSubsystem elevator){
        m_elevator = elevator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
    }
    @Override
    public void execute() {
        if(m_elevator.getElevatorRelativeEncoderValue() <= 4000 && m_elevator.getElevatorTarget().equals(ElevatorSubsystem.ElevatorSetpoint.ZERO)){
            m_elevator.setWristTarget(ElevatorSubsystem.WristAngle.DOWN);
        }
        //sets the elevator speed with pid and relative encoder values
        double elevatorSpeed = m_elevator.getElevatorPid().calculate(m_elevator.getElevatorRelativeEncoderValue());
        //sets the wrist speed with pid and absolute encoder values
        double wristSpeed = m_elevator.getWristPid().calculate(m_elevator.getWristAbsoluteEncoderValue());
        SmartDashboard.putNumber("elevator speed", elevatorSpeed);
        if(Math.abs(elevatorSpeed) < .005){
            elevatorSpeed = 0+Constants.ElevatorSubsystemConstants.Elevatorf;
        }

        if(m_elevator.getElevatorTarget().equals(ElevatorSubsystem.ElevatorSetpoint.ZERO) && !m_elevator.isAtBottom() && elevatorSpeed > -0.05){
            elevatorSpeed = Constants.ElevatorSubsystemConstants.ElevatorZeroMotorSpeed;
        }
        //checks to see if the elevator is at the bottom when using feedforward so it doesn't constantly run the motors.
        if(!m_elevator.algaecontrolled){
            m_elevator.runElevatorMotor(elevatorSpeed+(m_elevator.isAtBottom() ? 0 : Constants.ElevatorSubsystemConstants.Elevatorf));
        }else{
            
            if(ElevatorSubsystem.getInstance().getWristAbsoluteEncoderValue() >= 0.3) {
                if (ElevatorSubsystem.getInstance().getElevatorRelativeEncoderValue() >= 6750) {
                    ElevatorSubsystem.getInstance().runElevatorMotor(-0.25);
                } else {
                    CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.algaeRemovalMotorSpeed);
                    ElevatorSubsystem.getInstance().runElevatorMotor(1);
                }
            } else {
                CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.algaeRemovalMotorSpeed);
            }
        }
        System.out.println(wristSpeed);
        m_elevator.runWristMotor(wristSpeed);
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void end(boolean interrupted) {
        //sets the elevator and wrist motors to 0 when the command ends
        m_elevator.runElevatorMotor(0);
        m_elevator.runWristMotor(0);
    }
    
}
