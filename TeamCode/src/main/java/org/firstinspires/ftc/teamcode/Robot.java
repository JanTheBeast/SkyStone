package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.IMU;
import org.firstinspires.ftc.teamcode.Math.Mathfunc;

public class Robot extends LinearOpMode {
    public Drivetrain drivetrain;
    public IMU imu;
    BNO055IMU gyro;

    public void robotInit(){

        waitForStart();
    }

    public void driveToSpot(){

    }

    public void turnToHeading(double target, double speedMod){

        while (Mathfunc.range(3, imu.currentHeading(), target) && opModeIsActive()){

        }

    }

    private double getSpeed() {

    }

    @Override
    public void runOpMode() throws InterruptedException {

    }





}
