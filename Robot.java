/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  //Creates the encoder class
  Encoder encoder = new Encoder(0, 1);
  public Spark rightMotor1;
  public Spark leftMotor1;
  public Spark rightMotor2;
  public Spark leftMotor2;
  VictorSPX inMotor = new VictorSPX(13);
  TalonSRX outMotor = new TalonSRX(12);
  VictorSPX beltMotor1 = new VictorSPX(3);
  VictorSPX beltMotor2 = new VictorSPX(14);
  VictorSPX armMotor = new VictorSPX(7);
  Joystick joy = new Joystick(0);
  SpeedControllerGroup leftGroup;
  SpeedControllerGroup rightGroup;

  //Creates the class that will autonomously drive the robot
  DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    rightMotor1 = new Spark(0);
    rightMotor1.setInverted(true);
    leftMotor1 = new Spark(1);
    rightMotor2 = new Spark(2);
    rightMotor2.setInverted(true);
    leftMotor2 = new Spark(3);
    inMotor.set(ControlMode.PercentOutput, 0);
    outMotor.set(ControlMode.PercentOutput, 0);
    beltMotor1.set(ControlMode.PercentOutput, 0);
    beltMotor2.set(ControlMode.PercentOutput, 0);
    armMotor.set(ControlMode.PercentOutput, 0);

    leftGroup = new SpeedControllerGroup(leftMotor1, leftMotor2);
    rightGroup = new SpeedControllerGroup(rightMotor1, rightMotor2);

    //sets the encoder distance per pulse to default
    encoder.setDistancePerPulse(1./1024.);
  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
    /*
    if (encoder.getDistance() < 200){
      armMotor.set(0.2);
    } else {
      armMotor.set(0.0);
    }
    
    CommandScheduler.getInstance().run();
    */
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    /*
    //Set Modes to left and right DPad buttons
    boolean shootMode = joy.getRawButton(7);
    boolean spinMode = joy.getRawButton(8);

    if (shootMode == true) {
      
  } else if (spinMode == true) {

  }
  */
    //Gets Value For Movement and sets the motors to them
    /*
    double ySpeed = -joy.getRawAxis(1); 
    double xSpeed = joy.getRawAxis(0);
    rightGroup.set(ySpeed);
    leftGroup.set(ySpeed);
    //need to manipulate the code to be able to move the robot left and right while moving it forward and backwards
    rightGroup.set(ySpeed - xSpeed);
    leftGroup.set(ySpeed + xSpeed);
    */
    //get the y value, get the x value. When the x joystick is moved, subtract whatever
    //value you are getting from the axis from y and set that equal to x
    //while x joystick is being moved x = y - x joystick value
    //this should slow the motors on whatever side you want to turn
    //making the robot turn.
    //nevermind i did it we just need to make it smooth

    //Intake and Shooting motor code
    //double in = joy.getRawAxis(2);
    double out = joy.getRawAxis(3);
    //inMotor.set(ControlMode.PercentOutput, 0.2);
    if (out > 0.0) {
    outMotor.set(ControlMode.PercentOutput, 0.05);
    }
    /*
    //Belt Speed
    boolean bumper1Pressed = joy.getRawButton(5);
    boolean bumper2Pressed = joy.getRawButton(6);
    if (bumper1Pressed == true) {
      double beltSpeedUp = 0.75;
      beltMotor1.set(ControlMode.PercentOutput, 0.2);
      beltMotor2.set(ControlMode.PercentOutput, 0.2);
      
    } else if (bumper2Pressed == true) {
      double beltSlowDown = -0.75;
      beltMotor1.set(ControlMode.PercentOutput, 0.2);
      beltMotor2.set(ControlMode.PercentOutput, 0.2);
    }

    //Moving the intake up/down using right joystick
    double upDown = -joy.getRawAxis(5);
    armMotor.set(ControlMode.PercentOutput, 0.2);
    */
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
  
}