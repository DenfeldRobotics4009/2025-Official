package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase{
   private SparkMax winchMotor;
   private DigitalInput climberUpLimitSwitch;
   private DigitalInput climberDownLimitSwitch;
//    private Solenoid climberSolenoid;

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
    this.climberUpLimitSwitch = new DigitalInput(Constants.ClimberSubsystemConstants.climberUplimitSwitchID);
   }
public boolean getUpLimitSwitchHit(){
    return climberUpLimitSwitch.get();
}
public boolean getDownLimitSwitchHit(){
    return climberDownLimitSwitch.get();
}
public void moveClimberUp(){
    winchMotor.set(1);
}
public void moveClimberDown(){
    winchMotor.set(-1);
}
public void winchMotorOff(){
    winchMotor.set(0);
}
}
