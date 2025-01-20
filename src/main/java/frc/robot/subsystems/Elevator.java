package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    SparkMax shaftMotorLeft;
    SparkMax shaftMotorRight;
    AbsoluteEncoder elevatorEncoder;

    private PIDController movePIDController = new PIDController(0, 0, 0); //TODO: Add PID values in

    public enum setpoint{
        ZERO(0), //TODO: Add Constants in for the levels
        TRANSFER(1),
        P1(2),
        P2(3),
        P3(4),
        P4(5);
        double encoderValue;
        setpoint(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    double offset;
    public Elevator(){
        //Resets encoder values
        shaftMotorLeft.getEncoder().setPosition(0);
        shaftMotorRight.getEncoder().setPosition(0);
    }
    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        super.periodic();
    }
}
