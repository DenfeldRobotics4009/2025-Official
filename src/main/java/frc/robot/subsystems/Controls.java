package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Controls {
    //creates xbox controllers for the driver and operator that are in ports 0 and 1 in driver station
       public final XboxController driveController = new XboxController(0);
       public final XboxController operateController = new XboxController(1);
       public final XboxController numPad = new XboxController(2);

       //creates a trigger to use for controls in robot container
       public Trigger getOperatePOVTrigger(int direction) {
        return new Trigger(() -> {return operateController.getPOV() == direction;});
    }

    public Trigger getNumpadPOVTrigger(int direction) {
        return new Trigger(() -> {return numPad.getPOV() == direction;});
    }
}