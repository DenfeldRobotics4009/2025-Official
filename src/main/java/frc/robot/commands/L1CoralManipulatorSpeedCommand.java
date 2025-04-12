package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Controls;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;
import frc.robot.subsystems.L1CoraManipulatorSubsystem.ManipulatorAngle;

public class L1CoralManipulatorSpeedCommand extends Command{

    private final Controls controls;
    private Timer timer = new Timer();
    public L1CoralManipulatorSpeedCommand(Controls controls) {

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(L1CoraManipulatorSubsystem.getInstance());
    this.controls = controls;
  }
    @Override
    public void end(boolean interrupted) {
        L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(0);
        L1CoraManipulatorSubsystem.getInstance().setManipulatorTarget(ManipulatorAngle.UP);
    }

    @Override
    public void execute() {
         if(controls.operateController.getRightTriggerAxis() > 0.1){
            L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(Constants.L1CoralManipulatorConstants.outakeSpeed);
        }
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        L1CoraManipulatorSubsystem.getInstance().setManipulatorTarget(ManipulatorAngle.OUTTAKE);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}