package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase{
   private SparkMax winchMotor;
   private DigitalInput climberLimitSwitch;
//    private Solenoid climberSolenoid;

   public ClimberSubsystem(){
    this.winchMotor = new SparkMax(0, null); //TODO: find actual device IDs
    this.climberLimitSwitch = new DigitalInput(0);
   }
public boolean getLimitSwitchHit(){
    return climberLimitSwitch.get();
}
public void moveClimberUp(){
    winchMotor.set(1);
}
public void moveClimberDown(){
    winchMotor.set(-1);
}
}
