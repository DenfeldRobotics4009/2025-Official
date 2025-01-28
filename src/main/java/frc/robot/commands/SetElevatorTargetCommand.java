package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.setpoint;

public class SetElevatorTargetCommand extends Command{
    private final ElevatorSubsystem m_elevator;
    private final setpoint m_setTarget;
    public SetElevatorTargetCommand(ElevatorSubsystem elevator, setpoint setTarget){
        m_elevator = elevator;
        m_setTarget = setTarget;
    }

    @Override
    public void initialize() {
        m_elevator.setTarget(m_setTarget);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
