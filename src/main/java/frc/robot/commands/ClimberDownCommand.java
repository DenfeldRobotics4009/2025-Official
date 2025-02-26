package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class ClimberDownCommand extends Command{
    //creates a climber subsystem from the original subsystem file
    private ClimberSubsystem climberSubsystem;
    public ClimberDownCommand(ClimberSubsystem climber){
        climberSubsystem = climber;
    }
    @Override
    public void end(boolean interrupted) {
        //climber turns off when the command ends
        climberSubsystem.winchMotorOff();
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public boolean isFinished() {
        return false; // climberSubsystem.getDownLimitSwitchHit();
    }
    @Override
    public void execute() {
        //move the climber down when command is scheduled
        climberSubsystem.moveClimberDown();
        ElevatorSubsystem.getInstance().setElevatorTarget(ElevatorSubsystem.ElevatorSetpoint.LOW_ALGAE);
        //climberSubsystem.pistonIsPowered(false);
    }
}
