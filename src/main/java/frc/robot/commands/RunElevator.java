package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class RunElevator extends Command{
    private final ElevatorSubsystem m_elevator;
    public RunElevator(ElevatorSubsystem elevator){
        m_elevator = elevator;
    }
    @Override
    public void end(boolean interrupted) {
        m_elevator.runMotor(0);
    }
    @Override
    public void execute() {
        m_elevator.runMotor(1);
    }
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }
    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
}
