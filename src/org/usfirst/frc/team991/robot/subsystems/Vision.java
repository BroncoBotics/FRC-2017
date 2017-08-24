package org.usfirst.frc.team991.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.commands.DefaultVisionProcess;
import org.usfirst.frc.team991.robot.vison.GripPipeline;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {
	
	
	NetworkTable table;
	double[] defaultValue = new double[0];
	UsbCamera camera1, camera2;
	int[] resolution = {320, 240};
	public double centerX = resolution[1];
	public double width = 0;
	
	double VIEW_ANGLE = 50.5;
	public double OFFSET = 0;
	double TARGET_WIDTH = 10;
	
	public void initialize() {
		table = NetworkTable.getTable("Vision");
	}
	
	public void getNetworkTable() {
		centerX = table.getNumber("Center_Target", resolution[0]/2);
		width = table.getNumber("Width_Target", resolution[0]);
	}
	
	public void postSmartDash() {
		SmartDashboard.putNumber("Center", centerX);
		SmartDashboard.putNumber("Width", width);
		SmartDashboard.putNumber("Vision Distance", computeDistance());
		SmartDashboard.putNumber("Vision Angle", computeAngleOffset());
	}
	
	public double computeDistance() {

		double normalizedWidth;
		
		normalizedWidth = 2*(width)/(double)resolution[0];

		return  TARGET_WIDTH/(normalizedWidth*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
	}
	
	public double computeAngleOffset() {
		double constant = TARGET_WIDTH / width;
		double angleToGoal = 0;
		
		double distanceFromCenterPixels = centerX - (resolution[0] / 2);
		distanceFromCenterPixels -= OFFSET;
		
		// Converts pixels to inches using the constant from above.
		double distanceFromCenterInch = distanceFromCenterPixels * constant;
		
		angleToGoal = Math.atan(distanceFromCenterInch / computeDistance());
		angleToGoal = Math.toDegrees(angleToGoal);
		
		return angleToGoal;
	}
	
	public double adjustedCenter() {
		
		double calculatedCenter = centerX;
		calculatedCenter += OFFSET;
		if (calculatedCenter != -1) {
			return (calculatedCenter - (resolution[0]/2.0))/(resolution[0]/2.0) ;
		} else {
			return 0;
		}
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultVisionProcess());
    }
    
}

