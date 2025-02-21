package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;

public class ToggleAlgaeManipulatorCommand extends Command{
private AlgaeManipulatorSubsystem m_algaeManipulatorSubsystem;
    public ToggleAlgaeManipulatorCommand(AlgaeManipulatorSubsystem algaeManipulator) {
    this.m_algaeManipulatorSubsystem = algaeManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_algaeManipulatorSubsystem);
  }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public void execute() {
        if(m_algaeManipulatorSubsystem.isPistonDeployed()){
            m_algaeManipulatorSubsystem.setAlgaePiston(false);
        } else{
            m_algaeManipulatorSubsystem.setAlgaePiston(true);;
        }
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
