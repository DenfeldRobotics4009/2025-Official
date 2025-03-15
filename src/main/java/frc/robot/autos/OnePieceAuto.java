package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.CoralManipulatorOuttakeCommand;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.DriveStopCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.SwerveDrive;

public class OnePieceAuto extends SequentialCommandGroup{
    
    public OnePieceAuto(){
        super( 
            new ParallelRaceGroup(
                new WaitCommand(5),
                    new ParallelCommandGroup(
                        new DriveForwardCommand(),
                        new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(), ElevatorSubsystem.ElevatorSetpoint.P4, ElevatorSubsystem.WristAngle.UP)
                    )
                
            ),
            new CoralManipulatorOuttakeCommand(CoralManipulatorSubsystem.getInstance())
        );
    }
}

