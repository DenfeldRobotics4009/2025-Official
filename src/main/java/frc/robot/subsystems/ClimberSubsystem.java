package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase{
   private SparkMax winchMotor;
   private DigitalInput climberDownLimitSwitch;
   

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
        this.winchMotor = new SparkMax(Constants.ClimberSubsystemConstants.winchMotorDeviceID, null); 
        this.climberDownLimitSwitch = new DigitalInput(Constants.ClimberSubsystemConstants.climberDownlimitSwitchID);
    }
    public double getRelativeEncoderValue(){
        return winchMotor.getAlternateEncoder().getPosition();
    }
    public boolean getDownLimitSwitchHit(){
        return climberDownLimitSwitch.get();
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

    @Override
    public void periodic() {
        System.out.println(getRelativeEncoderValue());
    }
}
