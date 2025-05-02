package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;
import edu.wpi.first.wpilibj.Timer;

public class ShootAlgaeCommand extends Command{
    //creates an elevator subsystem from the original file
    private final ElevatorSubsystem m_elevator;
    public ShootAlgaeCommand(ElevatorSubsystem elevator){
        m_elevator = elevator;
        addRequirements(CoralManipulatorSubsystem.getInstance());
    // Use addRequirements() here to declare subsystem dependencies.
    }
    @Override
    public void execute() {

        if (ElevatorSubsystem.getInstance().getElevatorRelativeEncoderValue() >= 8750) {
            CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorConstants.coralManipulatorOuttakeMotorSpeed);
        } else {
            CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.algaeRemovalMotorSpeed);
        }

    }

    @Override
    public void initialize() {
        ElevatorSubsystem.getInstance().setElevatorTarget(ElevatorSetpoint.NET);
        ElevatorSubsystem.getInstance().setWristTarget(WristAngle.NET);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    @Override
    public void end(boolean interrupted) {
        //sets the elevator and wrist motors to 0 when the command ends
        m_elevator.runElevatorMotor(0);
        m_elevator.runWristMotor(0);
        CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(0);
    }
    
}
