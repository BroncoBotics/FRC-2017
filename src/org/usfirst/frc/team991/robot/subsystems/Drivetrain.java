package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {

	//Initialize controllers and sensors
	CANTalon front_left_motor, back_left_motor, front_right_motor, back_right_motor, gear_turner;
	RobotDrive drive;
	ADIS16448_IMU gyro;
	AnalogInput ultra;
	
	DigitalInput limit = new DigitalInput(6);
	boolean limit_has_pressed = false;
	
	boolean flipped = false;
	Relay light = new Relay(2);


	public Drivetrain() {
//		super("DriveTrain", 0.3, 0, 0, .3);
//		setSetpoint(0);
//		setInputRange(-1,1);
//		setOutputRange(-1,1);
		
		
		
		//Drive motor initialization
		front_left_motor = new CANTalon(RobotMap.frontleftMotor);
		back_left_motor = new CANTalon(RobotMap.backleftMotor);
		front_right_motor = new CANTalon(RobotMap.frontrightMotor);
		back_right_motor = new CANTalon(RobotMap.backrightMotor);
		
		gear_turner = new CANTalon(3);
		
		back_left_motor.changeControlMode(CANTalon.TalonControlMode.Follower);
		back_left_motor.set(front_left_motor.getDeviceID());
//		back_left_motor.reverseOutput(true);
		
		back_right_motor.changeControlMode(CANTalon.TalonControlMode.Follower);
		back_right_motor.set(front_right_motor.getDeviceID());
//		back_right_motor.reverseOutput(true);
		

    	
//		front_left_motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
//		front_right_motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        
		front_left_motor.reverseSensor(true);
		front_right_motor.reverseSensor(true);

		//Drive system
//		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
		drive = new RobotDrive(front_left_motor, front_right_motor);
		
		//Gyro initialization
		gyro = new ADIS16448_IMU();
		ultra = new AnalogInput(1);

	}
	
	public void lightOn() {
		light.set(Relay.Value.kForward);
	}
	
	public void lightOff() {
		light.set(Relay.Value.kOff);
	}
	
	protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return Robot.vision.adjustedCenter();
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	turn(-output);
    	
    }
//    
//    public void enablePID() {
//    	enable();
//    }
//    
//    public void disablePID() {
//    	disable();
//    }
    
    public boolean isLimitPressed() {
    	return !limit.get();
    }
    
    public void limitCheck() {
    	if (isLimitPressed()) {
    		limit_has_pressed = true;
    	}
    }
    
    public void resetLimit() {
    	limit_has_pressed = false;
    }
	
    public boolean limitHasPressed() {
    	return limit_has_pressed;
    }
    
	public CANTalon getLeft() {
		return front_left_motor;
	}
	public CANTalon getRight() {
		return front_right_motor;
	}

	public double getUltraDistance() {
		return ultra.getVoltage();
	}
	
	public void setTurner(double speed) {
		gear_turner.set(speed);
	}
	
	public void toggleForward() {
		if (flipped) {
			flipped = false;
		} else {
			flipped = true;
		}
	}

	//Sets arcade drive
	public void arcadeDriveTrigger(double right_trigger, double left_trigger, double x) {

		front_right_motor.changeControlMode(TalonControlMode.PercentVbus);
		front_left_motor.changeControlMode(TalonControlMode.PercentVbus);
		if (x <= .1 && x >= -.1) {
			x = 0;
		}
		if (flipped) {
			drive.arcadeDrive(-(right_trigger - left_trigger), -.4 * x, false);
		} else {
			drive.arcadeDrive(right_trigger - left_trigger, -.4 * x, false);
		}
		
		
	}
	
	public void arcadeDrive(double y, double x) {
		drive.arcadeDrive(y, x, false);
	}
	
	public void turn(double turn) {
		drive.arcadeDrive(0, turn, false);
	}
	
	public void tankDrive(double y_1, double y_2) {
		drive.tankDrive(y_1, y_2);
	}
	
	public double getLeftPosition() {
		return front_left_motor.getPosition();
	}
	
	public double getLeftVelocity() {
		return front_left_motor.getEncVelocity();
	}
	
	public double getRightPosition() {
		return front_right_motor.getPosition();
	}
	
	public double getRightVelocity() {
		return front_right_motor.getEncVelocity();
	}
	
	public void resetPosition() {
		front_right_motor.setPosition(0);
		front_left_motor.setPosition(0);
	}

	//Stops drivetrain
	public void stop() {
		drive.arcadeDrive(0, 0, false);
	}

	//Resets gyro to zero
	public void resetGryo() {
		gyro.reset();
	}

	//Gets currect gyro angle
	public double getGyroAngle() {
		return gyro.getAngleZ();
	}
	
	public void calibrateGyro() {
		gyro.calibrate();
	}

	//Sets the default command of subsystem
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
}

