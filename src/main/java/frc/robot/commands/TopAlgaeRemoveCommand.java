package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class TopAlgaeRemoveCommand extends Command {
    private ElevatorSubsystem elevatorSubsystem;
    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(0);
        ElevatorSubsystem.getInstance().setWristTarget(ElevatorSubsystem.WristAngle.MOVING);
        
    }

    @Override
    public void execute() {
        CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(-1);
        elevatorSubsystem.setWristTarget(ElevatorSubsystem.WristAngle.TOPALGAEREMOVAL);
    }

    @Override
    public void initialize() {
       
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }
}
