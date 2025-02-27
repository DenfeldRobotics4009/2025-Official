package frc.robot.autos;

import java.io.IOException;
import java.util.function.Supplier;

import org.json.simple.parser.ParseException;



import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.library.auto.pathing.FollowControllers;
import frc.library.auto.pathing.PurePursuitController;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.SetDrivePosition;
import frc.library.auto.pathing.field.GameField;
import frc.library.auto.pathing.pathObjects.Path;
import frc.robot.commands.CoralManipulatorOuttakeCommand;
import frc.robot.commands.SetElevatorTargetCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.CoralManipulatorSubsystem;
import frc.robot.subsystems.SwerveDrive;
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

public class OnePieceCageAuto extends SequentialCommandGroup {
    
    public OnePieceCageAuto(PurePursuitSettings config, Alliance alliance, GameField gameField){
        super(
            new ParallelCommandGroup(
            new SetDrivePosition(SwerveDrive.getInstance(), ()-> new Pose2d(7.265, 4.175, Rotation2d.fromDegrees(180))),
            new ParallelRaceGroup(
                new WaitCommand(10), 
                new FollowControllers(
                    new PurePursuitController(
                        new Path(config, alliance, 
                            new PathPoint(gameField, new Pose2d(7.265, 4.175, Rotation2d.fromDegrees(180)), 1),
                            new PathPoint(gameField, new Pose2d(6.5, 4.175, Rotation2d.fromDegrees(180)), .5),
                            new PathPoint(gameField, new Pose2d(5.814, 4.175, Rotation2d.fromDegrees(180)), 0)
            // new PathPoint(gameField, new Pose2d(6.965, 4.175, new Rotation2d(0)), 0)
                )), SwerveDrive.getInstance())
            ),

            new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(), ElevatorSubsystem.ElevatorSetpoint.P4, ElevatorSubsystem.WristAngle.UP)
            ),
            
            new WaitCommand(0.5),
            new CoralManipulatorOuttakeCommand(CoralManipulatorSubsystem.getInstance())

            
            // new SetDrivePosition(SwerveDrive.getInstance(), Path.getFromPathPlanner(config, alliance, "One Piece Cage Start").getStartingPoseSupplier()),
            // new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "One Piece Cage Start")), SwerveDrive.getInstance())
            // new ManipulatorOutputCommand(ManipulatorSubsystem.getInstance()),
            // new ParallelCommandGroup(new FollowControllers(new PurePursuitController(Path.getFromPathPlanner(config, alliance, "One Piece Cage End")), drivetrain),new SetElevatorTargetCommand(ElevatorSubsystem.getInstance(),ElevatorSubsystem.setpoint.ZERO))
        
       );
    }
}
