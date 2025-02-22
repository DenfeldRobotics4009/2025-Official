package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorOffset extends Command{
    private final ElevatorSubsystem m_elevator;
    private double offset;
    private boolean isDone = false;
    public SetElevatorOffset(ElevatorSubsystem elevator, double offset){
        m_elevator = elevator;
        this.offset = offset;
    }
    @Override
    public void end(boolean interrupted) {
    }
    @Override
    public void execute() {
        m_elevator.setOffset(m_elevator.getOffset() + offset);
        isDone = true;
    }
    @Override
    public void initialize() {
    }
    @Override
    public boolean isFinished() {
      
            return isDone;
        
    }
}
