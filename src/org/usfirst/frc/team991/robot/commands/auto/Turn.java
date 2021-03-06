package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn extends Command {
	CANTalon left_motor, right_motor;
	double ticks;

    public Turn() {
        requires(Robot.drivetrain);
        requires(Robot.vision);

    	Robot.vision.getNetworkTable();
    	Robot.vision.postSmartDash();
    	double tick_per_angle = 100;
    	ticks = tick_per_angle * Robot.vision.computeAngleOffset();
    	
        // Use requires() here to declare subsystem dependencies
        setTimeout(2);

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
		
//		left_motor.setAllowableClosedLoopErr(50);
//		right_motor.setAllowableClosedLoopErr(50);
		
		left_motor.setAllowableClosedLoopErr(0); /* always servo */
	    /* set closed loop gains in slot0 */
		left_motor.setProfile(0);
		left_motor.setF(0.0);
		left_motor.setP(2);
		left_motor.setI(0.0); 
		left_motor.setD(0.0);    
		
		right_motor.setAllowableClosedLoopErr(0); /* always servo */
	    /* set closed loop gains in slot0 */
		right_motor.setProfile(0);
		right_motor.setF(0.0);
		right_motor.setP(2);
		right_motor.setI(0.0); 
		right_motor.setD(0.0);  
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.vision.getNetworkTable();
    	Robot.vision.postSmartDash();
		left_motor.changeControlMode(TalonControlMode.Position);
		left_motor.set(-ticks);
		SmartDashboard.putNumber("Error Left", left_motor.getError());
		
		right_motor.changeControlMode(TalonControlMode.Position);
		right_motor.set(-ticks);
		SmartDashboard.putNumber("Error Right", right_motor.getError());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
//        return left_motor.getError()
    }

    // Called once after isFinished returns true
    protected void end() {
    	right_motor.changeControlMode(TalonControlMode.PercentVbus);
    	left_motor.changeControlMode(TalonControlMode.PercentVbus);
    	
    	left_motor.set(0);
    	right_motor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
