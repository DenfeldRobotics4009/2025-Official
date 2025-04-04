package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.L1CoralManipulatorSubsystem;

public class ToggleL1CoralManipulatorCommand extends Command{
private L1CoralManipulatorSubsystem m_l1CoralManipulatorSubsystem;
    public ToggleL1CoralManipulatorCommand(L1CoralManipulatorSubsystem algaeManipulator) {
    this.m_l1CoralManipulatorSubsystem = algaeManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_l1CoralManipulatorSubsystem);
  }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public void execute() {
        if(m_l1CoralManipulatorSubsystem.isPistonDeployed()){
            m_l1CoralManipulatorSubsystem.setAlgaePiston(false);
        } else{
            m_l1CoralManipulatorSubsystem.setAlgaePiston(true);;
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