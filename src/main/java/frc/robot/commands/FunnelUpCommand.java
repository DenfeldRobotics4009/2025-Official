package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.FunnelSubsystem;

public class FunnelUpCommand extends Command {
private FunnelSubsystem subsystem;
private boolean isDone = false;
public FunnelUpCommand(FunnelSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    @Override
    public void end(boolean interrupted) {
        subsystem.setFunnelMotorSpeed(0);
        super.end(interrupted);
    }

    @Override
    public void execute() {
          //when activated makes funnel go up so we can intake
        subsystem.pistonIsPowered(true);
        isDone = true;
    }

    @Override
    public void initialize() {
      
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return isDone;
    }
    
}
