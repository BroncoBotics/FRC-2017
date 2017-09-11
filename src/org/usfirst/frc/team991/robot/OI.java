package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.FlipForward;
import org.usfirst.frc.team991.robot.commands.GearControl;
import org.usfirst.frc.team991.robot.commands.ShootGear;
import org.usfirst.frc.team991.robot.commands.SignalControl;
import org.usfirst.frc.team991.robot.commands.SwitchCamera;
import org.usfirst.frc.team991.robot.commands.SwitchCamera.Cameras;
import org.usfirst.frc.team991.robot.commands.Turn360;
import org.usfirst.frc.team991.robot.commands.TurnAutoTele;
import org.usfirst.frc.team991.robot.subsystems.Pneumatics.GearSetting;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//Initialize joysticks
	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);
	
	
	private Button button_a = new JoystickButton(joystick_0, 1),
				button_b = new JoystickButton(joystick_0, 2),
				button_x = new JoystickButton(joystick_0, 3),
				button_y = new JoystickButton(joystick_0, 4),
				button_rb = new JoystickButton(joystick_0, 5),
				button_lb = new JoystickButton(joystick_0, 6);
	

	public OI() {

		button_a.whenPressed(new SwitchCamera(Cameras.CAMERA1));
		button_b.whileActive(new SwitchCamera(Cameras.CAMERA2));
		button_x.whileHeld(new TurnAutoTele());
		button_rb.whenPressed(new ShootGear());
		button_lb.whenPressed(new Turn360());
		
		
	}

	//Return the first joystick (Drive)
	public Joystick getPrimaryJoystick() {
		return joystick_0;
	}
	
	//Return the second joystick (Actuators)
	public Joystick getSecondaryJoystick() {
		return joystick_1;
	}


}

