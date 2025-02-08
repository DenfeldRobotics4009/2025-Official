package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// "Tanner is stinky" -Luke
public class FunnelSubsystem extends SubsystemBase{
    //object
    SparkMax dropFunnelMotor; 

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
        this.dropFunnelMotor = new SparkMax(Constants.FunnelConstants.dropMotorChannel, null);
    }
    //tests for if object in lazers way
    public void funnelDropMotorDown(){

    }
    public void funnelDropMotorUp(){

    }

} 

/* 
  if (codeWorks == false){
  isCrying = True
  }
 */