package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToGear extends Command {
	
	CANTalon left_motor, right_motor;
	double distance;

    public DriveToGear() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        setTimeout(5);

        left_motor = Robot.drivetrain.getLeft();
        right_motor = Robot.drivetrain.getRight();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGryo();
    	left_motor.setEncPosition(0);
		right_motor.setEncPosition(0);
		

    	left_motor.reverseSensor(true);
    	right_motor.reverseSensor(true);
		
		
		left_motor.configNominalOutputVoltage(+0f, -0f);
		right_motor.configNominalOutputVoltage(+0f, -0f);
		
		left_motor.configPeakOutputVoltage(+12f, -12f);
		right_motor.configPeakOutputVoltage(+12f, -12f);
		
		left_motor.setAllowableClosedLoopErr(0); /* always servo */
	    /* set closed loop gains in slot0 */
		left_motor.setProfile(0);
		left_motor.setF(0.0);
		left_motor.setP(0.1);
		left_motor.setI(0.0); 
		left_motor.setD(0.0);    
		
		right_motor.setAllowableClosedLoopErr(0); /* always servo */
	    /* set closed loop gains in slot0 */
		right_motor.setProfile(0);
		right_motor.setF(0.0);
		right_motor.setP(0.1);
		right_motor.setI(0.0); 
		right_motor.setD(0.0); 
		
		distance = Robot.vision.computeDistance();
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		left_motor.changeControlMode(TalonControlMode.Position);
		left_motor.set(-distance * 2600);
		
		right_motor.changeControlMode(TalonControlMode.Position);
		right_motor.set(distance * 2600);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	right_motor.changeControlMode(TalonControlMode.PercentVbus);
    	left_motor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	left_motor.set(0);
    	right_motor.set(0);
    }
}
