package frc.robot.subsystems;



import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ManipulatorSubsystem extends SubsystemBase{
//    objects
    SparkMax manipulatorMotor;
    AnalogInput pieceInManipulatorSensor;

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
        this.manipulatorMotor = new SparkMax(Constants.ManipulatorConstants.manipulatorMotorID, MotorType.kBrushless);
        this.pieceInManipulatorSensor = new AnalogInput(Constants.ManipulatorConstants.manipulatorSensorChannel); 
    }
    //sets the motor speed to the speed that is defined
    public void manipulatorMotorSpeed(double motorSpeed){
        manipulatorMotor.set(motorSpeed);
    }
    @Override
    public void periodic() {
    }

    //get commands for Elastic
    public double getManipulatorMotorEncoder() {
        return manipulatorMotor.getAbsoluteEncoder().getPosition();
    }
    public boolean getShortFunnelSensor() {
        return pieceInManipulatorSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }
    public double getManipulatorMotorSpeed() {
        return manipulatorMotor.get();
    }
}
