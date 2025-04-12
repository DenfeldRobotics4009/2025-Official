// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;
import frc.robot.subsystems.L1CoraManipulatorSubsystem.ManipulatorAngle;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class L1IntakeCommand extends Command {

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public L1IntakeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(L1CoraManipulatorSubsystem.getInstance());
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    L1CoraManipulatorSubsystem.getInstance().setManipulatorTarget(ManipulatorAngle.DOWN);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(Constants.L1CoralManipulatorConstants.intakeSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    L1CoraManipulatorSubsystem.getInstance().setL1ManipulatorManipulatorSpeed(0);
    L1CoraManipulatorSubsystem.getInstance().setManipulatorTarget(ManipulatorAngle.UP);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
