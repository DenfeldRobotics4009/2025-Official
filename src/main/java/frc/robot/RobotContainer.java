// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ManipulatorOutputCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.commands.FunnelDownCommand;
import frc.robot.commands.FunnelUpCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.Controls;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.setpoint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.io.IOException;
import java.util.List;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.field.FieldMirrorType;
import frc.library.auto.pathing.field.GameField;  

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
    private final SwerveDrive m_robotDrive = SwerveDrive.getInstance();
    private final FunnelSubsystem m_funnelSubsystem = FunnelSubsystem.getInstance();
    public final ManipulatorSubsystem m_manipulatorSubsystem = ManipulatorSubsystem.getInstance();
    private final ElevatorSubsystem m_ElevatorSubsystem = ElevatorSubsystem.getInstance();
    private final Controls m_controlsSubsystem = new Controls();
    private final ClimberSubsystem m_ClimberSubsystem = ClimberSubsystem.getInstance();
    // The driver's controller
    

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
    // Configure the button bindings
        configureButtonBindings();
        
        // Configure default commands
        m_robotDrive.setDefaultCommand(
            // The left stick controls translation of the robot.
            // Turning is controlled by the X axis of the right stick.
            new RunCommand(
                () -> m_robotDrive.drive(
                    -MathUtil.applyDeadband(m_controlsSubsystem.driveController.getLeftY(), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(m_controlsSubsystem.driveController.getLeftX(), OIConstants.kDriveDeadband),
                    -MathUtil.applyDeadband(m_controlsSubsystem.driveController.getRightX(), OIConstants.kDriveDeadband),
                    true),
                m_robotDrive));

                GameField gameField = null;
        try {
        gameField = new GameField(AprilTagFields.k2025Reefscape.loadAprilTagLayoutField(), FieldMirrorType.Mirrored);
        } catch (IOException e) {
        // AprilTagFields file not found
        e.printStackTrace();
        }

        PurePursuitSettings config = new PurePursuitSettings(gameField, Alliance.Blue)
        .setLookAheadScalar(0.2)
        .setDistanceToGoalTolerance(0.1)
        .setDefaultEndpointTolerance(0.1);

        //populateSendable
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
     * subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
     * passing it to a
     * {@link JoystickButton}.
     */
    private void configureButtonBindings() {
    new JoystickButton(m_controlsSubsystem.driveController, Button.kRightBumper.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));

        m_controlsSubsystem.getOperatePOVTrigger(90).whileTrue(
            new SequentialCommandGroup(
                new FunnelDownCommand(m_funnelSubsystem),
                new ClimberUpCommand(m_ClimberSubsystem)
            )
        );

        m_controlsSubsystem.getOperatePOVTrigger(270).whileTrue(
            new SequentialCommandGroup(
                new ClimberDownCommand(m_ClimberSubsystem),
                new FunnelUpCommand(m_funnelSubsystem)
            )
        );
        
        new Trigger(() -> {return m_controlsSubsystem.operateController.getRightTriggerAxis() >= 0.1;}).whileTrue(
        (new ManipulatorOutputCommand(m_manipulatorSubsystem))
        );

        m_controlsSubsystem.getOperatePOVTrigger(180)
        .whileTrue(new IntakeCommand(m_manipulatorSubsystem));

        new JoystickButton(m_controlsSubsystem.operateController, Button.kA.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, setpoint.ZERO));

        new JoystickButton(m_controlsSubsystem.operateController, Button.kB.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, setpoint.P2));

        new JoystickButton(m_controlsSubsystem.operateController, Button.kY.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, setpoint.P3));

        new JoystickButton(m_controlsSubsystem.operateController, Button.kX.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, setpoint.P4));
    }

    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return AutoShuffleboardTab.getInstance().getSelectedAuto();
    }
}
