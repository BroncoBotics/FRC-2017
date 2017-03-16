package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	CANTalon climber;
	
	public Climber() {
		climber = new CANTalon(RobotMap.climber);
	}
	

	public void setClimber(double right_trigger, double left_trigger) {
		climber.set(right_trigger - left_trigger);
	}
	
	public void stop() {
		climber.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

