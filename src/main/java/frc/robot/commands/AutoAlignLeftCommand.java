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

public class AutoAlignLeftCommand extends Command {
    
    public AutoAlignLeftCommand(SwerveDrive swerveDrive){
      addRequirements(SwerveDrive.getInstance());
    }

    @Override
    public void execute() {
      // this is giving me an aneurysm
      if(AprilTagOdometry.getInstance().bestTarget() == 10 || AprilTagOdometry.getInstance().bestTarget() == 21) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back 12"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 9 || AprilTagOdometry.getInstance().bestTarget() == 22) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back Right 2"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 8 || AprilTagOdometry.getInstance().bestTarget() == 17) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front Right 5"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 7 || AprilTagOdometry.getInstance().bestTarget() == 18) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front 7"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 6 || AprilTagOdometry.getInstance().bestTarget() == 19) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Front Left 9"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else if (AprilTagOdometry.getInstance().bestTarget() == 11 || AprilTagOdometry.getInstance().bestTarget() == 20) {
        try {
          AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile("Back Left 10"),
          Constants.DriveConstants.pathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("No april tag target found");
      }
        
    }
    
    @Override
    public void initialize() {
        
    }
    
    @Override
    public boolean isFinished() {
      return false;
    }
    
    @Override
    public void end(boolean interrupted) {
        
    }

}
