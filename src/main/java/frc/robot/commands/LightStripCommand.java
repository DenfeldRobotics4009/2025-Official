package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class LightStripCommand extends Command{
    private final CoralManipulatorSubsystem subsystem;
    public LightStripCommand(CoralManipulatorSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public void execute() {
        if (BlinkLightStripCommand.isBlinking = false) {
            if(subsystem.getCoralManipulatorSensor()){
                subsystem.setLightStripPowered(true);
            }
            else if(!subsystem.getCoralManipulatorSensor()){
                subsystem.setLightStripPowered(false);
            }
        }
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

}