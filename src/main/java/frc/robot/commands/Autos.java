// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public enum Autos {

  /* ----------------- */
  /* Define Autos here */

    AUTOEXAMPLE(new SequentialCommandGroup());

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
      new WaitCommand(0);
  }

  public SequentialCommandGroup getSequence() {return autoSequence;}
}
