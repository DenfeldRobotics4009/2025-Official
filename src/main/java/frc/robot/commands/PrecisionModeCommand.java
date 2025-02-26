package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;

public class PrecisionModeCommand extends Command{

    public PrecisionModeCommand(){

    }

    @Override
    public void end(boolean interrupted) {
        SwerveDrive.getInstance().setPrecisionMode(false);
    }

    @Override
    public void execute() {
        SwerveDrive.getInstance().setPrecisionMode(true);
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
