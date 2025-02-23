package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.library.auto.pathing.FollowControllers;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.field.GameField;
import frc.library.auto.pathing.pathObjects.Path;
import frc.library.auto.pathing.pathObjects.PathPoint;
import frc.robot.subsystems.SwerveDrive;

public class AutoTest extends SequentialCommandGroup{
    public AutoTest(PurePursuitSettings config, Alliance alliance, GameField gameField){
        super(
            new FollowControllers(new PurePursuitController(new Path(config, alliance, 
            new PathPoint(gameField, new Pose2d(0, 0, new Rotation2d(0)), 1),
            new PathPoint(gameField, new Pose2d(0.5, 0, new Rotation2d(0)), 1),
            new PathPoint(gameField, new Pose2d(1, 0, new Rotation2d(0)), 1),
            new PathPoint(gameField, new Pose2d(1, 1, new Rotation2d(0)), 0)

            )), SwerveDrive.getInstance())
        );
    }
}
