package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.subsystems.Pneumatics.GearSetting;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearControl extends InstantCommand {
	
	GearSetting setting;

    public GearControl(GearSetting setting) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.pneumatics);
        this.setting = setting;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch (setting) {
    		case CLOSE_GATE:
    			Robot.pneumatics.closeGate();
    			break;
    		case OPEN_GATE:
    			Robot.pneumatics.openGate();
    			break;
    		case CLOSE_PUSHER:
    			Robot.pneumatics.closePusher();
    			break;
    		case OPEN_PUSHER:
    			Robot.pneumatics.openPusher();
    			break;
    	}
    }
    
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
