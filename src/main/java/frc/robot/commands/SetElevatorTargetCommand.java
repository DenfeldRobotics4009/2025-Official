package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;

public class SetElevatorTargetCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final ElevatorSetpoint m_setTarget;
    private final WristAngle m_WristAngle;
    public SetElevatorTargetCommand(ElevatorSubsystem elevator, ElevatorSetpoint setTarget, WristAngle angle){
        m_elevator = elevator;
        m_setTarget = setTarget;
        m_WristAngle = angle;
    }

    @Override
    public void initialize() {
        m_elevator.setElevatorTarget(m_setTarget);
        m_elevator.setWristTarget(m_WristAngle);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
