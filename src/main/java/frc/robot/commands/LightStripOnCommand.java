package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class LightStripOnCommand extends Command{
    private final CoralManipulatorSubsystem subsystem;
    public LightStripOnCommand(CoralManipulatorSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void end(boolean interrupted) {
        subsystem.setLightStripPowered(true);
    }

    @Override
    public void execute() {
        subsystem.setLightStripPowered(true);
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