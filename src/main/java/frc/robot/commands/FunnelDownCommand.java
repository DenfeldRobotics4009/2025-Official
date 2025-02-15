package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FunnelSubsystem;

public class FunnelDownCommand extends Command {
private FunnelSubsystem subsystem;
private boolean isDone =false;
  public FunnelDownCommand(FunnelSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    @Override
    public void execute() {
      //when activated makes funnel go up so we can intake
      subsystem.pistonIsPowered(false);
      isDone = true;
    }

    @Override
    public void initialize() {
    
        
    }

    @Override
    public boolean isFinished() {
        return isDone;
    }
    
}
