package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;

public class L1CoralManipulatorSpeedCommand extends Command{

    private final double speed;
    public L1CoralManipulatorSpeedCommand(double speed) {

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(L1CoraManipulatorSubsystem.getInstance());
    this.speed = speed;
  }
    @Override
    public void end(boolean interrupted) {
        L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(speed);//TODO: Find out which way is positive and negative
    }

    @Override
    public void initialize() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}