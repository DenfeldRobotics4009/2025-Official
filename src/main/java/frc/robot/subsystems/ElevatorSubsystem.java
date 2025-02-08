package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ElevatorControllerCommand;

public class ElevatorSubsystem extends SubsystemBase {
    //Creates components of the Elevator
    //Positive value is up
    private SparkMax shaftMotor;
    private DigitalInput bottomLimitSwitch;
    private static ElevatorSubsystem instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return the instance
     */
    public static  ElevatorSubsystem getInstance() {
      if (instance == null) {
        instance = new ElevatorSubsystem();
      }
      return instance;
      }

    public SparkMax getShaftMotor() {
        return shaftMotor;
    }
    private RelativeEncoder elevatorEncoder;
    public RelativeEncoder getRelativeEncoder(){
        return elevatorEncoder;
    }
    private PIDController pid;
    public PIDController getPid() {
        return pid;
    }
    private double offset;

    public ElevatorSubsystem(){
        bottomLimitSwitch = new DigitalInput(9);
        shaftMotor = new SparkMax(11, MotorType.kBrushless); //TODO: get Spark IDs
        elevatorEncoder = shaftMotor.getEncoder();
        pid = new PIDController(offset, offset, offset);
        setTarget(setpoint.ZERO);
      //  setDefaultCommand(new ElevatorControllerCommand(this));
    }

    public boolean isAtBottom(){
        return bottomLimitSwitch.get();
    }
    
    public void setTarget(setpoint var){
        pid.setSetpoint(offset+var.encoderValue);
    }

    //Creates setpoints for the elevator to reach
    public enum setpoint{
        ZERO(Constants.ElevatorSubsystemConstants.enumPointZero), 
        P2(Constants.ElevatorSubsystemConstants.enumP2),
        P3(Constants.ElevatorSubsystemConstants.enumP3),
        P4(Constants.ElevatorSubsystemConstants.enumP4);
        double encoderValue;
        setpoint(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    public void runMotor(double speed){
        shaftMotor.set(speed);

    }
    public void setOffset(double newOffset){
        this.offset = newOffset;
    }
    public void resetOffset(){
        this.offset = 0;
    }
    @Override
    public void periodic() {
        System.out.println(isAtBottom());
    }
}
