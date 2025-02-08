package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class RunElevator extends Command{
    private final ElevatorSubsystem m_elevator;
        private double m_speed;
        public RunElevator(ElevatorSubsystem elevator, double speed){
            m_elevator = elevator;
            m_speed = speed;
    }
    @Override
    public void end(boolean interrupted) {
        m_elevator.runMotor(0);
    }
    @Override
    public void execute() {
        if (m_speed > 0 || !m_elevator.isAtBottom()){
            
        
        } else {
            m_elevator.runMotor(0);
        }
    }
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }
    @Override
    public boolean isFinished() {
      
            return false;
        
    }
}
