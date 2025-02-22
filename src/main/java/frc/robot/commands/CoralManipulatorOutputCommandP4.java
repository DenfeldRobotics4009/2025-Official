package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class CoralManipulatorOutputCommandP4 extends Command {
    private final CoralManipulatorSubsystem subsystem;
    public CoralManipulatorOutputCommandP4(CoralManipulatorSubsystem subsystem) {
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
        subsystem.coralManipulatorMotorSpeed(-Constants.CoralManipulatorCommandConstants.coralManipulatorMotorSpeed);
    }

    @Override
    public boolean isFinished() {
      
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.coralManipulatorMotorSpeed(0);
    }
    
    
}
