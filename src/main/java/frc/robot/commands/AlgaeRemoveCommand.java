package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class AlgaeRemoveCommand extends Command{

    public AlgaeRemoveCommand(){
    }

    @Override
    public void end(boolean interrupted) {
        CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(0);
        ElevatorSubsystem.getInstance().setWristTarget(ElevatorSubsystem.WristAngle.MOVING);
    }

    @Override
    public void execute() {
        CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.algaeRemovalMotorSpeed);
        if(ElevatorSubsystem.getInstance().getElevatorTarget().equals(ElevatorSubsystem.ElevatorSetpoint.P4)) {
            ElevatorSubsystem.getInstance().setWristTarget(ElevatorSubsystem.WristAngle.UP);
        } else {
            ElevatorSubsystem.getInstance().setWristTarget(ElevatorSubsystem.WristAngle.BOTTOMALGAEREMOVAL);
        }
    }

    @Override
    public void initialize() {
       
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
