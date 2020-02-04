package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.IMU;
import org.firstinspires.ftc.teamcode.Math.Mathfunc;

@TeleOp(name = "DO NOT USE", group = "")
public class teleop_experimental extends OpMode {

    //declaring motor variables
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private IMU imu;
    private BNO055IMU _imu;

    private double correction;
    private double correctionangle;

    //declaring servo variables

    //declaring sensor variables

    //declaring variables
    private double drivedirectionspeed;

    @Override
    public void init() {
        //initializing motor names
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        imu = new IMU(_imu);
        imu.initialize();

        //initializing servo names

        //initializing sensor names

        //initializing variables
        drivedirectionspeed = 1;
        correction = 0;
        correctionangle = imu.currentHeading();

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        DriveChecks();
    }

    //Drivecheck function to keep loop() clean
    private void DriveChecks() {

        double leftx = 0;
        double lefty = 0;
        double rightx = 0;
        double speed;
        double stickangle;
        double RightX;

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;
        double shortCut = .30;

        //correction code
        if(gamepad1.right_stick_x > 0.5){
            correctionangle = imu.currentHeading();
        }

        if(gamepad1.right_stick_x < 0.2){
            correction  = correctionangle - imu.currentHeading();
        }else{
            correction = 0;
        }

        //control code
        if (gamepad1.dpad_up) {
            lefty = shortCut;
        } else if (gamepad1.dpad_right) {
            leftx = shortCut;
        } else if (gamepad1.dpad_down) {
            lefty = -shortCut;
        } else if (gamepad1.dpad_left) {
            leftx = -shortCut;
        } else {
            leftx = gamepad1.left_stick_x;
            lefty = -gamepad1.left_stick_y;
            rightx = gamepad1.right_stick_x;
        }

        //radius of circle the stick is in
        speed = (Math.hypot(leftx, lefty))*drivedirectionspeed;

        //inverse tangent calculate angle of stick
        stickangle = Math.atan2(lefty, leftx);

        //twisting the circle of units 45 degrees
        stickangle -= Math.PI / 4;

        RightX = rightx * 0.5 + correction;
        frontLeftPower = (speed * Math.cos(stickangle) + RightX);
        frontRightPower = (speed * Math.sin(stickangle) - RightX);
        backLeftPower = (speed * Math.sin(stickangle) + RightX);
        backRightPower = (speed * Math.cos(stickangle) - RightX);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        //clamping the dds and making it go up and down with a button
        if (gamepad1.right_trigger > 0.5) {
            drivedirectionspeed += 0.005f;
        } else if (gamepad1.left_trigger > 0.5) {
            drivedirectionspeed -= 0.005f;
        } else if (gamepad1.right_bumper) {
            drivedirectionspeed = 1f;
        } else if (gamepad1.left_bumper) {
            drivedirectionspeed = 0.33f;
        }
        drivedirectionspeed = Mathfunc.clamp(0.3,1, drivedirectionspeed);

        telemetry.addData("DriveDirectionSpeed", drivedirectionspeed);
        telemetry.update();
    }
}


