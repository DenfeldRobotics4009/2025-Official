package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.L1CoralManipulatorSubsystem;

public class L1CoralManipulatorIntakeCommand extends Command{private L1CoralManipulatorSubsystem m_l1CoralManipulatorSubsystem;
    public L1CoralManipulatorIntakeCommand(L1CoralManipulatorSubsystem l1CoralManipulator) {
    this.m_l1CoralManipulatorSubsystem = l1CoralManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_l1CoralManipulatorSubsystem);
  }
    @Override
    public void end(boolean interrupted) {
        m_l1CoralManipulatorSubsystem.setAlgaeManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        m_l1CoralManipulatorSubsystem.setAlgaeManipulatorSpeed(1);//TODO: Find out which way is positive and negative
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