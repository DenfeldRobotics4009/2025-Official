package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FunnelSubsystem extends SubsystemBase{
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
    //Voltage for the lazer sensors
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
} 