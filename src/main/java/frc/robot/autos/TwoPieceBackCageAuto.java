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
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ManipulatorOutputCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;

public class TwoPieceBackCageAuto extends SequentialCommandGroup{
     public TwoPieceBackCageAuto(SwerveDrive drivetrain, PurePursuitSettings config, Alliance alliance) throws Throwable, IOException, ParseException{
        super(
            new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 1")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.P4)),
            new ManipulatorOutputCommand(ManipulatorSubsystem.getInstance()),
            new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 2")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.ZERO)),
            new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "2 piece back cage auto path 3")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.P4))

       );

    }
}
