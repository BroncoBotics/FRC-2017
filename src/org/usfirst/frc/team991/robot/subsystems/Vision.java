package org.usfirst.frc.team991.robot.subsystems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.Core;
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
import edu.wpi.cscore.VideoProperty;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {
	
	
//	NetworkTable table;
//	double[] defaultValue = new double[0];
//	int[] resolution = {320, 240};
//	public double centerX = resolution[1];
//	public double width = 0;
//	
//	double VIEW_ANGLE = 50.5;
//	public double OFFSET = 0;
//	double TARGET_WIDTH = 10;
	
//
//	UsbCamera camera1, camera2;
//	VideoSink server;
//	CvSink cvsink1, cvsink2;
	
//	public void initialize() {
//		camera1 = CameraServer.getInstance().startAutomaticCapture(0);
//		camera2 = CameraServer.getInstance().startAutomaticCapture(1);
//		server = CameraServer.getInstance().getServer();
//		cvsink1 = new CvSink("cam1cv");
//		cvsink2 = new CvSink("cam2cv");
//		
//		cvsink1.setSource(camera1);
//		cvsink1.setEnabled(true);
//		
//		cvsink2.setSource(camera2);
//		cvsink2.setEnabled(true);
//	}
//	
	public void setCamera1() {
		camera = 0;
	}
	
	public void setCamera2() {
		camera = 1;
	}
	
	Thread visionThread;
	volatile int camera = 0;
	public volatile double abs_center = 0;
	public volatile double width = 0;
	public void initialize() {
		visionThread = new Thread(() -> {
			// Get the UsbCamera from CameraServer
			
//			UsbCamera testcam = new UsbCamera("Test", 0);
//			testcam.free();
			
			
			UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
			UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
//			camera1.setFPS(15);
			camera1.setExposureManual(10);
			camera1.setBrightness(50);
			// Set the resolution
//			VideoProperty[] properties = camera1.enumerateProperties();
//			for(int i=0; i < properties.length; i++){
//				System.out.println(properties[i].getName());
//			}
//			

			// Get a CvSink. This will capture Mats from the camera
			CvSink cvSink1 = CameraServer.getInstance().getVideo(camera1);
			CvSink cvSink2 = CameraServer.getInstance().getVideo(camera2);
			// Setup a CvSource. This will send images back to the Dashboard
			CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 160, 120);

			// Mats are very memory expensive. Lets reuse this Mat.
			Mat mat = new Mat();
			GripPipeline grip = new GripPipeline();
			ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		    Rect rect1, rect2, rect;
		    Point tl, br;

		    Point mid;

			// This cannot be 'true'. The program will never exit if it is. This
			// lets the robot stop this thread when restarting robot code or
			// deploying.
			while (!Thread.interrupted()) {
				// Tell the CvSink to grab a frame from the camera and put it
				// in the source mat.  If there is an error notify the output.

				if (camera == 0) {
					if (cvSink1.grabFrame(mat) == 0) {
						// Send the output the error.
						outputStream.notifyError(cvSink1.getError());
						// skip the rest of the current iteration
						continue;
					}
					Core.flip(mat, mat, -1);
					grip.process(mat);
					contours = grip.findContoursOutput();
					if (contours.size() > 1) {
				        Collections.sort(contours, new Comparator<MatOfPoint>() {
				            @Override
				            public int compare(MatOfPoint point1, MatOfPoint point2) {
				                if (Imgproc.boundingRect(point1).area() > Imgproc.boundingRect(point2).area()) {
				                  return -1;
				                }
				                else if (Imgproc.boundingRect(point1).area() < Imgproc.boundingRect(point2).area()) {
				                  return 1;
				                }
				                else {
				                  return 0;
				                }
				            }
				        });

				        // System.out.println(contours.get(0));
				        rect1 = Imgproc.boundingRect(contours.get(0));
				        rect2 = Imgproc.boundingRect(contours.get(1));

				        tl = new Point(Math.min(rect1.tl().x, rect2.tl().x)/2, Math.min(rect1.tl().y, rect2.tl().y)/2);
				        br = new Point(Math.max(rect1.br().x, rect2.br().x)/2, Math.max(rect1.br().y, rect2.br().y)/2);

				        Imgproc.rectangle(mat, tl, br, new Scalar(0, 255, 0), 1);

				        mid = new Point((tl.x+br.x)/2,(tl.y+br.y)/2);
				        abs_center = (mid.x - 80)/80;
				        width = br.x-tl.x;
//				        SmartDashboard.putNumber("Center_Target", (mid.x - 80)/80);
				      
				      } else if (contours.size() > 0) {
				        
				        rect = Imgproc.boundingRect(contours.get(0));
				        tl = new Point(rect.tl().x/2, rect.tl().y/2);
				        br = new Point(rect.br().x/2, rect.br().y/2);
				        Imgproc.rectangle(mat, tl, br, new Scalar(0, 255, 0), 1);
				        mid = new Point((rect.tl().x+rect.br().x)/4,(rect.tl().y+rect.br().y)/4);
				        abs_center = (mid.x - 80)/80;
//				        SmartDashboard.putNumber("Center_Target", (mid.x - 80)/80);
				      }
				} else {
					if (cvSink2.grabFrame(mat) == 0) {
						// Send the output the error.
						outputStream.notifyError(cvSink2.getError());
						// skip the rest of the current iteration
						continue;
					}
				}
				
				// Put a rectangle on the image
//				Imgproc.rectangle(mat, new Point(100, 100), new Point(300, 200),
//						new Scalar(255, 255, 255), 5);
				// Give the output stream a new image to display
				outputStream.putFrame(mat);
			}
		});
		visionThread.setDaemon(true);
		visionThread.start();
	}
	
	
	
	
	
//	public void getNetworkTable() {
//		centerX = table.getNumber("Center_Target", resolution[0]/2);
//		width = table.getNumber("Width_Target", resolution[0]);
//	}
//	
//	public void postSmartDash() {
//		SmartDashboard.putNumber("Center", centerX);
//		SmartDashboard.putNumber("Width", width);
//		SmartDashboard.putNumber("Vision Distance", computeDistance());
//		SmartDashboard.putNumber("Vision Angle", computeAngleOffset());
//	}
//	
//	public double computeDistance() {
//
//		double normalizedWidth;
//		
//		normalizedWidth = 2*(width)/(double)resolution[0];
//
//		return  TARGET_WIDTH/(normalizedWidth*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
//	}
//	
//	public double computeAngleOffset() {
//		double constant = TARGET_WIDTH / width;
//		double angleToGoal = 0;
//		
//		double distanceFromCenterPixels = centerX - (resolution[0] / 2);
//		distanceFromCenterPixels -= OFFSET;
//		
//		// Converts pixels to inches using the constant from above.
//		double distanceFromCenterInch = distanceFromCenterPixels * constant;
//		
//		angleToGoal = Math.atan(distanceFromCenterInch / computeDistance());
//		angleToGoal = Math.toDegrees(angleToGoal);
//		
//		return angleToGoal;
//	}
//	
//	public double adjustedCenter() {
//		
//		double calculatedCenter = centerX;
//		calculatedCenter += OFFSET;
//		if (calculatedCenter != -1) {
//			return (calculatedCenter - (resolution[0]/2.0))/(resolution[0]/2.0) ;
//		} else {
//			return 0;
//		}
//	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new DefaultVisionProcess());
    }
    
}

