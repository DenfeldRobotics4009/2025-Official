package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class CoralManipulatorOuttakeCommand extends Command {
    private final CoralManipulatorSubsystem subsystem;
    public CoralManipulatorOuttakeCommand(CoralManipulatorSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    @Override
    public void execute() {
        
        super.execute();
    }

    @Override
    public void initialize() {
        if(ElevatorSubsystem.getInstance().getElevatorTarget() == ElevatorSubsystem.ElevatorSetpoint.P4){
            subsystem.coralManipulatorMotorSpeed(0.9);
        }else{
            subsystem.coralManipulatorMotorSpeed(-0.9);
            
        }
    }

    @Override
    public boolean isFinished() {
      
        return !subsystem.getCoralManipulatorSensor();
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.coralManipulatorMotorSpeed(0);
    }
    
    
}
