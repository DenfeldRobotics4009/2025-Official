package frc.robot.subsystems;



import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ManipulatorSubsystem extends SubsystemBase{
   //objects
    SparkMax manipulatorMotor;
    SparkMax deployMotor;
    AnalogInput pieceInManipulatorSensor;
    PIDController pid;
    DutyCycleEncoder pidEncoder = new DutyCycleEncoder(Constants.ManipulatorConstants.dutyCycleEncoderChannel);

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
    public enum deployMotorPoints{
        Start(0), //TODO: Add Constants in for the levels
        Up(1),
        Down(2);
        double encoderValue;
        deployMotorPoints(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    
}
