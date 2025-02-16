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
   private DigitalInput climberDownLimitSwitch;
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
        this.climberDownLimitSwitch = new DigitalInput(Constants.ClimberSubsystemConstants.climberDownlimitSwitchPort);
        //this.climbPiston = new DoubleSolenoid(null, 0, 0);
    }

    public double getRelativeEncoderValue(){
        return 0; // winchMotor.getAlternateEncoder().getPosition();
    }

    public boolean isAtBottom(){
        //bottom is false 
        return !climberDownLimitSwitch.get();
    }
    
    public void moveClimberUp(){
        winchMotor.set(Constants.ClimberSubsystemConstants.climberUpSpeed);
    }

    public void moveClimberDown(){
        //if limit switch is hit, you can't go down
        if(isAtBottom()){
            winchMotor.set(0);
        }
        else{
            winchMotor.set(Constants.ClimberSubsystemConstants.climberDownSpeed);
        }
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
