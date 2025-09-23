// package frc.robot.subsystems;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// // "Tanner is stinky" -Luke
// public class FunnelSubsystem extends SubsystemBase{
//     //objects
   
//     DoubleSolenoid dropPiston;
//     private boolean setFunnelPiston;

//     private static FunnelSubsystem instance;

//     /**
//      * Returns the Scheduler instance.
//      *
//      * @return the instance
//     */
//     public static  FunnelSubsystem getInstance() {
//         if (instance == null) {
//             instance = new FunnelSubsystem();
//         }
//       return instance;
//     }

//     //Constructor
//     public FunnelSubsystem() {
//         //Pnumatics hub is module 20, and is a REVPH hub. the piston is plugged into 0 and 1 on the REVPH
//         this.dropPiston =  new DoubleSolenoid(20,PneumaticsModuleType.REVPH, 0, 1);
//     }

//     @Override
//     public void periodic() {
//     }
        
//     //power the piston
//     public void setFunnelPistonOn(){
//         dropPiston.set(DoubleSolenoid.Value.kForward);
//         this.setFunnelPiston = true;
//     }

//     //unpower the piston
//     public void setFunnelPistonOff(){
//         dropPiston.set(DoubleSolenoid.Value.kReverse);
//         this.setFunnelPiston = false;
//     }

//     public boolean isPistonDeployed(){
//         return setFunnelPiston;
//     }

//     //get commands for shuffleboard
//     public boolean getFunnelDropPiston() {
//         return dropPiston.equals(dropPiston);
//     }
// } 

// /* 
//   if (codeWorks == false){
//   isCrying = True
//   }
//  */