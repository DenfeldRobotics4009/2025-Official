package frc.robot.subsystems;
import static edu.wpi.first.units.Units.Value;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.IntakeCommand;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    //objects
    
    AnalogInput wideFunnelSensor;
   
    DoubleSolenoid dropPiston;

    private static FunnelSubsystem instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return the instance
     */
    public static  FunnelSubsystem getInstance() {
      if (instance == null) {
        instance = new FunnelSubsystem();
      }
      return instance;
      }

//Constructor
    public FunnelSubsystem() {
       
        //this.wideFunnelSensor = new AnalogInput(Constants.FunnelConstants.wideFunnelSensorChannel);
       
        //Pnumatics hub is module 20, and is a REVPH hub. the piston is plugged into 0 and 1 on the REVPH
        this.dropPiston =  new DoubleSolenoid(20,PneumaticsModuleType.REVPH, 0, 1);
    }
    //tests for if object in lazers way
   
    public boolean getWideFunnelSensor() {
        return false;
        //return wideFunnelSensor.getVoltage() < Constants.FunnelConstants.laserSensorVoltageHigh;
        
    }

  
    //Droppiston on / off
 

// IntakeCommand runIntakeCommand = new IntakeCommand(ManipulatorSubsystem.getInstance());
    @Override
    public void periodic() {
        //System.out.println(dropPiston.get());
        // if (getWideFunnelSensor() && !runIntakeCommand.isScheduled()) {
        //     CommandScheduler.getInstance().schedule(runIntakeCommand);
        // }
    }
     public void pistonIsPowered(boolean pistonOn){
        if(pistonOn){
            dropPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            dropPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
} 

/*
 * if (codeWorks == false){
 * isCrying = True
 * }
 */