package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
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
       //ends when below max angle
        return (subsystem.getFunnelPos() > Constants.FunnelConstants.maxFunnelAngle);
    }

    @Override
    public void end(boolean interrupted) {
     subsystem.setFunnelMotorSpeed(0);
    }
    
}
