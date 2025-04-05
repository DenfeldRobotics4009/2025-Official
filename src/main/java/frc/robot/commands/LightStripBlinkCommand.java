package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class LightStripBlinkCommand extends Command{
    public static boolean isBlinking = false;
    private final CoralManipulatorSubsystem subsystem;
    public LightStripBlinkCommand(CoralManipulatorSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public void execute() {
        isBlinking = true;
            new LightStripOnCommand(subsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}