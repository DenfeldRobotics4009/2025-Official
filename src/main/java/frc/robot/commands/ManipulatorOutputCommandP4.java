package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ManipulatorSubsystem;

public class ManipulatorOutputCommandP4 extends Command {
    private final ManipulatorSubsystem subsystem;
    public ManipulatorOutputCommandP4(ManipulatorSubsystem subsystem) {
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
        subsystem.manipulatorMotorSpeed(-Constants.ManipulatorCommandConstants.manipulatorMotorSpeed);
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
