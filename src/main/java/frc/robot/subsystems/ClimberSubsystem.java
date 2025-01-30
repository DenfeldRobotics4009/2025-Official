package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase{
   private SparkMax winchMotor;
   private DigitalInput climberUpLimitSwitch;
   private DigitalInput climberDownLimitSwitch;
//    private Solenoid climberSolenoid;

   public ClimberSubsystem(){
    this.winchMotor = new SparkMax(0, null); //TODO: find actual device IDs
    this.climberUpLimitSwitch = new DigitalInput(0);
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
