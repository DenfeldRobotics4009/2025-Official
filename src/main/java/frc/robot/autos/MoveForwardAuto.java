package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.DriveStopCommand;
import frc.robot.subsystems.SwerveDrive;

public class MoveForwardAuto extends SequentialCommandGroup{
    
    public MoveForwardAuto(){
        super( 
            new ParallelRaceGroup(
            new WaitCommand(3), new DriveForwardCommand()
        ),
        new DriveStopCommand()
        );
    }
}

