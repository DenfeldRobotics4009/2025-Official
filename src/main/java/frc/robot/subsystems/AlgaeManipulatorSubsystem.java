package frc.robot.subsystems;

import java.nio.file.attribute.PosixFileAttributeView;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeManipulatorSubsystem extends SubsystemBase{
    private SparkMax algaeManipulatorMotor;
    private DoubleSolenoid algaePiston;
    private boolean setPiston;
    
    private static AlgaeManipulatorSubsystem instance;

    public static  AlgaeManipulatorSubsystem getInstance() {
        if (instance == null) {
          instance = new AlgaeManipulatorSubsystem();
        }
        return instance;
        }

    public AlgaeManipulatorSubsystem(){
        this.algaeManipulatorMotor = new SparkMax(Constants.AlgaeManipulatorConstants.algaeManipulatorMotorID, MotorType.kBrushless);
        this.algaePiston = new DoubleSolenoid(Constants.AlgaeManipulatorConstants.AlgaeManipulatorModule,
        PneumaticsModuleType.REVPH,
        Constants.AlgaeManipulatorConstants.AlgaeManipulatorForwardChannel,
        Constants.AlgaeManipulatorConstants.AlgaeManipulatorReverseChannel); //TODO: Find out what to put for channels and module type
    }

    public void setAlgaeManipulatorSpeed(double speed){
        algaeManipulatorMotor.set(speed);
    }
    public void setAlgaePiston(boolean pistonOn){
        if(pistonOn){
            algaePiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            algaePiston.set(DoubleSolenoid.Value.kReverse);
        }
        this.setPiston = pistonOn;
    }
    public boolean isPistonDeployed(){
        return setPiston;
    }
}