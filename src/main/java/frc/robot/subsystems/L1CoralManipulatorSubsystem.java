package frc.robot.subsystems;

import java.nio.file.attribute.PosixFileAttributeView;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class L1CoralManipulatorSubsystem extends SubsystemBase{
    private SparkMax l1CoralManipulatorMotor;
    private DoubleSolenoid l1CoralPiston;
    private boolean setPiston;
    
    private static L1CoralManipulatorSubsystem instance;

    public static  L1CoralManipulatorSubsystem getInstance() {
        if (instance == null) {
          instance = new L1CoralManipulatorSubsystem();
        }
        return instance;
    }
    /**
     * Creates an algae manipulator subsystem.
     */
    public L1CoralManipulatorSubsystem(){
        this.l1CoralManipulatorMotor = new SparkMax(
            Constants.AlgaeManipulatorConstants.algaeManipulatorMotorID, 
            MotorType.kBrushless
        );
        this.l1CoralPiston = new DoubleSolenoid(
            Constants.AlgaeManipulatorConstants.AlgaeManipulatorModule,
            PneumaticsModuleType.REVPH,
            Constants.AlgaeManipulatorConstants.AlgaeManipulatorForwardChannel,
            Constants.AlgaeManipulatorConstants.AlgaeManipulatorReverseChannel
        );
    }

    public void setAlgaeManipulatorSpeed(double speed){
        l1CoralManipulatorMotor.set(speed);
    }

    public void setAlgaePiston(boolean pistonOn){
        if(pistonOn){
            l1CoralPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            l1CoralPiston.set(DoubleSolenoid.Value.kReverse);
        }
        this.setPiston = pistonOn;
    }
    
    public boolean isPistonDeployed(){
        return setPiston;
    }
}