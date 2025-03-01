package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;

public class DriveStopCommand extends Command{

    public DriveStopCommand(){
        
    }

    @Override
    public void end(boolean interrupted) {
        SwerveDrive.getInstance().drive(0, 0, 0, false);
    }

    @Override
    public void execute() {
        SwerveDrive.getInstance().drive(0, 0, 0, false);
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
