package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ElevatorControllerCommand;

public class ElevatorSubsystem extends SubsystemBase {
    //Creates components of the Elevator
    //Positive value is up
    private SparkMax shaftMotor;
    private DigitalInput bottomLimitSwitch;
    private Encoder encoder;
    private double offset = 0;
    private static ElevatorSubsystem instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return the instance
          * @throws Exception If our motors are not configured, fail creating instance
          */
         public static  ElevatorSubsystem getInstance() throws Exception {
      if (instance == null) {
        instance = new ElevatorSubsystem();
      }
      return instance;
      }

    public SparkMax getShaftMotor() {
        return shaftMotor;
    }
    public double getRelativeEncoderValue(){
        return encoder.getDistance()-offset;
    }
    private PIDController pid;
    public PIDController getPid() {
        return pid;
    }

    public ElevatorSubsystem() throws Exception{
        //plugged into DIO 9 on roborio
        bottomLimitSwitch = new DigitalInput(9);
        
        shaftMotor = new SparkMax(Constants.ElevatorSubsystemConstants.ElevatormotorID, MotorType.kBrushless); //TODO: get Spark IDs
        //get the elevator follower for sanity checks
        SparkMax shaftMotorFollower = new SparkMax(Constants.ElevatorSubsystemConstants.ElevatormotorFollowerID, MotorType.kBrushless); 
        
        //make sure the follower is following The Elevator Motor ID, its inverted from the elevator motor
        //The elevator motor is inverted because that sets "up" to be positive
        if(!(shaftMotorFollower.isFollower() 
        &&shaftMotorFollower.configAccessor.getFollowerModeLeaderId() == Constants.ElevatorSubsystemConstants.ElevatormotorID
        && shaftMotorFollower.configAccessor.getFollowerModeInverted() 
        && shaftMotor.configAccessor.getInverted())){
            throw new Exception("Evelator Motors not set up");
        }
        //The PID controller setup. 
        //kp is how much to multiply speed by the farther it is away. 
        //Example: 1 tick away is .0005 faster than the previous tick.
        //ki is how much to speed up the longer it takes to get there
        //example: every loop add .0001 to the speed untill we reach our target
        //kd is to slow down the faster we go
        pid = new PIDController(.0005, 0, 0);
        encoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
        setTarget(setpoint.ZERO);
        //setDefaultCommand(new ElevatorControllerCommand(this));
    }

    public boolean isAtBottom(){
        //bottom is false
        return !bottomLimitSwitch.get();
    }
    
    public void setTarget(setpoint var){
        pid.setSetpoint(offset+var.encoderValue);
    }

    //Creates setpoints for the elevator to reach
    public enum setpoint{
        ZERO(Constants.ElevatorSubsystemConstants.enumPointZero), 
        P2(Constants.ElevatorSubsystemConstants.enumP2),
        P3(Constants.ElevatorSubsystemConstants.enumP3),
        P4(Constants.ElevatorSubsystemConstants.enumP4);
        double encoderValue;
        setpoint(double val){
            this.encoderValue = val;
        }
        public double getEncoderValue(){
            return encoderValue;
        }
    }
    public void runMotor(double speed){
        //Check to see that if we are  above our max height and going up, we stop. If we are above and going down that is ok
        if(encoder.getDistance() >= Constants.ElevatorSubsystemConstants.maxHeight && speed > 0){
            speed = 0;
        }
        //get a temp variable to hold max speed
        double maxSpeed = Constants.ElevatorSubsystemConstants.maxSpeed;
        //make sure we only send -1 to 1
        speed = Math.max(-maxSpeed, speed);
        speed = Math.min(maxSpeed, speed);
        shaftMotor.set(speed);

    }
    private void setOffset(double newOffset){
        this.offset = newOffset;
    }
    public void resetOffset(){
        this.offset = 0;
    }
    @Override
    public void periodic() {
    //    System.out.println(isAtBottom());
        System.out.println( "encoder: " +getRelativeEncoderValue());
        System.out.println("pid "+pid.calculate (getRelativeEncoderValue()));

        //if we are at the bottom, reset encoder so 0 is the bottom of the elevator
        if(isAtBottom()){
            setOffset(encoder.getDistance());
        } 
    }
}
