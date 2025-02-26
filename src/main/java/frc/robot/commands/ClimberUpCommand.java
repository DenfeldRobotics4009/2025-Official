package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ClimberSubsystem;


public class ClimberUpCommand extends Command{
    //creates a type of climber subsystem to use in this command from the climber subsystem file
    private ClimberSubsystem climberSubsystem;
    public ClimberUpCommand(ClimberSubsystem climber){
        climberSubsystem = climber;
    }
    @Override
    public void end(boolean interrupted) {
        //turns motor off when the command ends
        climberSubsystem.winchMotorOff();
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public boolean isFinished() {
        return false;// climberSubsystem.getRelativeEncoderValue()>Constants.ClimberSubsystemConstants.climberUpEncoderVal;
    }
    @Override
    public void execute() {
        //when the command is scheduled, move the climber up
        climberSubsystem.moveClimberUp();
        //climberSubsystem.pistonIsPowered(true);
    }

}
