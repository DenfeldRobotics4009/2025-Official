package frc.robot.commands;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.SwerveDrive;

public class AutoAlignRightCommand extends Command {
    
    public AutoAlignRightCommand(SwerveDrive swerveDrive){
      addRequirements(SwerveDrive.getInstance());
    }

    @Override
    public void execute() {
        
    }
    
    @Override
    public void initialize() {
      if(AprilTagOdometry.getInstance().bestTarget() == 10 || AprilTagOdometry.getInstance().bestTarget() == 21) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back 1"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 9 || AprilTagOdometry.getInstance().bestTarget() == 22) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back Right 3"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 8 || AprilTagOdometry.getInstance().bestTarget() == 17) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front Right 4"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 7 || AprilTagOdometry.getInstance().bestTarget() == 18) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front 6"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 6 || AprilTagOdometry.getInstance().bestTarget() == 19) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front Left 8"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 11 || AprilTagOdometry.getInstance().bestTarget() == 20) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back Left 11"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("No april tag target found");
      }
        
    }
    
    @Override
    public boolean isFinished() {
      return false;
    }
    
    @Override
    public void end(boolean interrupted) {
        
    }

}
