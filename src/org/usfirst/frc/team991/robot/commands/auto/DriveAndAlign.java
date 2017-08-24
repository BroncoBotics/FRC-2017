package org.usfirst.frc.team991.robot.commands.auto;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
       	setTimeout(7);
       	


//    	SmartDashboard.putNumber("GyroMult", 0.1);
//    	SmartDashboard.putNumber("VisionMult", 0.4);
//    	SmartDashboard.putNumber("VisionDistance", 20);
//    	SmartDashboard.putNumber("Offset", 25);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetPosition();
    	Robot.drivetrain.resetGryo();
    	align = true;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.vision.getNetworkTable();
    	Robot.vision.postSmartDash();
    	
    	double distance = Robot.vision.computeDistance();
    	angle_offset = Robot.vision.adjustedCenter();
    	

//       	GYRO_MULT = SmartDashboard.getNumber("GyroMult", 0.1);
//       	VISION_MULT = SmartDashboard.getNumber("VisionMult", 0.4);
//       	DISTANCE = SmartDashboard.getNumber("VisionDistance", 20);
//       	Robot.vision.OFFSET = SmartDashboard.getNumber("Offset", 25);
    	

       	GYRO_MULT = 0.1;
       	VISION_MULT = 0.4;
       	DISTANCE = 20;
       	Robot.vision.OFFSET = 15;
       	
    	if (distance < DISTANCE && align) {
    		align = false;
    		Robot.drivetrain.resetGryo();
//           	setTimeout(7);
    	}

//		SmartDashboard.putBoolean("Align", align);
    	
    	if (align) {
    		Robot.drivetrain.arcadeDrive(-0.3, (angle_offset) * VISION_MULT);
    	} else {
    		Robot.drivetrain.arcadeDrive(-0.3, (Robot.drivetrain.getGyroAngle() * GYRO_MULT));
    	}
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.drivetrain.limitHasPressed()) {
            return isTimedOut();
    	}
    	return false;
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
