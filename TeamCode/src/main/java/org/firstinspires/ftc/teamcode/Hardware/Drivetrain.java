package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import org.firstinspires.ftc.teamcode.Math.Constants;
import org.firstinspires.ftc.teamcode.Math.Mathfunc;

public class Drivetrain {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    BNO055IMU imu;

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

    public void turnToAngle(double angle, double speed, double precision){
            double lastError;
            long lastTime;
        while(!Mathfunc.range(3, imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle,angle), precision){

            double error = angle - imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES). thirdAngle;
            long currTime = System.currentTimeMillis();
            double dE = error - lastError;
            long dT = currTime - lastTime;
            double correction = (error * Constants.Kp)+((dE/dT)*Constants.Kd) + Constants.Ci;
            correction = Mathfunc.clamp(-1,1,correction);
            setPower(correction, -correction, correction, -correction);
            lastError = error;
            lastTime = currTime;
        }
        setPower(0,0,0,0);
    }

    public void ForwardCorrection(int time, double speed){
        double startAngle =
    }

}
