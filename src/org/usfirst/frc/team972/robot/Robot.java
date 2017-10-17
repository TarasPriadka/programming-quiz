package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*; // We want to import all of WPILIB Java.
import com.ctre.*; // We need this for the CANTalons

public class Robot extends IterativeRobot {
	Joystick leftJoystick = new Joystick(0); // Make sure these are set correctly in the Driver Station
	Joystick rightJoystick = new Joystick(1);
	
	CANTalon frontLeftDriveMotor = new CANTalon(1); // CAN IDs are set in the RoboRIO Web Interface
	CANTalon frontRightDriveMotor = new CANTalon(2); // roborio-972-frc.local using Internet Explorer
	CANTalon backLeftDriveMotor = new CANTalon(3);
	CANTalon backRightDriveMotor = new CANTalon(4);
	
	CANTalon frontClimbMotor = new CANTalon(5);
	CANTalon backClimbMotor = new CANTalon(6);
	
	boolean squaredDriveButtonPressedLastTime = false;
	boolean squaredDriveMode = false;
	
	long autonomousStartTime = System.currentTimeMillis();
	public void autonomousInit() { // we use auto init for all the autonomous code
		while (System.currentTimeMillis() - autonomousStartTime > 3000) { // we get the current time again every time we run this loop. 3000 is 3 seconds in milliseconds
			frontLeftDriveMotor.set(0.4);
			backLeftDriveMotor.set(0.4);
			frontRightDriveMotor.set(-0.4);
			backRightDriveMotor.set(-0.4); // one side needs to be reversed because the motors are facing opposite directions, so driving straight forwards requires them to be set opposite to each other
		}
	}

	public void teleopPeriodic() {
		double leftDriveSpeed = leftJoystick.getY();
		double rightDriveSpeed = -rightJoystick.getY(); // one side needs to be reversed
		
		boolean squaredDriveButtonPressed = rightJoystick.getRawButton(1);
		if (squaredDriveButtonPressed) {
			squaredDriveMode = !squaredDriveMode; // Switches between true and false for squared drive mode
		}
		
		if (squaredDriveMode) {
			leftDriveSpeed = Math.pow(leftDriveSpeed, 2); // leftDriveSpeed = leftDriveSpeed*leftDriveSpeed also works.
			rightDriveSpeed = Math.pow(rightDriveSpeed, 2); // Math.pow(a,b) takes a and puts it to the power of b
		}
		
		if (leftDriveSpeed > .6 || rightDriveSpeed > .6) {
			System.out.println("Gotta go fast"); // make sure to enable prints on the FRC Driver Station
			// Click the gear icon and then click prints
		}
		
		frontLeftDriveMotor.set(leftDriveSpeed);
		backLeftDriveMotor.set(leftDriveSpeed);
		frontRightDriveMotor.set(rightDriveSpeed);
		backRightDriveMotor.set(rightDriveSpeed);
	
		boolean climbUpButtonPressed = leftJoystick.getRawButton(4);
		boolean climbDownButtonPressed = leftJoystick.getRawButton(5);
		
		if (climbUpButtonPressed) {
			frontClimbMotor.set(0.9);
			backClimbMotor.set(0.9);
		} else if (climbDownButtonPressed) {
			frontClimbMotor.set(-0.5);
			backClimbMotor.set(-0.5);
		}
	}
}

