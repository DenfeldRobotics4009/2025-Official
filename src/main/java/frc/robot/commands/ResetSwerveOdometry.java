package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;

public class ResetSwerveOdometry extends Command {

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        SwerveDrive.getInstance().zeroHeading();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
