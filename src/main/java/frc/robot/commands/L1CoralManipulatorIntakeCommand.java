package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.L1CoralManipulatorConstants;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;
import frc.robot.subsystems.L1CoraManipulatorSubsystem.ManipulatorAngle;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;

public class L1CoralManipulatorIntakeCommand extends Command{
    private L1CoraManipulatorSubsystem m_L1CoralManipulatorSubsystem;
    private final ManipulatorAngle m_ManipulatorAngle;
    public L1CoralManipulatorIntakeCommand(L1CoraManipulatorSubsystem l1CoralManipulator, ManipulatorAngle angle) {
    this.m_L1CoralManipulatorSubsystem = l1CoralManipulator;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_L1CoralManipulatorSubsystem);
    m_ManipulatorAngle = angle;
  }
    @Override
    public void end(boolean interrupted) {
        m_L1CoralManipulatorSubsystem.setL1ManipulatorManipulatorSpeed(0);
    }

    @Override
    public void execute() {
        m_L1CoralManipulatorSubsystem.setL1ManipulatorManipulatorSpeed(1);//TODO: Find out which way is positive and negative
    }

    @Override
    public void initialize() {
        m_L1CoralManipulatorSubsystem.setManipulatorTarget(m_ManipulatorAngle);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}