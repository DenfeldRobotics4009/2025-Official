package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.FunnelSubsystem;

public class FunnelDownCommand extends Command {
private FunnelSubsystem m_FunnelSubsystem;
private boolean isDone =false;
  public FunnelDownCommand(FunnelSubsystem m_FunnelSubsystem) {
    this.m_FunnelSubsystem = m_FunnelSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_FunnelSubsystem);
  }

    @Override
    public void execute() {
      //when activated makes funnel go up so we can intake
      m_FunnelSubsystem.pistonIsPowered(false);
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
