package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToAngle extends Command {
	
	double angle_target;

    public TurnToAngle(double angle_target) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        this.angle_target = angle_target;
        setTimeout(2);
    }
    
    public TurnToAngle() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        requires(Robot.vision);
        setTimeout(1.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGryo();
        angle_target = -Robot.vision.computeAngleOffset();
        Robot.vision.OFFSET = Preferences.getInstance().getDouble("Offset", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.turn(-((angle_target - Robot.drivetrain.getGyroAngle()) / 45));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Math.abs(angle_target - Robot.drivetrain.getGyroAngle()) < 1;
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
