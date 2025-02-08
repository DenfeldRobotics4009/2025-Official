package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FunnelSubsystem;

public class FunnelDownCommand extends Command {
private FunnelSubsystem subsystem;
  public FunnelDownCommand(FunnelSubsystem subsystem) {
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
        //when activated makes funnel drop so we can climb
        subsystem.funnelDropMotorDown();
        
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}
