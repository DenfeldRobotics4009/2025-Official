package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.IntakeCommand;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    //objects
    
    AnalogInput wideFunnelSensor;
    SparkMax dropFunnelMotor; 

//Constructor
    public FunnelSubsystem() {
       
        this.wideFunnelSensor = new AnalogInput(Constants.FunnelConstants.wideFunnelSensorChannel);
        this.dropFunnelMotor = new SparkMax(Constants.FunnelConstants.dropMotorChannel, null);
    }
    //tests for if object in lazers way
   
    public boolean getWideFunnelSensor() {
        return wideFunnelSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
        
    }
    public void funnelDropMotorDown(){

    }
    public void funnelDropMotorUp(){

    }
IntakeCommand runIntakeCommand = new IntakeCommand(ManipulatorSubsystem.getInstance());
    @Override
    public void periodic() {
        if (getWideFunnelSensor() && !runIntakeCommand.isScheduled()) {
            CommandScheduler.getInstance().schedule(runIntakeCommand);
        }
    }
} 

/* 
  if (codeWorks == false){
  isCrying = True
  }
 */