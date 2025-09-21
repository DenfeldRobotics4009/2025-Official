package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FunnelSubsystem extends SubsystemBase{

    //objects

    private static FunnelSubsystem instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return the instance
    */
    public static  FunnelSubsystem getInstance() {
        if (instance == null) {
            instance = new FunnelSubsystem();
        }
      return instance;
    }

    //Constructor
    public FunnelSubsystem() {
        //Pnumatics hub is module 20, and is a REVPH hub. the piston is plugged into 0 and 1 on the REVPH
        
    }

    @Override
    public void periodic() {
    }

} 