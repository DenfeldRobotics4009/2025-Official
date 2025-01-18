package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    SparkMax shaftMotorLeft;
    SparkMax shaftMotorRight;
    AbsoluteEncoder elevatorEncoder;
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
}
