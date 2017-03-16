package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	
	CANTalon left_motor, right_motor;
	
	double position;

    public DriveStraight(double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
//        left_motor = Robot.drivetrain.getLeft();
//        right_motor = Robot.drivetrain.getRight();
//        
//        this.position = position;
//        
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	left_motor.setEncPosition(0);
//    	right_motor.setEncPosition(0);
//    	
////    	left_motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
////    	right_motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
////        
////    	left_motor.reverseSensor(true);
////    	right_motor.reverseSensor(false);
//    	
//    	left_motor.configNominalOutputVoltage(+0f, -0f);
//    	right_motor.configNominalOutputVoltage(+0f, -0f);
//    	
//    	left_motor.configPeakOutputVoltage(+12f, -12f);
//    	right_motor.configPeakOutputVoltage(+12f, -12f);
//    	
//    	left_motor.setAllowableClosedLoopErr(0); /* always servo */
//        /* set closed loop gains in slot0 */
//    	left_motor.setProfile(0);
//    	left_motor.setF(0.0);
//    	left_motor.setP(0.1);
//    	left_motor.setI(0.0); 
//    	left_motor.setD(0.0);    
//    	
//    	right_motor.setAllowableClosedLoopErr(0); /* always servo */
//        /* set closed loop gains in slot0 */
//    	right_motor.setProfile(0);
//    	right_motor.setF(0.0);
//    	right_motor.setP(0.1);
//    	right_motor.setI(0.0); 
//    	right_motor.setD(0.0);  
//    	
//    	left_motor.changeControlMode(TalonControlMode.Position);
//    	left_motor.set(position);
//    	
//    	right_motor.changeControlMode(TalonControlMode.Position);
//    	right_motor.set(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.arcadeDrive(-.2, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
