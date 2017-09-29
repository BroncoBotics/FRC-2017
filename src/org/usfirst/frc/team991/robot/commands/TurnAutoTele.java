package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnAutoTele extends Command {

	boolean cameraTurn;
	
    public TurnAutoTele() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.drivetrain);
    	requires(Robot.vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	cameraTurn = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.vision.width < 70 && cameraTurn) {
    		Robot.drivetrain.arcadeDriveTrigger(Robot.oi.getPrimaryJoystick().getRawAxis(2), Robot.oi.getPrimaryJoystick().getRawAxis(3), Robot.vision.abs_center*.4, true);
    	} else {
    		Robot.drivetrain.arcadeDriveTrigger(Robot.oi.getPrimaryJoystick().getRawAxis(2), Robot.oi.getPrimaryJoystick().getRawAxis(3), 0, true);
    		cameraTurn = false;
    	}
    	
    	SmartDashboard.putBoolean("CameraTurn", cameraTurn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
