package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;

public class AlgaeManipulatorIntakeCommand extends Command{
    private AlgaeManipulatorSubsystem m_algaeManipulatorSubsystem;
    public AlgaeManipulatorIntakeCommand(AlgaeManipulatorSubsystem AlgaeManipulator) {
    this.m_algaeManipulatorSubsystem = AlgaeManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_algaeManipulatorSubsystem);
  }
    @Override
    public void end(boolean interrupted) {
        m_algaeManipulatorSubsystem.setAlgaeManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        m_algaeManipulatorSubsystem.setAlgaeManipulatorSpeed(1);//TODO: Find out which way is positive and negative
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