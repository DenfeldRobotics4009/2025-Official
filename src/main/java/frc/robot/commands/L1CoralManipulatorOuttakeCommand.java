package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.L1CoralManipulatorSubsystem;

public class L1CoralManipulatorOuttakeCommand extends Command{
private L1CoralManipulatorSubsystem m_AlgaeManipulatorSubsystem;
public L1CoralManipulatorOuttakeCommand(L1CoralManipulatorSubsystem algaeManipulator) {
    this.m_AlgaeManipulatorSubsystem = algaeManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_AlgaeManipulatorSubsystem);
  }
    @Override
    public void end(boolean interrupted) {
        m_AlgaeManipulatorSubsystem.setAlgaeManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        m_AlgaeManipulatorSubsystem.setAlgaeManipulatorSpeed(-1);//TODO: Find out which way is positive and negative
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