package frc.robot.subsystems;



import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ManipulatorSubsystem extends SubsystemBase{
   //objects
    SparkMax manipulatorMotor;
    SparkMax deployMotor;
    AnalogInput pieceInManipulatorSensor;
    PIDController pid;
    DutyCycleEncoder pidEncoder = new DutyCycleEncoder(Constants.ManipulatorConstants.dutyCycleEncoderChannel);

      private static ManipulatorSubsystem instance;

  /**
   * Returns the Scheduler instance.
   *
   * @return the instance
   */
  public static  ManipulatorSubsystem getInstance() {
    if (instance == null) {
      instance = new ManipulatorSubsystem();
    }
    return instance;
    }
    //constructor
    public ManipulatorSubsystem() {
        this.manipulatorMotor = new SparkMax(Constants.ManipulatorConstants.manipulatorMotorID, null);
        this.pieceInManipulatorSensor = new AnalogInput(Constants.ManipulatorConstants.manipulatorSensorChanel); 
        this.pid = new PIDController(Constants.ManipulatorConstants.deployMotorP, Constants.ManipulatorConstants.deployMotorI, Constants.ManipulatorConstants.deployMotorD);
        
    }
    //sets the motor speed to the speed that is defined
    public void manipulatorMotorSpeed(double motorSpeed){
        manipulatorMotor.set(motorSpeed);
    }
    //tests for if object in lazers way
    public boolean getShortFunnelSensor() {
        return pieceInManipulatorSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }

    public void setPIDTarget(double pidTarget) {
        pid.setSetpoint(pidTarget);
    }
    @Override
    public void periodic() {
        
       double speed = pid.calculate(pidEncoder.get());
       deployMotor.set(speed);
    }
    
    
}
