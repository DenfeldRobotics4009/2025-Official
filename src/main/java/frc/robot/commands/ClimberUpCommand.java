package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;


public class ClimberUpCommand extends Command{
    private ClimberSubsystem climberSubsystem;
    public ClimberUpCommand(ClimberSubsystem climber, double speed){
        climberSubsystem = climber;
    }
    @Override
    public void end(boolean interrupted) {
        climberSubsystem.winchMotorOff();
    }
    @Override
    public void initialize() {
        climberSubsystem.moveClimberUp();
    }
    @Override
    public boolean isFinished() {
    
        return true;
    }
    @Override
    public void execute() {

    }

}
