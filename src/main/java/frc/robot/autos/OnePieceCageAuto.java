package frc.robot.autos;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.library.auto.pathing.FollowControllers;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.pathObjects.Path;
import frc.robot.commands.FunnelDownCommand;
import frc.robot.subsystems.SwerveDrive;

public class OnePieceCageAuto extends SequentialCommandGroup {
    
    

    public OnePieceCageAuto(SwerveDrive drivetrain, PurePursuitSettings config, Alliance alliance) throws Throwable, IOException, ParseException{
        super(
            new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "ExamplePathFinal")), drivetrain),
            new FunnelDownCommand(null)

       );

    }
}
