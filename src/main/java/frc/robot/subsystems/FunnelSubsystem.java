package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.IntakeCommand;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    //objects
    
    AnalogInput wideFunnelSensor;
   
    Solenoid dropPiston;

//Constructor
    public FunnelSubsystem() {
       
        this.wideFunnelSensor = new AnalogInput(Constants.FunnelConstants.wideFunnelSensorChannel);
       
        this.dropPiston =  new Solenoid(null, Constants.FunnelConstants.dropPistonChannel);
    }
    //tests for if object in lazers way
   
    public boolean getWideFunnelSensor() {
        return wideFunnelSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
        
    }

  
    //Droppiston on / off
    public void dropPiston(boolean dropPistonDown){
      
            dropPiston.set(!dropPistonDown);
    }

    public boolean pistonValue(){
        return dropPiston.get();
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
 * if (codeWorks == false){
 * isCrying = True
 * }
 */