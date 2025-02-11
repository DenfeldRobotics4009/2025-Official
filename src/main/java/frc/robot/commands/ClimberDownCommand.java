package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberDownCommand extends Command{
    private ClimberSubsystem climberSubsystem;
    public ClimberDownCommand(ClimberSubsystem climber){
        climberSubsystem = climber;
    }
    @Override
    public void end(boolean interrupted) {
        climberSubsystem.winchMotorOff();
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public boolean isFinished() {
        return climberSubsystem.getDownLimitSwitchHit();
    }
    @Override
    public void execute() {
        climberSubsystem.moveClimberDown();
        climberSubsystem.pistonIsPowered(false);
    }
}
