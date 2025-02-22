package frc.robot.autos;

import java.io.IOException;

import org.json.simple.parser.ParseException;



import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.library.auto.pathing.FollowControllers;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.pathObjects.Path;
import frc.robot.commands.CoralManipulatorOuttakeCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;

public class OnePieceCageAuto extends SequentialCommandGroup {
    
    

    public OnePieceCageAuto(SwerveDrive drivetrain, PurePursuitSettings config, Alliance alliance) throws Throwable, IOException, ParseException{
        super(
            // new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "One Piece Cage Start")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.P4)),
            // new ManipulatorOutputCommand(ManipulatorSubsystem.getInstance()),
            // new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "One Piece Cage End")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.ZERO))

       );

    }
}
