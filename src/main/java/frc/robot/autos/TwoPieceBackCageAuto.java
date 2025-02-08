package frc.robot.autos;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.library.auto.pathing.FollowControllers;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.pathObjects.Path;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ManipulatorOutputCommand;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;

public class TwoPieceBackCageAuto extends SequentialCommandGroup{
     public TwoPieceBackCageAuto(SwerveDrive drivetrain, PurePursuitSettings config, Alliance alliance) throws Throwable, IOException, ParseException{
        super(
            new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 1")), drivetrain),
            new ManipulatorOutputCommand(ManipulatorSubsystem.getInstance()),
            new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 2")), drivetrain),
            new IntakeCommand(ManipulatorSubsystem.getInstance()),
            new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 3")), drivetrain)

       );

    }
}
