package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Autos;

/**
 * A singleton object handling autonomous routine
 * shuffleboard data
 */
public class AutoShuffleboardTab {
    static AutoShuffleboardTab instance;

    // Shuffleboard object for selecting autonomous routines
    SendableChooser<Command> autoChooser = new SendableChooser<>();

    // Tab to display autonomous data
    public static final ShuffleboardTab autoTab = Shuffleboard.getTab("Autonomous");
    // Entries for auto data
    public static final GenericEntry 
        lookAheadEntry = autoTab.add("Look Ahead", 0)
        .withPosition(0, 4).withSize(20, 4).withWidget("Graph").getEntry(), 
        speedEntry = autoTab.add("State Speed", 0)
        .withPosition(0,0).withSize(10, 4).withWidget("Graph").getEntry(),
        
        /**
         * If this graph is negative, the robot couldn't speed up fast enough
         * If this graph is positive, the robot couldn't slow down fast enough
         * The latter is a problem, the former is not (:
         */
        distanceFromGoalEntry = autoTab.add("Distance to Goal", 0)
        .withPosition(10,0).withSize(10, 4).withWidget("Graph").getEntry(),
        lastCrossedPointEntry = autoTab.add("Last Crossed Point Index", 0)
        .withPosition(20, 2).withSize(5, 1).getEntry();

    /**
     * Constructs auto tab, and initializes autoChooser
     */
    private AutoShuffleboardTab() {
        
        // For convenience a programmer could change this when going to competition.
        boolean isCompetition = true;

        // Build an auto chooser. This will use Commands.none() as the default option.
        // As an example, this will only show autos that start with "comp" while at
        // competition as defined by the programmer
        autoChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
        (stream) -> isCompetition
            ? stream.filter(auto -> auto.getName().startsWith("comp"))
            : stream
        );
        
        //autoTab.add("Autonomous", autoChooser).withPosition(20, 0).withSize(5, 2);
    }

    /**
     * @return AutoShuffleboardTab instance
     */
    public static AutoShuffleboardTab getInstance() {
        if (instance == null) {
            // Construct if not yet constructed
            instance = new AutoShuffleboardTab();
        }

        return instance;
    }

    /**
     * @return currently selected autonomous routine within shuffleboard
     */
    public Command getSelectedAuto() {
        return autoChooser.getSelected();
    }
}