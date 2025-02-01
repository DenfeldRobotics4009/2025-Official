package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FunnelSubsystem;

public class FunnelUpCommand extends Command {
private FunnelSubsystem subsystem;
    @Override
    public void end(boolean interrupted) {
        
        super.end(interrupted);
    }

    @Override
    public void execute() {
        
        super.execute();
    }

    @Override
    public void initialize() {
        //when activated makes funnel go up so we can intake
        subsystem.dropPiston(false);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }
    
}
