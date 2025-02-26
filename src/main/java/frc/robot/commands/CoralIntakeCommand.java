package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class CoralIntakeCommand extends Command {
  private final CoralManipulatorSubsystem subsystem;
    public CoralIntakeCommand(CoralManipulatorSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }
    @Override
    public void end(boolean interrupted) {
      //when let go it stops the intake wheel
        subsystem.coralManipulatorMotorSpeed(0);
    }
    

    @Override
    public void execute() {
        //when activated starts spinning the intake wheels
      subsystem.coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.coralManipulatorIntakeMotorSpeed);
    }

    @Override
    public void initialize() {
      
    }

    @Override
    public boolean isFinished() {
      //when lazer sensor in the manipulator is tripped it stops the program
        return subsystem.getCoralManipulatorSensor();
    }
    
}
