// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.autos.AutoTestBlue;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.library.auto.pathing.pathObjects.Path;
import frc.library.auto.pathing.pathObjects.PathPoint;
import frc.library.auto.pathing.PurePursuitSettings;
import frc.library.auto.pathing.field.FieldMirrorType;
import frc.library.auto.pathing.field.GameField;  

public enum Autos {

  /* ----------------- */
  /* Define Autos here */

    AUTOEXAMPLE(new SequentialCommandGroup());

    // AUTOTEST(new AutoTest()),
    
    // ONEPIECECAGEAUTO(new OnePieceCageAuto());

  /**
   * Drives the robot along the ExamplePath
   */ 
  
  /* ----------------- */

  /**
   * All autonomous routines above will be automatically inserted into
   * the autoChooser object labelled "Autonomous" within the tab "Autonomous".
   */
  final SequentialCommandGroup autoSequence;
  /**
   * Format for creating a new autonomous routine
   * @param autoSequence SequentialCommandGroup of any number of commands
   */
  Autos(SequentialCommandGroup autoSequence) {
      this.autoSequence = autoSequence;
  }

  public SequentialCommandGroup getSequence() {return autoSequence;}
}
