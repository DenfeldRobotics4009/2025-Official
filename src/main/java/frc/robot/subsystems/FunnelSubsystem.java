package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

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
        this.dropFunnelMotor = new SparkMax(Constants.FunnelConstants.dropMotorID, MotorType.kBrushless);
    }
    //tests for if object in lazers way
    public void funnelDropMotorDown(){
       
        dropFunnelMotor.set(Constants.FunnelConstants.dropFunnelMotorSpeed * -1);
    }
    public void funnelDropMotorUp(){
        dropFunnelMotor.set(Constants.FunnelConstants.dropFunnelMotorSpeed);
    }
    public double getFunnelPos(){
        return dropFunnelMotor.getAbsoluteEncoder().getPosition();
    }
    public void setFunnelMotorSpeed(double speed){
        dropFunnelMotor.set(speed);
    }

} 

/* 
  if (codeWorks == false){
  isCrying = True
  }
 */