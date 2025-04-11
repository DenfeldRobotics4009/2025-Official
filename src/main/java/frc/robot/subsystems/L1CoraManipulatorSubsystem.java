package frc.robot.subsystems;

import java.nio.file.attribute.PosixFileAttributeView;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.L1IntakeCommand;
import frc.robot.subsystems.ElevatorSubsystem.ElevatorSetpoint;
import frc.robot.subsystems.ElevatorSubsystem.WristAngle;

public class L1CoraManipulatorSubsystem extends SubsystemBase{
    private SparkMax rotationMotor;
    private SparkMax coralIntakeMotor;
    private PIDController Manipulatorpid;
    public PIDController getManipulatorPid() {
        return Manipulatorpid;
    }
    
    private static L1CoraManipulatorSubsystem instance;

    public static  L1CoraManipulatorSubsystem getInstance() {
        if (instance == null) {
          instance = new L1CoraManipulatorSubsystem();
        }
        return instance;
    }
    public double getManipulatorAbsoluteEncoderValue(){
        return rotationMotor.getAbsoluteEncoder().getPosition();
    }
    /**
     * Creates an algae manipulator subsystem.
     */
    public L1CoraManipulatorSubsystem(){
        this.rotationMotor = new SparkMax(
            Constants.L1CoralManipulatorConstants.L1CoralManipulatorRotationMotorID, 
            MotorType.kBrushless
        );
        this.coralIntakeMotor = new SparkMax(
            Constants.L1CoralManipulatorConstants.L1CoralManipulatorIntakeMotorID, 
            MotorType.kBrushless
        );
        Manipulatorpid = new PIDController(
            Constants.L1CoralManipulatorConstants.L1CoralManipulatorp, //TODO: Tune PID
            Constants.L1CoralManipulatorConstants.L1CoralManipulatori, 
            Constants.L1CoralManipulatorConstants.L1CoralManipulatord
        );
        Manipulatorpid.enableContinuousInput(0, 2* Math.PI);

        setManipulatorTarget(ManipulatorAngle.UP);
        setDefaultCommand(new RunCommand(() -> {
            double wristSpeed = L1CoraManipulatorSubsystem.getInstance().getManipulatorPid().calculate(L1CoraManipulatorSubsystem.getInstance().getManipulatorAbsoluteEncoderValue());
            L1CoraManipulatorSubsystem.getInstance().setL1ManipulatoRotationSpeed(wristSpeed);
        }, this));
    }

    public void setL1ManipulatorManipulatorSpeed(double speed){
        coralIntakeMotor.set(speed);
    }
    public double getL1ManipulatorMotorEncoder() {
        System.out.println(rotationMotor.getAbsoluteEncoder().getPosition());
        return rotationMotor.getAbsoluteEncoder().getPosition();
    }

    public void setL1ManipulatoRotationSpeed(double speed){
        System.out.println(speed + "  " + getL1ManipulatorMotorEncoder());
        if(getL1ManipulatorMotorEncoder() > Constants.L1CoralManipulatorConstants.enumManipulatorUP && speed > 0){
            return;
        }
        rotationMotor.set(speed);
    }

    public void setManipulatorTarget(ManipulatorAngle var){
        //sets the manipulator's target angle using an encoder and a setpoint
        Manipulatorpid.setSetpoint(var.manipulatorEncoderValue);
    }

    public ManipulatorAngle manipulatorTarget;
    public ManipulatorAngle getManipulatorTarget(){
        return manipulatorTarget;
    }

    public enum ManipulatorAngle{
        DOWN(Constants.L1CoralManipulatorConstants.enumManipulatorDOWN), 
        UP(Constants.L1CoralManipulatorConstants.enumManipulatorUP);
        double manipulatorEncoderValue;
        ManipulatorAngle(double val){
            this.manipulatorEncoderValue = val;
        }
        public double getEncoderValue(){
            return manipulatorEncoderValue;
        }
    }

}