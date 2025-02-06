package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    // objects
    SparkMax agitatorMotor;
    AnalogInput wideFunnelSensor;
    AnalogInput shortFunnelSensor;
    Solenoid dropPiston;

//Constructor
    public FunnelSubsystem() {
        this.agitatorMotor = new SparkMax(Constants.FunnelConstants.agitatorMotorID, null);
        this.wideFunnelSensor = new AnalogInput(Constants.FunnelConstants.wideFunnelSensorChannel);
        this.shortFunnelSensor = new AnalogInput(Constants.FunnelConstants.shortFunnelSensorChannel);
        this.dropPiston =  new Solenoid(null, Constants.FunnelConstants.dropPistonChannel);
    }
    //tests for if object in lazers way
    public boolean getShortFunnelSensor() {
        return shortFunnelSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }
    public boolean getWideFunnelSensor() {
        return wideFunnelSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
    }

    //agitator on / off
    public void agitatorMoterToggle(boolean agitatorOn){
        if (agitatorOn == true) {
            agitatorMotor.set(Constants.FunnelConstants.agitatorMotorSpeed);
        } else {
            agitatorMotor.set(0);
        } 
    }  
    //Droppiston on / off
    public void dropPiston(boolean dropPistonDown){
            dropPiston.set(!dropPistonDown);
    }

    public boolean pistonValue(){
        return dropPiston.get();
    }
} 

/*
 * if (codeWorks == false){
 * isCrying = True
 * }
 */