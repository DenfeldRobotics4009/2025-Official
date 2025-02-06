package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ElevatorControllerCommand;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax shaftMotor;
    public SparkMax getShaftMotor() {
        return shaftMotor;
    }
    private RelativeEncoder elevatorEncoder;
    public RelativeEncoder gRelativeEncoder(){
        return elevatorEncoder;
    }
    private PIDController pid;
    public PIDController getPid() {
        return pid;
    }
    private double offset;

    public ElevatorSubsystem(){
        shaftMotor = new SparkMax(22, MotorType.kBrushless); //TODO: get Spark IDs
        elevatorEncoder = shaftMotor.getEncoder();
        pid = new PIDController(offset, offset, offset);
        setTarget(setpoint.ZERO);
        setDefaultCommand(new ElevatorControllerCommand(this));
        // DigitalInput zeroLimitSwitch = new DigitalInput(0);
    }
    
    public void setTarget(setpoint var){
        pid.setSetpoint(offset+var.encoderValue);
    }

    //Creates setpoints for the elevator to reach
    public enum setpoint{
        ZERO(0), //TODO: Add Constants in for the levels
        P2(1),
        P3(2),
        P4(3);
        double encoderValue;
        setpoint(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    public void setOffset(double newOffset){
        this.offset = newOffset;
    }
    public void resetOffset(){
        this.offset = 0;
    }
    @Override
    public void periodic() {
        // TODO Auto-generated method stub
    }
}
