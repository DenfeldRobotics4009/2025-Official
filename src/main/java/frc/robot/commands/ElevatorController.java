package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorController extends Command{
    private final ElevatorSubsystem m_elevator;
    public ElevatorController(ElevatorSubsystem elevator){
        m_elevator = elevator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(elevator);
    }
    @Override
    public void execute() {
        double speed = m_elevator.getPid().calculate(m_elevator.getElevatorEncoder().get());
        m_elevator.getShaftMotor().set(speed);
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
    
}
