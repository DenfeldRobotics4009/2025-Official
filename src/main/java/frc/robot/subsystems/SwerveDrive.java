// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.events.EventTrigger;
import com.studica.frc.AHRS;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.library.auto.pathing.DriveSubsystem;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralManipulatorOuttakeCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.commands.ToggleFunnelCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class SwerveDrive extends SubsystemBase implements DriveSubsystem {
  //creates inverse kinematics
  private static SwerveDrive instance;

  /**
   * Returns the Scheduler instance.
   *
   * @return the instance
   */
  public static  SwerveDrive getInstance() {
    if (instance == null) {
      instance = new SwerveDrive();
    }
    return instance;
    }

  // Create MAXSwerveModules
 
  private final MAXSwerveModule m_frontLeft = new MAXSwerveModule(
      DriveConstants.kFrontLeftDrivingCanId,
      DriveConstants.kFrontLeftTurningCanId,
      DriveConstants.kFrontLeftChassisAngularOffset);

  private final MAXSwerveModule m_frontRight = new MAXSwerveModule(
      DriveConstants.kFrontRightDrivingCanId,
      DriveConstants.kFrontRightTurningCanId,
      DriveConstants.kFrontRightChassisAngularOffset);

  private final MAXSwerveModule m_rearLeft = new MAXSwerveModule(
      DriveConstants.kRearLeftDrivingCanId,
      DriveConstants.kRearLeftTurningCanId,
      DriveConstants.kBackLeftChassisAngularOffset);

  private final MAXSwerveModule m_rearRight = new MAXSwerveModule(
      DriveConstants.kRearRightDrivingCanId,
      DriveConstants.kRearRightTurningCanId,
      DriveConstants.kBackRightChassisAngularOffset);

    boolean precisionMode;

    ChassisSpeeds chassisSpeeds = null;
    
  // The gyro sensor
  public final AHRS m_gyro = new AHRS(AHRS.NavXComType.kMXP_SPI, 100);
  
  // Odometry class for tracking robot pose
  private SwerveDrivePoseEstimator swerveDrivePoseEstimator = new SwerveDrivePoseEstimator(DriveConstants.kDriveKinematics,
  Rotation2d.fromDegrees(-m_gyro.getAngle()),
  new SwerveModulePosition[] {
    m_frontLeft.getPosition(),
    m_frontRight.getPosition(),
    m_rearLeft.getPosition(),
    m_rearRight.getPosition()
  }, new Pose2d());

  /** Creates a new DriveSubsystem. */
  public SwerveDrive() {
    // Usage reporting for MAXSwerve template
    HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);
    RobotConfig config;
    try {
      config = RobotConfig.fromGUISettings();
       // Configure AutoBuilder last
    AutoBuilder.configure(
      this::getPosition, // Robot pose supplier
      this::setPosition, // Method to reset odometry (will be called if your auto has a starting pose)
      this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
      (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
      new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
              new PIDConstants(3.5, 0.0, 0.75), // Translation PID constants
              new PIDConstants(3, 0.0, 0.25) // Rotation PID constants
      ),
      config, // The robot configuration
      () -> {
        // Boolean supplier that controls when the path will be mirrored for the red alliance
        // This will flip the path being followed to the red side of the field.
        // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
          return alliance.get() == DriverStation.Alliance.Red;
        }
        return false;
      },
      this // Reference to this subsystem to set requirements

      );

    } catch (Exception e) {
      e.printStackTrace();
    }
    NamedCommands.registerCommand("Elevator L4", new SetElevatorTargetCommand(
      ElevatorSubsystem.getInstance(),
      ElevatorSubsystem.ElevatorSetpoint.P4,
      ElevatorSubsystem.WristAngle.UP)
    );

    NamedCommands.registerCommand("Elevator L3", new SetElevatorTargetCommand(
      ElevatorSubsystem.getInstance(),
      ElevatorSubsystem.ElevatorSetpoint.P3,
      ElevatorSubsystem.WristAngle.MOVING)
    );

    NamedCommands.registerCommand("Elevator L2", new SetElevatorTargetCommand(
      ElevatorSubsystem.getInstance(),
      ElevatorSubsystem.ElevatorSetpoint.P2,
      ElevatorSubsystem.WristAngle.MOVING)
    );

    NamedCommands.registerCommand("Elevator ZERO", new SetElevatorTargetCommand(
      ElevatorSubsystem.getInstance(),
      ElevatorSubsystem.ElevatorSetpoint.ZERO,
      ElevatorSubsystem.WristAngle.DOWN)
    );

    NamedCommands.registerCommand("Coral intake", new CoralIntakeCommand(CoralManipulatorSubsystem.getInstance()));
    NamedCommands.registerCommand("Coral outtake", new CoralManipulatorOuttakeCommand(CoralManipulatorSubsystem.getInstance()));
    NamedCommands.registerCommand("Toggle funnel", new ToggleFunnelCommand(FunnelSubsystem.getInstance()));
  }
 
  private ChassisSpeeds getRobotRelativeSpeeds(){
    return DriveConstants.kDriveKinematics.toChassisSpeeds(
    m_frontLeft.getState(),
    m_frontRight.getState(),
    m_rearLeft.getState(),
    m_rearRight.getState());
  }
  public void driveRobotRelative(ChassisSpeeds speed){
    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(speed);
  SwerveDriveKinematics.desaturateWheelSpeeds(
      swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
  m_frontLeft.setDesiredState(swerveModuleStates[0]);
  m_frontRight.setDesiredState(swerveModuleStates[1]);
  m_rearLeft.setDesiredState(swerveModuleStates[2]);
  m_rearRight.setDesiredState(swerveModuleStates[3]);
  }
  @Override
  public void periodic() {

    // Update the odometry in the periodic block
    SmartDashboard.putNumber("gyro:", getHeading());
      swerveDrivePoseEstimator.update(Rotation2d.fromDegrees(-m_gyro.getAngle()), new SwerveModulePosition[]{ 
          m_frontLeft.getPosition(),
          m_frontRight.getPosition(),
          m_rearLeft.getPosition(),
          m_rearRight.getPosition()
        }
      );

      SmartDashboard.putNumber("Odometry X", getPosition().getX());
      SmartDashboard.putNumber("Odometry Y", getPosition().getY());
      //System.out.println("Inverse Kinematics Statement: " + m_rearLeft.getPosition());
    };

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPosition() {
    var x = swerveDrivePoseEstimator.getEstimatedPosition();
    // SmartDashboard.putData("Current pose", x);
    return x;
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void setPosition(Pose2d pose) {
    swerveDrivePoseEstimator.resetPosition(
        Rotation2d.fromDegrees(-m_gyro.getAngle()),
        new SwerveModulePosition[] {
            m_frontLeft.getPosition(),
            m_frontRight.getPosition(),
            m_rearLeft.getPosition(),
            m_rearRight.getPosition()
        },
    pose);
  }

  // Returns ChassisSpeeds
  // public ChassisSpeeds getSpeeds() {
  //   return Constants.DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates());
  // }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed        Speed of the robot in the x direction on a 0-1 scale (forward).
   * @param ySpeed        Speed of the robot in the y direction on a 0-1 scale (sideways).
   * @param rot           Angular rate of the robot on a 0-1 scale.
   * @param fieldRelative Whether the provided x and y speeds are relative to the
   *                      field.
   */
  @Override
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {

    if (precisionMode) {
      xSpeed = xSpeed * Constants.DriveConstants.precisionModeSpeed;
      ySpeed = ySpeed * Constants.DriveConstants.precisionModeSpeed;
    }

    // Convert the commanded speeds into the correct units for the drivetrain
    double xSpeedDelivered = xSpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double ySpeedDelivered = ySpeed * DriveConstants.kMaxSpeedMetersPerSecond;
    double rotDelivered = rot * DriveConstants.kMaxAngularSpeed;

    var swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
       fieldRelative
          ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
            Rotation2d.fromDegrees(-m_gyro.getAngle()))
            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered)
    );
    SwerveDriveKinematics.desaturateWheelSpeeds(
        swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_rearLeft.setDesiredState(swerveModuleStates[2]);
    m_rearRight.setDesiredState(swerveModuleStates[3]);
  }

  

  /**
   * Sets the wheels into an X formation to prevent movement.
   */
  public void setX() {
    m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    m_rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
  }

    /**
   * Stops the swerve modules.
   */
  public void stopModules() {
    m_frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(0)));
    m_frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(0)));
    m_rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(0)));
    m_rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(0)));
  }

  /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
    m_frontLeft.setDesiredState(desiredStates[0]);
    m_frontRight.setDesiredState(desiredStates[1]);
    m_rearLeft.setDesiredState(desiredStates[2]);
    m_rearRight.setDesiredState(desiredStates[3]);
  }

  // public SwerveModuleState getModuleStates() {
  //   SwerveModuleState[] states = new SwerveModuleState[];
  //   m_frontLeft.getState();
  //   m_frontRight.getState();
  //   m_rearLeft.getState();
  //   m_rearRight.getState();
        
  //   return

  // }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_frontLeft.resetEncoders();
    m_rearLeft.resetEncoders();
    m_frontRight.resetEncoders();
    m_rearRight.resetEncoders();
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return Rotation2d.fromDegrees(-m_gyro.getAngle()).getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  public void addVisionMeasurement(Pose2d visionPosition, double timestampSeconds) {
    // Check if the vision position is within 1 meter of the current drive position,
    // per the robotPoseEstimator recommendations.

    // If the AprilTag is wildly different from the Swerve Pose, don't update.
    //if (visionPosition.getTranslation().getDistance(getPosition().getTranslation()) < 1) {
      swerveDrivePoseEstimator.addVisionMeasurement(visionPosition, timestampSeconds);
    //}
  }

  public boolean setPrecisionMode(boolean precisionMode) {
      this.precisionMode = precisionMode;
      return precisionMode;
  }
}
