package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class SlowCoralManiuplatorOuttakeCommand extends Command {
    private final CoralManipulatorSubsystem subsystem;
    public SlowCoralManiuplatorOuttakeCommand(CoralManipulatorSubsystem subsystem) {
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
            subsystem.coralManipulatorMotorSpeed(Constants.CoralManipulatorConstants.coralManipulatorSlowOuttakeMotorSpeed);
        }else{
            subsystem.coralManipulatorMotorSpeed(Constants.CoralManipulatorConstants.coralP4ManipulatorP4SlowOuttakeMotorSpeed);
            
        }
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.coralManipulatorMotorSpeed(0);
    }
    
    
}
