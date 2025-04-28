package frc.robot.commands;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;

public class NetElevatorControllerCommand extends Command{
    private final TrapezoidProfile m_profile =
    new TrapezoidProfile(new TrapezoidProfile.Constraints(1, 0.25));
    private TrapezoidProfile.State m_goal = new TrapezoidProfile.State(1, 0);
    private TrapezoidProfile.State m_startpoint = new TrapezoidProfile.State();

    //creates an elevator subsystem from the original file
    private final ElevatorSubsystem m_elevator;
    public NetElevatorControllerCommand(ElevatorSubsystem elevator){
        m_elevator = elevator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
    }
    @Override
    public void execute() {
        m_profile.calculate(0, m_startpoint, m_goal);
        //sets the elevator speed with pid and relative encoder values
        double elevatorSpeed = m_elevator.getElevatorPid().calculate(m_elevator.getElevatorRelativeEncoderValue(), m_profile.timeLeftUntil(0));
        //sets the wrist speed with pid and absolute encoder values
        double wristSpeed = m_elevator.getWristPid().calculate(m_elevator.getWristAbsoluteEncoderValue());

        //checks to see if the elevator is at the bottom when using feedforward so it doesn't constantly run the motors.
        m_elevator.runElevatorMotor(elevatorSpeed+(m_elevator.isAtBottom() ? 0 : Constants.ElevatorSubsystemConstants.Elevatorf));
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
