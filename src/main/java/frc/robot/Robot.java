// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ResetSwerveOdometry;
import frc.robot.subsystems.L1CoraManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  // private AutoResetOdometry m_AutoResetOdometry;
  Thread m_visionThread;

  private final RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    m_visionThread =
      new Thread(
        () -> {
        UsbCamera camera0 = CameraServer.startAutomaticCapture(0);
        UsbCamera camera1 = CameraServer.startAutomaticCapture(1);
        camera0.setResolution(640, 480);
        camera1.setResolution(640, 480);

        CvSink cvSink0 = CameraServer.getVideo(camera0);
        CvSink cvSink1 = CameraServer.getVideo(camera1);
        CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);

        Mat mat = new Mat();

        while (!Thread.interrupted()) {
          if (cvSink0.grabFrame(mat) == 0) {
            outputStream.notifyError(cvSink0.getError());
            continue;
          }
          if (cvSink1.grabFrame(mat) == 0) {
            outputStream.notifyError(cvSink1.getError());
            continue;
          }
          Imgproc.rectangle(
            mat, new Point(100, 100), new Point(400, 400), new Scalar(255, 255, 255), 5);

            outputStream.putFrame(mat);
        }

        });

    m_visionThread.setDaemon(true);
    m_visionThread.start();
    
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //System.out.println("encode: " + L1CoraManipulatorSubsystem.getInstance().getL1ManipulatorMotorEncoder());
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  
    // new AutoResetOdometry();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    // new ResetSwerveOdometry();
    // new AutoResetOdometry();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
