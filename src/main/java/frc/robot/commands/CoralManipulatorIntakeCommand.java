package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class CoralManipulatorIntakeCommand extends Command {
  private final CoralManipulatorSubsystem subsystem;
    public CoralManipulatorIntakeCommand(CoralManipulatorSubsystem subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }
    @Override
    public void end(boolean interrupted) {
      //when let go it stops the intake wheel
        subsystem.manipulatorMotorSpeed(0);
    }
    

    @Override
    public void execute() {
        
        super.execute();
    }

    @Override
    public void initialize() {
      //when activated starts spinning the intake wheels
      subsystem.manipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.manipulatorMotorSpeed);
    }

    @Override
    public boolean isFinished() {
      //when lazer sensor in the manipulator is tripped it stops the program
        return !subsystem.getManipulatorSensor();
    }
    
}
