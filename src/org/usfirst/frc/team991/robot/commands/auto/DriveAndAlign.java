package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveAndAlign extends Command {
	
	boolean align = true;

	double angle_offset;
	
	double GYRO_MULT, VISION_MULT, DISTANCE;
	
    public DriveAndAlign() {
        // Use requires() here to declare subsystem dependencies
       	requires(Robot.vision);
       	requires(Robot.drivetrain);
       	setTimeout(Preferences.getInstance().getDouble("Timeout", 7));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetPosition();
    	Robot.drivetrain.resetGryo();

       	
       	GYRO_MULT = Preferences.getInstance().getDouble("GyroMult", 1/45);
       	VISION_MULT = Preferences.getInstance().getDouble("VisionMult", 1/15);
       	DISTANCE = Preferences.getInstance().getDouble("VisionDistance", 1/15);
       	Robot.vision.OFFSET = Preferences.getInstance().getDouble("Offset", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.vision.getNetworkTable();
    	Robot.vision.postSmartDash();
    	
    	double distance = Robot.vision.computeDistance();
    	angle_offset = Robot.vision.adjustedCenter();
    	
    	
    	if (distance < DISTANCE && align) {
    		align = false;
    		Robot.drivetrain.resetGryo();
           	setTimeout(4);
    	}
    	
    	
    	if (align) {
    		Robot.drivetrain.arcadeDrive(-.1, (angle_offset) * VISION_MULT);
    	} else {
    		Robot.drivetrain.arcadeDrive(-.1, (Robot.drivetrain.getGyroAngle() * GYRO_MULT));
    	}
    	
    	
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
