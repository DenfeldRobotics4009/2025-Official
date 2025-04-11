package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;
import frc.robot.subsystems.L1CoraManipulatorSubsystem.ManipulatorAngle;

public class L1CoralManipulatorSpeedCommand extends Command{

    private final double speed;
    private Timer timer = new Timer();
    public L1CoralManipulatorSpeedCommand(double speed) {

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(L1CoraManipulatorSubsystem.getInstance());
    this.speed = speed;
  }
    @Override
    public void end(boolean interrupted) {
        L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        L1CoraManipulatorSubsystem.getInstance().setManipulatorTarget(ManipulatorAngle.OUTTAKE);
        if(L1CoraManipulatorSubsystem.getInstance().getManipulatorPid().atSetpoint() || timer.hasElapsed(0.5)){
            L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(Constants.L1CoralManipulatorConstants.outakeSpeed);
        }
    
        double wristSpeed = L1CoraManipulatorSubsystem.getInstance().getManipulatorPid().calculate(L1CoraManipulatorSubsystem.getInstance().getManipulatorAbsoluteEncoderValue());
        L1CoraManipulatorSubsystem.getInstance().setL1ManipulatoRotationSpeed(wristSpeed);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}