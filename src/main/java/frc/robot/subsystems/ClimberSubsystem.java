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
        //this.climbPiston = new DoubleSolenoid(null, 0, 0);
    }

    public double getRelativeEncoderValue(){
        return 0; // winchMotor.getAlternateEncoder().getPosition();
    }

    
    public void moveClimberUp(){
        winchMotor.set(Constants.ClimberSubsystemConstants.climberUpSpeed);
    }

    public void moveClimberDown(){
        winchMotor.set(Constants.ClimberSubsystemConstants.climberDownSpeed);
    }

    public void winchMotorOff(){
        winchMotor.set(Constants.ClimberSubsystemConstants.climberOffSpeed);
    }

    public void pistonIsPowered(boolean pistonOn){
        if(pistonOn){
            climbPiston.set(Value.kForward);
        }
        else{
            climbPiston.set(Value.kOff);
        }
    }

    @Override
    public void periodic() {
        
    }
}
