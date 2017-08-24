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
	double position;

    public DriveStraight(double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
//        
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGryo();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.drivetrain.arcadeDrive(-.2, Robot.drivetrain.getGyroAngle()*.05);
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
