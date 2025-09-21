// package frc.robot.commands;

// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.FunnelSubsystem;

// public class ToggleFunnelCommand extends Command {

// // objects
// private FunnelSubsystem m_funnelSubsystem;
// private boolean isDone = false;

// // constructor
// public ToggleFunnelCommand(FunnelSubsystem funnel) {
//     this.m_funnelSubsystem = funnel;
//     // Use addRequirements() here to declare subsystem dependencies.
//     addRequirements(funnel);
//   } 

// // ends the command
//     @Override
//     public void end(boolean interrupted) {
//         super.end(interrupted);
//     }

//     @Override
//     public void execute() {
//           //when activated makes funnel go up so we can intake
//           if(m_funnelSubsystem.isPistonDeployed()){
//             m_funnelSubsystem.setFunnelPiston(false);
//         } else{
//             m_funnelSubsystem.setFunnelPiston(true);
//         }
//         isDone = true;
//     }

//     @Override
//     public void initialize() {
//     }

//     @Override
//     public boolean isFinished() {
//         return isDone;
//     }
    
// }
