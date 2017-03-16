package org.usfirst.frc.team991.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Compressor compressor = new Compressor();
	DoubleSolenoid gate = new DoubleSolenoid(7,6);
	DoubleSolenoid pusher = new DoubleSolenoid(0,1);
	
	public Pneumatics() {
		closeGate();
//		compressor.stop();
	}
	
	public void openGate() {
//		if (pusher.get() == DoubleSolenoid.Value.kForward)
		gate.set(DoubleSolenoid.Value.kForward);
	}
	
	public void closeGate() {
		if (pusher.get() != DoubleSolenoid.Value.kForward)
			gate.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void openPusher() {
		if (gate.get() == DoubleSolenoid.Value.kForward)
			pusher.set(DoubleSolenoid.Value.kForward);
	}
	
	public void closePusher() {
		pusher.set(DoubleSolenoid.Value.kReverse);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
//        setDefaultCommand(new GearGateControl());
    }
    
    public enum GearSetting {
		OPEN_GATE, CLOSE_GATE, OPEN_PUSHER, CLOSE_PUSHER
	}
}

