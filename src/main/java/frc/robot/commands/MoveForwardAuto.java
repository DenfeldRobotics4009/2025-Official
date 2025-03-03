package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Configs.MAXSwerveModule;
import frc.robot.subsystems.SwerveDrive;

public class MoveForwardAuto extends Command{
    
    @Override
    public void end(boolean interrupted) {

    }
    @Override
    public void execute() {
        // new SequentialCommandGroup(
        //     new ParallelCommandGroup(
        //         new SwerveDrive().getInstance().drive(1, 0, 0, false),
        //         new WaitCommand(1);
        //     ),
        //     new SwerveDrive().getInstance().drive(0, 0, 0, false);
        // );
    }
    @Override
    public void initialize() {
    }
}

