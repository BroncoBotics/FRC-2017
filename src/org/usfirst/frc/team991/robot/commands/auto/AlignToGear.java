package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignToGear extends Command {

    public AlignToGear() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.vision);
    	requires(Robot.drivetrain);
//    	setTimeout(2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.vision.getNetworkTable();
    	Robot.vision.postSmartDash();
    	double adjCenter = Robot.vision.adjustedCenter();
    	Robot.drivetrain.arcadeDriveTrigger(Robot.oi.getPrimaryJoystick().getRawAxis(2), Robot.oi.getPrimaryJoystick().getRawAxis(3), -adjCenter);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        return isTimedOut();
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
