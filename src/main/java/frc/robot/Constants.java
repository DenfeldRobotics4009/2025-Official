// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants.NeoMotorConstants;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(26+(1/16));
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(26+(1/16));
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 4;
    public static final int kRearLeftDrivingCanId = 3;
    public static final int kFrontRightDrivingCanId = 5;
    public static final int kRearRightDrivingCanId = 2;

    public static final int kFrontLeftTurningCanId = 8;
    public static final int kRearLeftTurningCanId = 7;
    public static final int kFrontRightTurningCanId = 9;
    public static final int kRearRightTurningCanId = 6;

    public static final double precisionModeSpeed = 0.15;

    public static final boolean kGyroReversed = true;

    public static final PathConstraints pathConstraints = new PathConstraints(3, 2, 540, 720);
    
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
    // more teeth will result in a robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.08;
  }

  public static final class AprilTagOdometryConstants {
    public static double maxSpeed = 0.001; // power
    public static double maxRotation = 0.001; // power
    public static Transform3d frontCamPose = new Transform3d  (
      new Translation3d(0, 0, 0.694), // TODO: Find actual camera height from ground
      new Rotation3d(0, 0, 0)
        );
        public static double yawToSpeakerOffset = -5;
  }

    public static Transform3d backCamPose = new Transform3d(
      new Translation3d(0, 0, 0.694), // TODO: Find actual camera height from ground
      new Rotation3d(0, 0, 0)
        );

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = Constants.DriveConstants.kMaxSpeedMetersPerSecond;
    public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;
    public static final double kMaxAngularSpeedRadiansPerSecond = Constants.DriveConstants.kMaxAngularSpeed;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static final class FunnelConstants{
    public static final int wideFunnelSensorChannel = 0;
    public static final int dropMotorID = 0;
    public static final double laserSensorVoltageHigh = 455;
    public static final double agitatorMotorSpeed = 0.5;
    public static final double dropFunnelMotorSpeed = 1;
    public static final double maxFunnelAngle = 0;
    public static final double minFunnelAngle = 0;
  }
  public static final class CoralManipulatorConstants{
    public static final int coralManipulatorMotorID = 14;
    public static final int coralManipulatorSensorChannel = 0;
    public static final int deployMotorP = 0;
    public static final int deployMotorI = 0;
    public static final int deployMotorD = 0;
    public static final int dutyCycleEncoderChannel = 3;
    public static final int deployMotorStart = 0;
    public static final int deployMotorUp = 1;
    public static final int deployMotorDown = 2;
    public static final int lightStripAID = 6;
    public static final int lightStripBID = 7;
    public static final int lightStripCID = 8;
    public static final int lightStripDID = 9;

    public static final double coralManipulatorOuttakeMotorSpeed = 0.9;
    public static final double coralP4ManipulatorOuttakeMotorSpeed = -0.9;
    public static final double coralManipulatorSlowOuttakeMotorSpeed = -0.1;
    // public static final double coralP4ManipulatorP4SlowOuttakeMotorSpeed = -0.5;
  }
  public static final class CoralManipulatorCommandConstants{
    public static final double coralManipulatorIntakeMotorSpeed = -0.3;
    public static int PneumaticHubID = 20;
    public static final double algaeRemovalMotorSpeed = -0.5;
  }
  public static final class AlgaeManipulatorConstants{
    public static final int algaeManipulatorMotorID =16;

    public static final int AlgaeManipulatorModule = 20;
    public static final int AlgaeManipulatorForwardChannel = 3;
    public static final int AlgaeManipulatorReverseChannel = 4;
  }
  public static final class L1CoralManipulatorConstants{
    public static final int L1CoralManipulatorRotationMotorID = 16;
    public static final int L1CoralManipulatorIntakeMotorID = 17;
    public static final double enumManipulatorUP = .25; 
    public static final double enumManipulatorDOWN = 2.45; //1.0
    public static final double enumManipulatorOUTTAKE = .8; // .9 algae processer outake
    public static final double enumManipulatorALGAE = 1;
    public static final double enumManipulatorALGAEUP = 0; //TODO: Find encoder value
    public static final double enumALGAEOUTTAKE = .7;
    public static final double intakeSpeed = -.3;
    public static final double outakeSpeed = .5;

    public static final double algaeIntakeSpeed = -.4;
    public static final double algaeOuttakeSpeed = -.4;
    public static final double algaeOuttakeWristSpeed = .25;


    public static final double L1CoralManipulatorp = .4;
    public static final double L1CoralManipulatori = 0;
    public static final double L1CoralManipulatord = .008;
    public static final double pF = .05;
  }
  public static final class ClimberSubsystemConstants{
    public static final int winchMotorDeviceID = 13;
    public static final int climberDownlimitSwitchPort = 2;
    public static final double climberUpSpeed = 0.8;
    public static final double climberDownSpeed = -0.8;
    public static final double climberOffSpeed = 0;
  }
  public static final class ElevatorSubsystemConstants{
    public static final int enumPointZero = 0;
    public static final int enumPointLowAlgae = 300;
    public static final int enumP2 = 3500;
    public static final int enumP3 = 9500;
    public static final int enumP4 = 9500;

    public static int maxHeight = 10500;
    public static double maxSpeed = 1;
    public static final int ElevatormotorID = 11;
    public static final int ElevatormotorFollowerID = 10;
    public static final int ElevatorLimitSwitchPort = 9;
    public static final double ElevatorZeroMotorSpeed = -0.05;

    public static final double Elevatorp = .00025;
    public static final double Elevatori = 0; //.000035;
    public static final double Elevatord = .00002;
    public static final double Elevatorf = .01;

    public static final int wristMotorID = 31;
    public static final double wristUp = 0.45;
    public static final double wristNet = 0.455;
    public static final double topWristAlgae = 0.316; //TODO: tune this value
    public static final double bottomWristAlgae = 0.316; //TODO: tune this value
    public static final double wristDown = 0.078;
    public static final double wristMoving = 0.137;
    //"fake" zero: 0.122

    public static final double wristMinAngle = 0.082; //TODO: find actual value
    public static final double wristMaxAngle = 0.467; //TODO: find actual value

    public static final double Wristp = 1.5; //TODO: tune
    public static final double Wristi = 0; //TODO: tune
    public static final double Wristd = 0; //TODO: tune
    public static final double WristF = .15; //TODO: tune
  }
  
}
