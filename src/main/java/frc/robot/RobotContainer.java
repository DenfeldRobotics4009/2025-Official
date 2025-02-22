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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.XboxController.Button;

import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.autos.AutoTest;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.commands.ToggleAlgaeManipulatorCommand;
import frc.robot.commands.ToggleFunnelCommand;
import frc.robot.commands.TopAlgaeRemoveCommand;
import frc.robot.commands.AlgaeManipulatorIntakeCommand;
import frc.robot.commands.AlgaeManipulatorOuttakeCommand;
import frc.robot.commands.BottomAlgaeRemoveCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.ElevatorControllerCommand;
import frc.robot.commands.ResetSwerveOdometry;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralManipulatorOutputCommand;
import frc.robot.commands.CoralManipulatorOutputCommandP4;
import frc.robot.commands.SetElevatorOffset;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.commands.ToggleFunnelCommand;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.Controls;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;
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
    public final CoralManipulatorSubsystem m_manipulatorSubsystem = CoralManipulatorSubsystem.getInstance();
    public final AlgaeManipulatorSubsystem m_AlgaeManipulatorSubsystem = AlgaeManipulatorSubsystem.getInstance();
    private ElevatorSubsystem m_ElevatorSubsystem;
    private final Controls m_controlsSubsystem = new Controls();
    private final ClimberSubsystem m_ClimberSubsystem = ClimberSubsystem.getInstance();
    private final AprilTagOdometry m_AprilTagOdometry = AprilTagOdometry.getInstance();

    // The driver's controller
    public final Compressor m_compressor = new Compressor(20,PneumaticsModuleType.REVPH);
    private final ShuffleBoard m_Elastic = new ShuffleBoard();
    GameField gameField = null;
    PurePursuitSettings config = null;

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
           
    
            try {
                m_ElevatorSubsystem = ElevatorSubsystem.getInstance();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                m_ElevatorSubsystem = null;
        }
        m_compressor.enableDigital();
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
                    !m_controlsSubsystem.driveController.getLeftBumperButton()),
                m_robotDrive));

        try {
        gameField = new GameField(AprilTagFields.k2025Reefscape.loadAprilTagLayoutField(), FieldMirrorType.Mirrored);
        } catch (IOException e) {
        // AprilTagFields file not found
        e.printStackTrace();
        }

        this.config = new PurePursuitSettings(gameField, Alliance.Red)
        .setLookAheadScalar(0.2)
        .setDistanceToGoalTolerance(0.1)
        .setDefaultEndpointTolerance(0.1);
        config.setTurningPID(1, 0, 0);

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
    new JoystickButton(m_controlsSubsystem.driveController, Button.kRightBumper.value);
    new JoystickButton(m_controlsSubsystem.driveController, Button.kRightBumper.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));

        new JoystickButton(m_controlsSubsystem.driveController, Button.kB.value)
        .onTrue(new ResetSwerveOdometry());
    
        //toggle algae manipulator
        m_controlsSubsystem.getOperatePOVTrigger(0).whileTrue(
            new ToggleAlgaeManipulatorCommand(m_AlgaeManipulatorSubsystem)    
        );
        //algae intake
        m_controlsSubsystem.getOperatePOVTrigger(90).whileTrue(
            new AlgaeManipulatorIntakeCommand(m_AlgaeManipulatorSubsystem)    
        );
        //outtake algae
        m_controlsSubsystem.getOperatePOVTrigger(270).whileTrue(
            new AlgaeManipulatorOuttakeCommand(m_AlgaeManipulatorSubsystem)
        );
        //toggle funnel
        m_controlsSubsystem.getOperatePOVTrigger(180).whileTrue(
            new ToggleFunnelCommand(m_funnelSubsystem)
        );
        //Makes manipulator output coral
        new Trigger(() -> {return m_controlsSubsystem.operateController.getRightTriggerAxis() >= 0.1;}).whileTrue(
        (new CoralManipulatorOutputCommand(m_manipulatorSubsystem))
        );
        new Trigger(() -> {return m_controlsSubsystem.operateController.getLeftTriggerAxis() >= 0.1;}).whileTrue(
        (new CoralIntakeCommand(m_manipulatorSubsystem))
        );
        new JoystickButton(m_controlsSubsystem.operateController, Button.kRightBumper.value).whileTrue(
        (new ClimberDownCommand(m_ClimberSubsystem))
        );
        new JoystickButton(m_controlsSubsystem.operateController, Button.kLeftBumper.value).whileTrue(
        (new ClimberUpCommand(m_ClimberSubsystem))
        );
        new Trigger(() -> {return m_controlsSubsystem.driveController.getLeftTriggerAxis() >= 0.1;}).whileTrue(
        (new TopAlgaeRemoveCommand())
        );
        new Trigger(() -> {return m_controlsSubsystem.driveController.getRightTriggerAxis() >= 0.1;}).whileTrue(
        (new BottomAlgaeRemoveCommand())
        );

        // new JoystickButton(m_controlsSubsystem.operateController, Button.kRightBumper.value)
        // .onTrue(new SetElevatorOffset(m_ElevatorSubsystem, 10));
        // new JoystickButton(m_controlsSubsystem.operateController, Button.kLeftBumper.value)
        // .onTrue(new SetElevatorOffset(m_ElevatorSubsystem, -10));
        
        // Moves elevator to height for each reef level
        new JoystickButton(m_controlsSubsystem.operateController, Button.kA.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.ZERO, WristAngle.MOVING)); //Zero is the same as L1

        new JoystickButton(m_controlsSubsystem.operateController, Button.kB.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P2, WristAngle.MOVING));


        new JoystickButton(m_controlsSubsystem.operateController, Button.kY.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P3, WristAngle.MOVING));

        new JoystickButton(m_controlsSubsystem.operateController, Button.kX.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P4, WristAngle.UP)); 
        
        new JoystickButton(m_controlsSubsystem.operateController, Button.kRightStick.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.ZERO, WristAngle.DOWN)); 
    }

    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        // return AutoShuffleboardTab.getInstance().getSelectedAuto();
        return new AutoTest(config, Alliance.Blue, gameField);
    }
}
