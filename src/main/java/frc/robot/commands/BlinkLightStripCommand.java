package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import edu.wpi.first.wpilibj.Timer;

public class BlinkLightStripCommand extends Command {
    public static boolean isBlinking = false;
    public int numBlinks = 0;
    public Timer blinkTimer = new Timer();

    private final CoralManipulatorSubsystem subsystem;
    public BlinkLightStripCommand(CoralManipulatorSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }
    @Override
    public void end(boolean interrupted) {
        blinkTimer.stop();
        numBlinks = 0;
        isBlinking = false;
    }

    @Override
    public void execute() {
        if (blinkTimer.hasElapsed(0.5)) {
            subsystem.setLightStripPowered(false);
            blinkTimer.reset();
            numBlinks++;
        }
        else if (blinkTimer.hasElapsed(0.25)) {
            subsystem.setLightStripPowered(true);
        }
    
    }

    @Override
    public void initialize() {
        blinkTimer.reset();
        blinkTimer.start();
        isBlinking = true;
    }

    @Override
    public boolean isFinished() {
        return numBlinks == 8;
    }

}