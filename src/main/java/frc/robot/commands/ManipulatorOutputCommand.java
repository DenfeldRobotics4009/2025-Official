package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ManipulatorSubsystem;

public class ManipulatorOutputCommand extends Command {
    private final ManipulatorSubsystem subsystem;
    public ManipulatorOutputCommand(ManipulatorSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    @Override
    public void execute() {
        
        super.execute();
    }

    @Override
    public void initialize() {
        subsystem.manipulatorMotorSpeed(-0.9);
    }

    @Override
    public boolean isFinished() {
      
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.manipulatorMotorSpeed(0);
    }
    
    
}
