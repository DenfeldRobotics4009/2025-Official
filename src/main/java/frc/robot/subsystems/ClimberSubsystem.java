package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase{
    //creates variables for our objects
   private SparkMax winchMotor;
   DoubleSolenoid climbPiston;
   

private static ClimberSubsystem instance;

/**
 * Returns the Scheduler instance.
 *
 * @return the instance
 */
public static  ClimberSubsystem getInstance() {
    if (instance == null) {
        instance = new ClimberSubsystem();
    }
    return instance;
    }

    public ClimberSubsystem(){
        this.winchMotor = new SparkMax(Constants.ClimberSubsystemConstants.winchMotorDeviceID, MotorType.kBrushless); 
    }
    
    public void moveClimberUp(){
        //function for moving the climber up. sets the motor speed to our set climber up speed.
        winchMotor.set(Constants.ClimberSubsystemConstants.climberUpSpeed);
    }

    public void moveClimberDown(){
        winchMotor.set(Constants.ClimberSubsystemConstants.climberDownSpeed);
    }

    public void winchMotorOff(){
        //sets the winch motor speed to our constant (which should be 0)
        winchMotor.set(Constants.ClimberSubsystemConstants.climberOffSpeed);
    }

    public void pistonIsPowered(boolean pistonOn){
        //if the piston is powered, power on the climber solenoid
        if(pistonOn){
            climbPiston.set(Value.kForward);
        }
        //otherwise, power the solenoid off
        else{
            climbPiston.set(Value.kOff);
        }
    }

    @Override
    public void periodic() {
        
    }
}
