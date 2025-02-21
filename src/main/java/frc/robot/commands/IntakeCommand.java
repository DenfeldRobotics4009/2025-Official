package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ManipulatorSubsystem;

public class IntakeCommand extends Command {
  private final ManipulatorSubsystem subsystem;
    public IntakeCommand(ManipulatorSubsystem subsystem) {
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
        //when activated starts spinning the intake wheels
      subsystem.manipulatorMotorSpeed(Constants.ManipulatorCommandConstants.manipulatorMotorSpeed);
    }

    @Override
    public void initialize() {
      
    }

    @Override
    public boolean isFinished() {
      //when lazer sensor in the manipulator is tripped it stops the program
        return !subsystem.getShortFunnelSensor();
    }
    
}
