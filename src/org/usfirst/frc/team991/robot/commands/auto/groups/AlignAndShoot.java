package org.usfirst.frc.team991.robot.commands.auto.groups;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.ShootGear;
import org.usfirst.frc.team991.robot.commands.auto.DriveAndAlign;
import org.usfirst.frc.team991.robot.commands.auto.TurnToTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AlignAndShoot extends CommandGroup {

    public AlignAndShoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	

//        addSequential(new TurnToAngle());
//    	addSequential(new TurnToTarget());
        addSequential(new DriveAndAlign());
//        if (Robot.drivetrain.limitHasPressed()){
        addSequential(new ShootGear());
//        }
    }
}
