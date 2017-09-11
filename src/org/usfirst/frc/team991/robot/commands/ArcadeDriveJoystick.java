package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArcadeDriveJoystick extends Command {
	
	double turner_power = 0;

	public ArcadeDriveJoystick() {
		requires(Robot.drivetrain);
		requires(Robot.climber);
	}

	protected void initialize() {
//		Robot.drivetrain.resetPosition();
//		Robot.drivetrain.disablePID();
		Robot.drivetrain.resetGryo();
		
		
	}

	protected void execute() {
		Robot.drivetrain.arcadeDriveTrigger(Robot.oi.getPrimaryJoystick().getRawAxis(2), Robot.oi.getPrimaryJoystick().getRawAxis(3), Robot.oi.getPrimaryJoystick().getRawAxis(4), false);
//		Robot.climber.setClimber(Robot.oi.getSecondaryJoystick().getRawAxis(3), Robot.oi.getSecondaryJoystick().getRawAxis(2));
//		turner_power = 0;
//		if (Robot.oi.getSecondaryJoystick().getRawButton(5)) {
//			turner_power = -1;
//		} else if (Robot.oi.getSecondaryJoystick().getRawButton(6)) {
//			turner_power = 1;
//		} else {
//			turner_power = 0;
//		}
//		Robot.drivetrain.setTurner(turner_power);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}
