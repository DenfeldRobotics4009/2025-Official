package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Controls {
       public final XboxController driveController = new XboxController(0);
       public final XboxController operateController = new XboxController(1);

       public Trigger getOperatePOVTrigger(int direction) {
        return new Trigger(() -> {return operateController.getPOV() == direction;});
    }
}
