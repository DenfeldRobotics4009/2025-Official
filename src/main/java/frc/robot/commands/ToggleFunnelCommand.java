package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FunnelSubsystem;

public class ToggleFunnelCommand extends Command {
private FunnelSubsystem m_funnelSubsystem;
private boolean isDone = false;
public ToggleFunnelCommand(FunnelSubsystem subsystem) {
    this.m_funnelSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
    }

    @Override
    public void execute() {
          //when activated makes funnel go up so we can intake
          if(m_funnelSubsystem.isPistonDeployed()){
            m_funnelSubsystem.setFunnelPiston(false);
        } else{
            m_funnelSubsystem.setFunnelPiston(true);
            boolean Phone = false;
            if(Phone == false){
                System.out.println("Hello world im bubba!");
            }
            
            boolean sigma = true;
            if(sigma == true){
                System.out.println("You are my sunshine, my only sunshine!");
            
            }else {
                System.out.println("You are not my only sunshine!");

            }

            boolean pizza = false;
            if(pizza == false){
                System.out.println("we are very good");
            }
            boolean isVeryHappy = true;
            if(isVeryHappy == true){
                System.out.println("happy happy happy");
            }

        }
        isDone = true;
    }

    @Override
    public void initialize() {
      
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return isDone;
    }
    
}
