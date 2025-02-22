package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.CoralIntakeCommand;

public class CoralManipulatorSubsystem extends SubsystemBase{
//    objects
    SparkMax coralManipulatorMotor;
    AnalogInput coralManipulatorSensor;
    PneumaticHub lightHub = new PneumaticHub(Constants.CoralManipulatorCommandConstants.PneumaticHubID);
    Solenoid lightStripA = lightHub.makeSolenoid(Constants.CoralManipulatorConstants.lightStripAID);
    Solenoid lightStripB = lightHub.makeSolenoid(Constants.CoralManipulatorConstants.lightStripBID);
    Solenoid lightStripC = lightHub.makeSolenoid(Constants.CoralManipulatorConstants.lightStripCID);
    Solenoid lightStripD = lightHub.makeSolenoid(Constants.CoralManipulatorConstants.lightStripDID);

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
        this.coralManipulatorMotor = new SparkMax(Constants.CoralManipulatorConstants.coralManipulatorMotorID, MotorType.kBrushless);
        this.coralManipulatorSensor = new AnalogInput(Constants.CoralManipulatorConstants.coralManipulatorSensorChannel); 
    }
    //sets the motor speed to the speed that is defined
    public void coralManipulatorMotorSpeed(double motorSpeed){
        coralManipulatorMotor.set(motorSpeed);
    }
    @Override
    public void periodic() {
        setLightStripPowered(getShortFunnelSensor());
        System.out.println("coral sensor: "+ getShortFunnelSensor());
        System.out.println("sensor voltage: "+ coralManipulatorSensor.getValue());
    }

    //get commands for Elastic
    public double getManipulatorMotorEncoder() {
        return coralManipulatorMotor.getAbsoluteEncoder().getPosition();
    }
    public boolean getShortFunnelSensor() {
        return coralManipulatorSensor.getValue() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }
    public double getCoralManipulatorMotorSpeed() {
        return coralManipulatorMotor.get();
    }
    public void setLightStripPowered(boolean lightStripOn){
        if(lightStripOn){
            lightStripA.set(true);
            lightStripB.set(true);
            lightStripC.set(true);
            lightStripD.set(true);
        }
        else{
            lightStripA.set(false);
            lightStripB.set(false);
            lightStripC.set(false);
            lightStripD.set(false);
        }
    }
}
