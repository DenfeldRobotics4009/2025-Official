package frc.robot.commands;

import org.ejml.equation.Symbol;

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
        if(subsystem.getShortFunnelSensor()){
            subsystem.setLightStripPowered(true);
        }
        else if(!subsystem.getShortFunnelSensor()){
            subsystem.setLightStripPowered(false);
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