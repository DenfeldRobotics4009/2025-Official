package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

public class ShootAlgaeCommand extends Command{
    //creates an elevator subsystem from the original file
    private final ElevatorSubsystem m_elevator;

    // Max velocity is 100 encoder ticks per second
    // Max acceleration is 100 encoder ticks per second
    ProfiledPIDController controller = new ProfiledPIDController(
  0.007, 0, 0.0001,
  new TrapezoidProfile.Constraints(20000, 40000));

    public ShootAlgaeCommand(ElevatorSubsystem elevator){
        m_elevator = elevator;
        addRequirements(CoralManipulatorSubsystem.getInstance(), ElevatorSubsystem.getInstance());
    // Use addRequirements() here to declare subsystem dependencies.
    }
    @Override
    public void execute() {

        //sets the wrist speed with pid and absolute encoder values
        double wristSpeed = m_elevator.getWristPid().calculate(m_elevator.getWristAbsoluteEncoderValue());
        m_elevator.runWristMotor(wristSpeed);

        if (ElevatorSubsystem.getInstance().getElevatorRelativeEncoderValue() >= 8750) {
            CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorConstants.coralManipulatorOuttakeMotorSpeed);
        } else {
            CoralManipulatorSubsystem.getInstance().coralManipulatorMotorSpeed(Constants.CoralManipulatorCommandConstants.algaeRemovalMotorSpeed);
        }

        double elevatorSpeed = controller.calculate(m_elevator.getElevatorRelativeEncoderValue(), 6000);
        m_elevator.runElevatorMotor(elevatorSpeed);
        SmartDashboard.putNumber("Elevator Speed", elevatorSpeed);

    }

    @Override
    public void initialize() {
        // ElevatorSubsystem.getInstance().setElevatorTarget(ElevatorSetpoint.NET);
        ElevatorSubsystem.getInstance().setWristTarget(WristAngle.NET);
        controller.reset(ElevatorSubsystem.getInstance().getElevatorRelativeEncoderValue());
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
        ElevatorSubsystem.getInstance().setElevatorTarget(ElevatorSetpoint.P2);
    }
    
}
