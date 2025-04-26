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
    private PIDController manipulatorpid;
    public PIDController getManipulatorPid() {
        return manipulatorpid;
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
        manipulatorpid = new PIDController(
            Constants.L1CoralManipulatorConstants.L1CoralManipulatorp, //TODO: Tune PID
            Constants.L1CoralManipulatorConstants.L1CoralManipulatori, 
            Constants.L1CoralManipulatorConstants.L1CoralManipulatord
        );
        manipulatorpid.enableContinuousInput(0, 2* Math.PI);
        manipulatorpid.setTolerance(0.06);
    }

    public void setL1ManipulatorManipulatorSpeed(double speed){
        coralIntakeMotor.set(speed);
    }
    public double getL1ManipulatorMotorEncoder() {
        return rotationMotor.getAbsoluteEncoder().getPosition();
    }

    public void setL1ManipulatoRotationSpeed(double speed){

        double feedforward = Constants.L1CoralManipulatorConstants.pF * Math.sin(getL1ManipulatorMotorEncoder()-.25);
        if(manipulatorpid.atSetpoint()){
            feedforward = 0;
        }
        rotationMotor.set(speed - feedforward);
    }
    public void setRotationMotorSpeed(double speed){
        rotationMotor.set(speed);
    }
    private ManipulatorAngle target = ManipulatorAngle.UP;
    public void setManipulatorTarget(ManipulatorAngle var){
        //sets the manipulator's target angle using an encoder and a setpoint
        target  = var;
    }

    public ManipulatorAngle manipulatorTarget;
    public ManipulatorAngle getManipulatorTarget(){
        return manipulatorTarget;
    }

    public enum ManipulatorAngle{
        DOWN(Constants.L1CoralManipulatorConstants.enumManipulatorDOWN), 
        OUTTAKE(Constants.L1CoralManipulatorConstants.enumManipulatorOUTTAKE), 
        ALGAE(Constants.L1CoralManipulatorConstants.enumManipulatorALGAE), 
        UP(Constants.L1CoralManipulatorConstants.enumManipulatorUP),
        ALGAEOUTTAKE(Constants.L1CoralManipulatorConstants.enumALGAEOUTTAKE);
        double manipulatorEncoderValue;
        ManipulatorAngle(double val){
            this.manipulatorEncoderValue = val;
        }
        public double getEncoderValue(){
            return manipulatorEncoderValue;
        }
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        double wristSpeed = manipulatorpid.calculate(getManipulatorAbsoluteEncoderValue(),target.manipulatorEncoderValue);
        setL1ManipulatoRotationSpeed(wristSpeed);
        // if(target == ManipulatorAngle.UP && !manipulatorpid.atSetpoint()){
        //     setL1ManipulatorManipulatorSpeed(-0.06);
        // }
    }

}