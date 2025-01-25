package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FunnelSubsystem;

public class ToggleFunnelCommand extends Command {
private FunnelSubsystem subsystem;
  public ToggleFunnelCommand(FunnelSubsystem subsystem) {
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
        if (subsystem.pistonValue() == false) {
            subsystem.dropPiston(true);
        } else {
            subsystem.dropPiston(false);
        }
        
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
