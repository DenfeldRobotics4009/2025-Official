package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CoralManipulatorIntakeCommand;

public class CoralManipulatorSubsystem extends SubsystemBase{
   //objects
    SparkMax manipulatorMotor;
    SparkMax deployMotor;
    AnalogInput pieceInManipulatorSensor;
    PIDController pid;
    DutyCycleEncoder pidEncoder = new DutyCycleEncoder(Constants.CoralManipulatorConstants.dutyCycleEncoderChannel);

      private static CoralManipulatorSubsystem instance;

  /**
   * Returns the Scheduler instance.
   *
   * @return the instance
   */
  public static  CoralManipulatorSubsystem getInstance() {
    if (instance == null) {
      instance = new CoralManipulatorSubsystem();
    }
    return instance;
    }
    //constructor
    public CoralManipulatorSubsystem() {
        this.manipulatorMotor = new SparkMax(Constants.CoralManipulatorConstants.manipulatorMotorID, null);
        this.pieceInManipulatorSensor = new AnalogInput(Constants.CoralManipulatorConstants.manipulatorSensorChanel); 
        this.pid = new PIDController(Constants.CoralManipulatorConstants.deployMotorP, Constants.CoralManipulatorConstants.deployMotorI, Constants.CoralManipulatorConstants.deployMotorD);
        
    }
    //sets the motor speed to the speed that is defined
    public void manipulatorMotorSpeed(double motorSpeed){
        manipulatorMotor.set(motorSpeed);
    }
    //tests for if object in lazers way
    public boolean getManipulatorSensor() {
        return pieceInManipulatorSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }

    public void setPIDTarget(double pidTarget) {
        pid.setSetpoint(pidTarget);
    }
    CoralManipulatorIntakeCommand runIntakeCommand = new CoralManipulatorIntakeCommand(CoralManipulatorSubsystem.getInstance());
    @Override
    public void periodic() {
        if (getManipulatorSensor() && !runIntakeCommand.isScheduled()) {
              CommandScheduler.getInstance().schedule(runIntakeCommand);
        }
       double speed = pid.calculate(pidEncoder.get());
       deployMotor.set(speed);
    }
    

    public enum deployMotorPoints{
        Start(Constants.CoralManipulatorConstants.deployMotorStart), 
        Up(Constants.CoralManipulatorConstants.deployMotorUp),
        Down(Constants.CoralManipulatorConstants.deployMotorDown);
        double encoderValue;
        deployMotorPoints(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    
}
