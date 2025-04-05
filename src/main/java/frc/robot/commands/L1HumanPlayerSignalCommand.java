package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CoralManipulatorSubsystem;

public class L1HumanPlayerSignalCommand extends Command{
    private final CoralManipulatorSubsystem subsystem;
    public L1HumanPlayerSignalCommand(CoralManipulatorSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void end(boolean interrupted) {
        
    }

    @Override
    public void execute() {
        new SequentialCommandGroup(
            new ParallelRaceGroup(
                new LightStripOnCommand(subsystem),
                new WaitCommand(0.2)
            ),
            new ParallelRaceGroup(
                new LightStripOffCommand(subsystem),
                new WaitCommand(0.2)
            ),
            new ParallelRaceGroup(
                new LightStripOnCommand(subsystem),
                new WaitCommand(0.2)
            ),
            new ParallelRaceGroup(
                new LightStripOffCommand(subsystem),
                new WaitCommand(0.2)
            ),
            new ParallelRaceGroup(
                new LightStripOnCommand(subsystem),
                new WaitCommand(0.2)
            ),
            new ParallelRaceGroup(
                new LightStripOffCommand(subsystem),
                new WaitCommand(0.2)
            )
        );
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}