package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;

public class DriveForwardCommand extends Command{
    private double xSpeed = 1;
    private double ySpeed = 0;
    private double rot = 0;
    private boolean fieldRelative = true;

    public DriveForwardCommand(){
        
    }

    @Override
    public void end(boolean interrupted) {
        SwerveDrive.getInstance().drive(0, 0, 0, true);
    }

    @Override
    public void execute() {
        SwerveDrive.getInstance().drive(xSpeed, ySpeed, rot, fieldRelative);
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
