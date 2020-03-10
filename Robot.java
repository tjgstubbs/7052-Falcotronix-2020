/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//RADIO PASSWORD: falcotronix

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Sendable;
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
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  // Creates the encoder class
  Encoder encoder = new Encoder(0, 1);
  public Spark rightMotor1;
  public Spark leftMotor1;
  public Spark rightMotor2;
  public Spark leftMotor2;
  VictorSPX inMotor = new VictorSPX(13);
  VictorSPX outMotor = new VictorSPX(3);
  VictorSPX beltMotor1 = new VictorSPX(14);
  VictorSPX beltMotor2 = new VictorSPX(7);
  VictorSPX liftMotor = new VictorSPX(0);
  VictorSPX pullMotor = new VictorSPX(1);
  TalonSRX armMotor = new TalonSRX(12);
  Joystick joy = new Joystick(0);
  SpeedControllerGroup leftGroup;
  SpeedControllerGroup rightGroup;
  VideoSink server;
  UsbCamera cameraBalls;
  UsbCamera cameraShoot;
  NetworkTableEntry cameraSelection;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() { 
    cameraBalls = CameraServer.getInstance().startAutomaticCapture(0);
    cameraShoot = CameraServer.getInstance().startAutomaticCapture(1);
    cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    cameraBalls.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    cameraShoot.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    cameraBalls.setResolution(640, 480);
    cameraShoot.setResolution(640, 480);

    m_oi = new OI();
    rightMotor1 = new Spark(0);
    rightMotor1.setInverted(false);
    leftMotor1 = new Spark(1);
    rightMotor2 = new Spark(2);
    rightMotor2.setInverted(true);
    leftMotor2 = new Spark(3);
    inMotor.set(ControlMode.PercentOutput, 0);
    outMotor.set(ControlMode.PercentOutput, 0);
    beltMotor1.set(ControlMode.PercentOutput, 0);
    beltMotor2.set(ControlMode.PercentOutput, 0);
    armMotor.set(ControlMode.PercentOutput, 0);
    liftMotor.set(ControlMode.PercentOutput, 0);
    pullMotor.set(ControlMode.PercentOutput, 0);

    leftGroup = new SpeedControllerGroup(leftMotor1, leftMotor2);
    rightGroup = new SpeedControllerGroup(rightMotor1, rightMotor2);

    // Create Cameras
    /*
     * UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0); MjpegServer
     * mjpegServer1 = new MjpegServer("serve_USB Camera 0", 1181);
     * mjpegServer1.setSource(usbCamera);
     * 
     * CvSink cvSink = new CvSink("opencv_USB Camera 0");
     * cvSink.setSource(usbCamera);
     * 
     * CvSource outputStream = new CvSource("Blur", PixelFormat.kMJPEG, 640, 480,
     * 30); MjpegServer mjpegServer2 = new MjpegServer("serve_Blur", 1182);
     * mjpegServer2.setSource(outputStream);
     */
    /*
     * // Creates UsbCamera and MjpegServer [1] and connects them
     * CameraServer.getInstance().startAutomaticCapture();
     * 
     * // Creates the CvSink and connects it to the UsbCamera CvSink cvSink =
     * CameraServer.getInstance().getVideo();
     * 
     * // Creates the CvSource and MjpegServer [2] and connects them CvSource
     * outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
     */

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
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
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();

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

    //Use Y to go to the shooting camera and X to go to the balls camera
    final boolean buttonYPressed = joy.getRawButton(4);
    if (buttonYPressed == true) {
      System.out.println("Setting Camera Shooter");
      cameraSelection.setString(cameraShoot.getName());
    }
    
    final boolean buttonXPressed = joy.getRawButton(3);
    if(buttonXPressed == true){
      System.out.println("Setting Camera Collector");
      cameraSelection.setString(cameraBalls.getName());
    }

    //Gets Value For Movement and sets the motors to them
    double ySpeed = -joy.getRawAxis(1) / 3; 
    double xSpeed = joy.getRawAxis(0) / 3;
   
    rightGroup.set(ySpeed);
    leftGroup.set(xSpeed);

    rightGroup.set(ySpeed - xSpeed);
    leftGroup.set(ySpeed + xSpeed); 

    //Create DeadZones
    if(ySpeed < 0.1){
      ySpeed = 0.0;
    } 

    if(xSpeed < 0.1){
      xSpeed = 0.0;
    }
    
    //Intake and Shooting motor code
    final double out = -joy.getRawAxis(3);
    outMotor.set(ControlMode.PercentOutput, out);

    final double in = -joy.getRawAxis(2);
    inMotor.set(ControlMode.PercentOutput, in / 2);
    final boolean buttonAPressed = joy.getRawButton(1);
    if(buttonAPressed == true){
      inMotor.set(ControlMode.PercentOutput, -in / 2); 
    } else if(buttonAPressed == false){
      //inMotor.set(ControlMode.PercentOutput, 0);
      inMotor.set(ControlMode.PercentOutput, in / 2);
    }

    //Lift Arm Motor
    double lift =  -joy.getRawAxis(5);
    liftMotor.set(ControlMode.PercentOutput, lift / 5);

    //Deadzones
    if(lift < 0.1){
      lift = 0.0;
    }

    //Pull Arm Motor
    double pull = joy.getRawAxis(4);
    if(pull < 0.2){
      pullMotor.set(ControlMode.PercentOutput, 1);
    } else if(pull < 0){
      pullMotor.set(ControlMode.PercentOutput, 0);
    }
    

    //Deadzones
    if(pull < 0.1){
      pull = 0.0;
    }
    
    //Belt Speed
      /*double beltSpeedUp = -joy.getRawAxis(5);
      double beltSlowDown = joy.getRawAxis(5);
      if(beltSpeedUp > 0.1){
        beltSpeedUp = 0.15;
        beltMotor1.set(ControlMode.PercentOutput, beltSpeedUp);
        beltMotor2.set(ControlMode.PercentOutput, beltSpeedUp);
      } else {
        beltMotor1.set(ControlMode.PercentOutput, 0);
        beltMotor2.set(ControlMode.PercentOutput, 0);
      }
      
    if(beltSlowDown < 0.1){
      beltSlowDown = -0.15;
      beltMotor1.set(ControlMode.PercentOutput, beltSlowDown);
      beltMotor2.set(ControlMode.PercentOutput, beltSlowDown);
    } else {
      beltMotor1.set(ControlMode.PercentOutput, 0);
      beltMotor2.set(ControlMode.PercentOutput, 0);
    }*/
    final boolean bumper1Pressed = joy.getRawButton(5);
    final boolean bumper2Pressed = joy.getRawButton(6);
    if (bumper1Pressed == true) {
      final double beltSpeedUp = 0.15;
      final double beltSlowDown = 0.15;
      beltMotor1.set(ControlMode.PercentOutput, beltSpeedUp);
      beltMotor2.set(ControlMode.PercentOutput, beltSlowDown);
      
      
    } else if (bumper1Pressed == false) { 
      final double beltSpeedUp = 0.0;
     beltMotor1.set(ControlMode.PercentOutput, beltSpeedUp);
     beltMotor2.set(ControlMode.PercentOutput, beltSpeedUp);
    }



      if (bumper2Pressed == true) {
      final double beltSlowDown = -0.15;
      beltMotor1.set(ControlMode.PercentOutput, -0.15);
      beltMotor2.set(ControlMode.PercentOutput, -0.15);
      } 
      else if (bumper2Pressed == false) { 
         final double beltSlowDown = 0.0;
         beltMotor1.set(ControlMode.PercentOutput, beltSlowDown);
         beltMotor2.set(ControlMode.PercentOutput, beltSlowDown);
        }
        
    }
    
    //Moving the intake arm up/down using a button

    /**
     * This function is called periodically during test mode.
     */
  @Override
  public void testPeriodic() {
  }
  
}
