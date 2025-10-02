// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.commands.ShootAlgaeCommand;
import frc.robot.commands.SlowCoralManiuplatorIntakeCommand;
import frc.robot.commands.SlowCoralManiuplatorOuttakeCommand;
import frc.robot.commands.ToggleAlgaeManipulatorCommand;
import frc.robot.commands.ToggleFunnelCommand;
import frc.robot.commands.AlgaeManipulatorIntakeCommand;
import frc.robot.commands.AlgaeManipulatorOuttakeCommand;
import frc.robot.commands.AlgaeRemoveCommand;
import frc.robot.commands.ClimberDownCommand;
import frc.robot.commands.ClimberUpCommand;
import frc.robot.commands.ElevatorControllerCommand;
import frc.robot.commands.L1AlgaeCommand;
import frc.robot.commands.L1CoralManipulatorSpeedCommand;
import frc.robot.commands.L1IntakeCommand;
import frc.robot.commands.PrecisionModeCommand;
import frc.robot.commands.ProcessorIntakeCommand;
import frc.robot.commands.ProcessorOuttakeCommand;
import frc.robot.commands.ResetSwerveOdometry;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralManipulatorOuttakeCommand;
import frc.robot.commands.SetElevatorOffset;
import frc.robot.subsystems.AlgaeManipulatorSubsystem;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.Controls;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.FunnelSubsystem;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

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
    public final CoralManipulatorSubsystem m_coralManipulatorSubsystem = CoralManipulatorSubsystem.getInstance();
    //public final AlgaeManipulatorSubsystem m_AlgaeManipulatorSubsystem = AlgaeManipulatorSubsystem.getInstance();
    public final L1CoraManipulatorSubsystem m_L1CoraManipulatorSubsystem = L1CoraManipulatorSubsystem.getInstance();
    private ElevatorSubsystem m_ElevatorSubsystem;
    private final Controls m_controlsSubsystem = new Controls();
    private final ClimberSubsystem m_ClimberSubsystem = ClimberSubsystem.getInstance();
    private final AprilTagOdometry m_AprilTagOdometry = AprilTagOdometry.getInstance();

    // The driver's controller
    public final Compressor m_compressor = new Compressor(20,PneumaticsModuleType.REVPH);
    private ShuffleBoard m_shuffleboard = null;
    GameField gameField = null;
    PurePursuitSettings config = null;

    boolean autoAlignActivated = false;

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
            m_robotDrive)
        );

        try {
        gameField = new GameField(AprilTagFields.k2025ReefscapeAndyMark.loadAprilTagLayoutField(), FieldMirrorType.Rotated);
        } catch (IOException e) {
        // AprilTagFields file not found
        e.printStackTrace();
        }

        this.config = new PurePursuitSettings(gameField, Alliance.Blue)
        .setLookAheadScalar(0.2)
        .setDistanceToGoalTolerance(0.1)
        .setDefaultEndpointTolerance(0.1)
        .setMaxVelocityMeters(Constants.DriveConstants.kMaxSpeedMetersPerSecond);
            
         config.setTurningPID(1, 0, 0);

        m_shuffleboard = new ShuffleBoard(config, gameField);

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
    // Locks robot movement - driver x
    new JoystickButton(m_controlsSubsystem.driveController, Button.kX.value)
        .whileTrue(new RunCommand(
            () -> m_robotDrive.setX(),
            m_robotDrive));

        // Reset driver gryo - driver y
        new JoystickButton(m_controlsSubsystem.driveController, Button.kY.value)
        .onTrue(new ResetSwerveOdometry());
    
        // Toggle algae manipulator - operator down dpad
        m_controlsSubsystem.getOperatePOVTrigger(180).whileTrue(
            new L1AlgaeCommand() 
        );

        // L1 intake - operator left dpad
        m_controlsSubsystem.getOperatePOVTrigger(90).whileTrue(
            new L1IntakeCommand()
        );

        // L1 Outtake - operator right dpad
        m_controlsSubsystem.getOperatePOVTrigger(270).whileTrue(
            new L1CoralManipulatorSpeedCommand(m_controlsSubsystem)
        );

        // Toggle funnel - operator up dpad
        m_controlsSubsystem.getOperatePOVTrigger(0).onTrue(
            new ToggleFunnelCommand(m_funnelSubsystem)
        );

        //Processor Outtake
        m_controlsSubsystem.getOperatePOVTrigger(180).whileTrue(
            new ProcessorIntakeCommand(m_L1CoraManipulatorSubsystem)
        );

        //Makes manipulator output coral - operator right trigger
        //check to make sure dpad not pressed for outake pos
        new Trigger(() -> {return m_controlsSubsystem.operateController.getRightTriggerAxis() >= 0.1 
            && !m_controlsSubsystem.getOperatePOVTrigger(270).getAsBoolean();}).whileTrue(
            (new CoralManipulatorOuttakeCommand(m_coralManipulatorSubsystem))
        );
        //Makes manipulator output coral - operator right trigger
        //check to make sure dpad not pressed for outake pos
        new Trigger(() -> {return m_controlsSubsystem.operateController.getRightTriggerAxis() >= 0.1 
            && m_controlsSubsystem.getOperatePOVTrigger(180).getAsBoolean();}).whileTrue(
            (new ProcessorOuttakeCommand(m_controlsSubsystem))
        );
        //Makes manipulator output coral - driver x
        // new JoystickButton(m_controlsSubsystem.driveController, Button.kX.value).whileTrue(
        //     (new CoralManipulatorOuttakeCommand(m_coralManipulatorSubsystem))
        // );

        // Set precision mode - drive right bumper and left bumper
        new JoystickButton(m_controlsSubsystem.driveController, Button.kRightBumper.value).whileTrue(
            (new PrecisionModeCommand())
        );
        new JoystickButton(m_controlsSubsystem.driveController, Button.kLeftBumper.value).whileTrue(
            (new PrecisionModeCommand())
        );

        // intake coral - operator left trigger 
        new Trigger(() -> {return m_controlsSubsystem.operateController.getLeftTriggerAxis() >= 0.1;}).whileTrue(
        (new CoralIntakeCommand(m_coralManipulatorSubsystem))
        );

        // slow coral outtake - operator right joystick
        new Trigger(()->{return m_controlsSubsystem.operateController.getRightY() > 0.5;}).whileTrue(
            (new SlowCoralManiuplatorOuttakeCommand(m_coralManipulatorSubsystem))
            );

        // slow coral intake - operator left joystick
        new Trigger(()->{return m_controlsSubsystem.operateController.getLeftY() > 0.5;}).whileTrue(
            (new SlowCoralManiuplatorIntakeCommand(m_coralManipulatorSubsystem))
            );

        // climber down - operator right bumper
        new JoystickButton(m_controlsSubsystem.operateController, Button.kRightBumper.value).whileTrue(
        (new ClimberDownCommand(m_ClimberSubsystem))
        );

        // climber up - operator left bumper
        new JoystickButton(m_controlsSubsystem.operateController, Button.kLeftBumper.value).whileTrue(
        (new ClimberUpCommand(m_ClimberSubsystem))
        );

        // remove low algae - driver left trigger
        JoystickButton driverStartButton = new JoystickButton(m_controlsSubsystem.driveController, Button.kStart.value);
        new Trigger(() -> {return m_controlsSubsystem.driveController.getLeftTriggerAxis() >= 0.1 && !driverStartButton.getAsBoolean();}).whileTrue(
        (new AlgaeRemoveCommand(ElevatorSubsystem.getInstance()))
        );

        // // remove high algae - driver right trigger
        // new Trigger(() -> {return m_controlsSubsystem.driveController.getRightTriggerAxis() >= 0.1;}).whileTrue(
        // (new TopAlgaeRemoveCommand())
        // );

        // new JoystickButton(m_controlsSubsystem.operateController, Button.kRightBumper.value)
        // .onTrue(new SetElevatorOffset(m_ElevatorSubsystem, 10));
        // new JoystickButton(m_controlsSubsystem.operateController, Button.kLeftBumper.value)
        // .onTrue(new SetElevatorOffset(m_ElevatorSubsystem, -10));
        
        // Moves elevator to height for each reef level

        // elevator zero/P1 - operator A
        new JoystickButton(m_controlsSubsystem.operateController, Button.kA.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.ZERO, WristAngle.MOVING)); //Zero is the same as L1

        // elevator P2 - operator B
        new JoystickButton(m_controlsSubsystem.operateController, Button.kB.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P2, WristAngle.MOVING));

        // elevator to low algae position - operator right joystick click
        new JoystickButton(m_controlsSubsystem.operateController, Button.kRightStick.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.LOW_ALGAE, WristAngle.MOVING));

        // elevator P3 - operator y
        new JoystickButton(m_controlsSubsystem.operateController, Button.kY.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P3, WristAngle.MOVING));

        // elevator P4 - operator x
        new JoystickButton(m_controlsSubsystem.operateController, Button.kX.value)
        .onTrue(new SetElevatorTargetCommand(m_ElevatorSubsystem, ElevatorSetpoint.P4, WristAngle.UP)); 
        
        // shoot algae - driver start
        driverStartButton
        .whileTrue(new ShootAlgaeCommand(m_ElevatorSubsystem));

        // net score auto align - driver down dpad AND right trigger pressed
        try {
            m_controlsSubsystem
            .getDrivePOVTrigger(180).and(new Trigger(() -> {return m_controlsSubsystem.driveController.getRightTriggerAxis() >= 0.1;}))
            .onTrue(
                AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Net Score"),
                Constants.DriveConstants.pathConstraints));
        } catch (FileVersionException | IOException | ParseException e) {
            e.printStackTrace();
        }

        // Cancel auto align - driver A
        new JoystickButton(m_controlsSubsystem.driveController, Button.kA.value)
        .whileTrue(new RunCommand(() -> m_robotDrive.getCurrentCommand().cancel()));
    
    }

    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        // return AutoShuffleboardTab.getInstance().getSelectedAuto();
      //  return m_shuffleboard.getSelectedAuto();
    
        return m_shuffleboard.getSelectedAuto();
    }
}

