package frc.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    //objects
   
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
       
       
        //Pnumatics hub is module 20, and is a REVPH hub. the piston is plugged into 0 and 1 on the REVPH
        this.dropPiston =  new DoubleSolenoid(20,PneumaticsModuleType.REVPH, 0, 1);
    }
    //tests for if object in laser's way
   
   

  
    //Droppiston on / off
 

// IntakeCommand runIntakeCommand = new IntakeCommand(ManipulatorSubsystem.getInstance());
    @Override
    public void periodic() {
    }
     public void pistonIsPowered(boolean pistonOn){
        //if the piston is set to on, power the piston
        if(pistonOn){
            dropPiston.set(DoubleSolenoid.Value.kForward);
        }
        //if it's not set to on, unpower the piston
        else{
            dropPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    //get commands for shuffleboard
    public boolean getFunnelDropPiston() {
        return dropPiston.equals(dropPiston);
    }
} 

/* 
  if (codeWorks == false){
  isCrying = True
  }
 */