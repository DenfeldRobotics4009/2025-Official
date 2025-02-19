package frc.robot.subsystems;

import java.nio.file.attribute.PosixFileAttributeView;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeManipulator extends SubsystemBase{
    private SparkMax algaeManipulatorMotor;
    private DoubleSolenoid algaePiston;
    public AlgaeManipulator(){
        this.algaeManipulatorMotor = new SparkMax(Constants.AlgaeManipulatorConstants.algaeManipulatorMotorID, MotorType.kBrushless);
        this.algaePiston = new DoubleSolenoid(null,1,2); //TODO: Find out what to put for channels and module type
    }

    public void setAlgaeManipulatorPowered(double speed){
        algaeManipulatorMotor.set(speed);
    }
    public void algaePistonIsDeployed(boolean pistonOn){
        if(pistonOn){
            algaePiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            algaePiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
