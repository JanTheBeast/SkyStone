package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Math.Constants;
import org.firstinspires.ftc.teamcode.Math.Mathfunc;

public class Drivetrain {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public IMU imu;
    public LinearOpMode opMode;

    public Drivetrain (DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight){
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.backLeft = backLeft;
        this.backRight = backRight;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;

    }

    public void DriveTrainMecanum(){
        backLeft = null;
        frontLeft = null;
        frontRight = null;
        backRight = null;
        imu = null;
    }

    public void setPower(double flpower, double frpower, double blpower, double brpower){
        frontLeft.setPower(flpower);
        frontRight.setPower(frpower);
        backLeft.setPower(blpower);
        backRight.setPower(brpower);
    }

    public void turnOff(){
        setPower(0,0,0,0);
    }

    public void turnToAngle(double angle, double speed, double precision){
            double lastError = 0;
            long lastTime = 0;
        while(!Mathfunc.range(precision, imu.currentHeading(),angle) && opMode.opModeIsActive()){

            double error = angle - imu.currentHeading();
            long currTime = System.currentTimeMillis();
            double dE = error - lastError;
            long dT = currTime - lastTime;
            double correction = (error * Constants.Kp)+((dE/dT)*Constants.Kd) + Constants.Ci;
            correction = Mathfunc.clamp(-1,1,correction);
            setPower(correction, -correction, correction, -correction);
            lastError = error;
            lastTime = currTime;
        }
        turnOff();
    }

    public void ForwardCorrection(int time, double speed){

        double startAngle = imu.currentHeading();
        double endTime = System.currentTimeMillis() + time;
        double left;
        double right;
        double correction;

        while(System.currentTimeMillis() < endTime && opMode.opModeIsActive()){

            correction = (startAngle - imu.currentHeading());
            right = speed + correction;
            left = speed - correction;
            left = Mathfunc.clampProportion(-1,1,left,right)[0];
            right = Mathfunc.clampProportion(-1,1,left,right)[1];
            setPower(left,right,left,right);

        }
        turnOff();
    }

    public void MoveSideways(double speed, double time){
        double startTime = System.currentTimeMillis();
        double endTime = startTime + time;
        while(System.currentTimeMillis() < endTime && opMode.opModeIsActive()){
            setPower(speed,speed,-speed,-speed);
        }
        turnOff();
    }

    public void MoveAngle(double speed, double angle, double time){

        double startTime = System.currentTimeMillis();
        double endTime = startTime + time;
        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        angle -= Math.PI /4;

        while(System.currentTimeMillis() < endTime && opMode.opModeIsActive()) {
            frontLeftPower = (speed * Math.cos(angle));
            frontRightPower = (speed * Math.sin(angle));
            backLeftPower = (speed * Math.sin(angle));
            backRightPower = (speed * Math.cos(angle));

            setPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        }
        turnOff();
    }

}
