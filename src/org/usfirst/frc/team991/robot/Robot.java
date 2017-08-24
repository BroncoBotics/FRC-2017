
package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team991.robot.commands.Mobility;
import org.usfirst.frc.team991.robot.commands.NullOp;
import org.usfirst.frc.team991.robot.commands.auto.AlignToGear;
import org.usfirst.frc.team991.robot.commands.auto.DriveAndAlign;
import org.usfirst.frc.team991.robot.commands.auto.DriveStraight;
import org.usfirst.frc.team991.robot.commands.auto.Turn;
import org.usfirst.frc.team991.robot.commands.auto.TurnToAngle;
import org.usfirst.frc.team991.robot.commands.auto.TurnToTarget;
import org.usfirst.frc.team991.robot.commands.auto.groups.AlignAndShoot;
import org.usfirst.frc.team991.robot.commands.auto.groups.SideAlign;
import org.usfirst.frc.team991.robot.subsystems.Climber;
import org.usfirst.frc.team991.robot.subsystems.Drivetrain;
import org.usfirst.frc.team991.robot.subsystems.Pneumatics;
import org.usfirst.frc.team991.robot.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Climber climber = new Climber();
	public static final Pneumatics pneumatics = new Pneumatics();
	public static final Vision vision = new Vision();
	public static OI oi;
	
	

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

    	Robot.vision.initialize();
//    	Robot.drivetrain.disablePID();
		

		drivetrain.calibrateGyro();
		
//		LiveWindow.addSensor("Drivetrain", "PID", drivetrain.getPIDController());
		oi = new OI();
		
		chooser.addDefault("No Auto", new NullOp());
		chooser.addObject("Center Auto", new AlignAndShoot());
		chooser.addObject("Mobility", new Mobility());
		chooser.addObject("Turn Auto", new TurnToTarget());
		chooser.addObject("Straight Auto", new DriveStraight(5));
		SmartDashboard.putData("Auto Mode", chooser);
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
//    	Robot.drivetrain.disablePID();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		
//		autonomousCommand = new DriveToGear();
//		autonomousCommand = new AlignToGear();
//		autonomousCommand = new AlignAndShoot();
//		autonomousCommand = new SideAlign();
//		autonomousCommand = new TurnToAngle();
//		autonomousCommand = new DriveAndAlign();

		if (autonomousCommand != null)
			autonomousCommand.start();
		
		Robot.drivetrain.resetLimit();
		SmartDashboard.putData(drivetrain);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		Robot.drivetrain.limitCheck();
		
//		SmartDashboard.putBoolean("Switch", Robot.drivetrain.limitHasPressed());
//		SmartDashboard.putNumber("Left Encoder", Robot.drivetrain.getLeftPosition());
//		SmartDashboard.putNumber("Right Encoder", Robot.drivetrain.getRightPosition());
	}

	@Override
	public void teleopInit() {
		Robot.pneumatics.openCollector();
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putBoolean("Switch", Robot.drivetrain.isLimitPressed());
		Scheduler.getInstance().run();
		
//		SmartDashboard.putNumber("Gyro", drivetrain.getGyroAngle());
	}
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		
		Robot.vision.getNetworkTable();
		Robot.vision.postSmartDash();
//    	Robot.drivetrain.disablePID();
	}
}
