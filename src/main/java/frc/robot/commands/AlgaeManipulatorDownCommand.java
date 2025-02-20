package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;

public class AlgaeManipulatorDownCommand extends Command{
private AlgaeManipulatorSubsystem m_AlgaeManipulatorSubsystem;
    public AlgaeManipulatorDownCommand(AlgaeManipulatorSubsystem algaeManipulator) {
    this.m_AlgaeManipulatorSubsystem = algaeManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_AlgaeManipulatorSubsystem);
  }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public void execute() {
        m_AlgaeManipulatorSubsystem.algaePistonIsDeployed(false);
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
