package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
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
    private SparkMax wristMotor;

    private DigitalInput bottomLimitSwitch;
    private Encoder elevatorEncoder;

    SlewRateLimiter elevatorRampRate = new SlewRateLimiter(2);

    //this is a offset value to move all setpoints up or down
    private double offset = 0;
    private static ElevatorSubsystem instance;

    /**
     * Returns the Scheduler instance.
     *
     * @return the instance
          * @throws Exception If our motors are not configured, fail creating instance
          */
         public static  ElevatorSubsystem getInstance() {
      if (instance == null) {
        instance = new ElevatorSubsystem();
      }
      return instance;
      }

    public double getElevatorRelativeEncoderValue(){
        return elevatorEncoder.getDistance();
    }
    public double getWristAbsoluteEncoderValue(){
        return wristMotor.getAbsoluteEncoder().getPosition();
    }
    private PIDController Elevatorpid;
    public PIDController getElevatorPid() {
        return Elevatorpid;
    }
    private PIDController Wristpid;
    public PIDController getWristPid() {
        return Wristpid;
    }

    Boolean ERROR = false;
    /**
     * 
     * @throws Exception if our motors are not set up correctly in REV client
     */
    public ElevatorSubsystem(){
        
        //plugged into DIO 9 on roborio
        bottomLimitSwitch = new DigitalInput(Constants.ElevatorSubsystemConstants.ElevatorLimitSwitchPort);
        
        shaftMotor = new SparkMax(Constants.ElevatorSubsystemConstants.ElevatormotorID, MotorType.kBrushless);
        wristMotor = new SparkMax(Constants.ElevatorSubsystemConstants.wristMotorID, MotorType.kBrushless); 
        
        //get the elevator follower for sanity checks
        SparkMax shaftMotorFollower = new SparkMax(Constants.ElevatorSubsystemConstants.ElevatormotorFollowerID, MotorType.kBrushless); 
        
        //make sure the follower is following The Elevator Motor ID, its inverted from the elevator motor
        //The elevator motor is inverted because that sets "up" to be positive
        if(!(shaftMotorFollower.isFollower() 
        &&shaftMotorFollower.configAccessor.getFollowerModeLeaderId() == Constants.ElevatorSubsystemConstants.ElevatormotorID
        && shaftMotorFollower.configAccessor.getFollowerModeInverted() 
        && shaftMotor.configAccessor.getInverted())){
            ERROR = true;
            //throw new Exception("Evelator Motors not set up");
        }
        
        //The PID controller setup. 
        //kp is how much to multiply speed by the farther it is away. 
        //Example: 1 tick away is .0005 faster than the previous tick.
        //ki is how much to speed up the longer it takes to get there
        //example: every loop add .0001 to the speed untill we reach our target
        //kd is to slow down the faster we go
        Elevatorpid = new PIDController(
            Constants.ElevatorSubsystemConstants.Elevatorp, 
            Constants.ElevatorSubsystemConstants.Elevatori, 
            Constants.ElevatorSubsystemConstants.Elevatord
        );
        Wristpid = new PIDController(
            Constants.ElevatorSubsystemConstants.Wristp, 
            Constants.ElevatorSubsystemConstants.Wristi, 
            Constants.ElevatorSubsystemConstants.Wristd
        );
        Wristpid.enableContinuousInput(0, 2* Math.PI);
        //declares the encoder
        elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
        
        //The default position of the elevator is at zero and the default wrist position is at down
        setElevatorTarget(ElevatorSetpoint.ZERO);
        setWristTarget(WristAngle.DOWN);
        setDefaultCommand(new ElevatorControllerCommand(this));
    }

    public boolean isAtBottom(){
        //bottom is false 
        return !bottomLimitSwitch.get();
    }

    public void setElevatorTarget(ElevatorSetpoint var){
        //sets our elevator target using offset and the setpoint encoder value.
        Elevatorpid.setSetpoint(offset+var.elevatorEncoderValue);
        elevatorTarget = var;
    }

    public ElevatorSetpoint elevatorTarget;

    public ElevatorSetpoint getElevatorTarget(){
        return elevatorTarget;
    }

    public void setWristTarget(WristAngle var){
        //sets the wrist's target angle using an encoder and a setpoint
        Wristpid.setSetpoint(var.wristEncoderValue);
    }

    //Creates setpoints for the elevator to reach
    public enum ElevatorSetpoint{
        ZERO(Constants.ElevatorSubsystemConstants.enumPointZero), 
        LOW_ALGAE(Constants.ElevatorSubsystemConstants.enumPointLowAlgae),
        P2(Constants.ElevatorSubsystemConstants.enumP2),
        P3(Constants.ElevatorSubsystemConstants.enumP3),
        P4(Constants.ElevatorSubsystemConstants.enumP4);
        double elevatorEncoderValue;
        ElevatorSetpoint(int val){
            this.elevatorEncoderValue = val;
        }
        public double getEncoderValue(){
            return elevatorEncoderValue;
        }
    }
    //creates positions for the wrist to be at
    public enum WristAngle{
        DOWN(Constants.ElevatorSubsystemConstants.wristDown), 
        UP(Constants.ElevatorSubsystemConstants.wristUp),
        TOPALGAEREMOVAL(Constants.ElevatorSubsystemConstants.topWristAlgae),
        MOVING(Constants.ElevatorSubsystemConstants.wristMoving),
        BOTTOMALGAEREMOVAL(Constants.ElevatorSubsystemConstants.bottomWristAlgae);
        double wristEncoderValue;
        WristAngle(double val){
            this.wristEncoderValue = val;
        }
        public double getEncoderValue(){
            return wristEncoderValue;
        }
    }
    public void runWristMotor(double speed){
        //Check to see that if we are  above our max height and going up, we stop. If we are above and going down that is ok
        if(getWristAbsoluteEncoderValue() >= Constants.ElevatorSubsystemConstants.wristMaxAngle && speed > 0){
            speed = 0;
        }
        if(getWristAbsoluteEncoderValue() <= Constants.ElevatorSubsystemConstants.wristMinAngle && speed < 0){
            speed = 0;
        }
        wristMotor.set(-speed-Constants.ElevatorSubsystemConstants.WristF * Math.sin(getWristAbsoluteEncoderValue()));
        double minAngle = Constants.ElevatorSubsystemConstants.wristMinAngle;
        double maxAngle = Constants.ElevatorSubsystemConstants.wristMaxAngle;
    }
    
    public void runElevatorMotor(double speed){
        speed = elevatorRampRate.calculate(speed);
        //Check to see that if we are  above our max height and going up, we stop. If we are above and going down that is ok
        if(getElevatorRelativeEncoderValue() >= Constants.ElevatorSubsystemConstants.maxHeight && speed > 0){
            speed = 0;
        }

        //if limit switch is hit, you can't go down
        if(isAtBottom() && speed < 0){
            speed = 0;
        }
        //get a temp variable to hold max speed
        double maxSpeed = Constants.ElevatorSubsystemConstants.maxSpeed;
        //make sure we only send -1 to 1
        speed = Math.max(-maxSpeed, speed);
        speed = Math.min(maxSpeed, speed);
        shaftMotor.set(speed);
    }
    public void setOffset(double newOffset){
        this.offset = newOffset;
        setElevatorTarget(elevatorTarget);
    }

    public double getOffset(){
        return offset;
    }
    public void resetOffset(){
        this.offset = 0;
    }
    @Override
    public void periodic() {
        //if we are at the bottom, reset encoder so 0 is the bottom of the elevator and set our offset to 0
        if(isAtBottom()){
            elevatorEncoder.reset();
            setOffset(0);
        } 
    }

    //get commands for shuffleboard
    public double shaftMotorSpeed(){
        return shaftMotor.get();
    }
    public double wristMotorSpeed(){
        return wristMotor.get();
    }
}
