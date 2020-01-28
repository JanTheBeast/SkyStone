package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.IMU;

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
        boolean goRight;
        double currentHeading;
        double degreesLeft;
        double speed;
        double prevHeading= 0;
        ElapsedTime timeout = new ElapsedTime();


        currentHeading = imu.currentHeading();
        degreesLeft = Math.abs(target - currentHeading);

        goRight = target > currentHeading;

        if (degreesLeft > 180) {
            goRight = !goRight;
            degreesLeft = 360 - degreesLeft;
        }

        timeout.reset();
        prevHeading = currentHeading;
        while (degreesLeft > .3 && OpModeIsActive() && timeout.seconds() < 2) {

            if (speedMod < 0){
                speed = (Math.pow((degreesLeft + 25) / -speedMod, 3) + 15) / 100;
            } else {
                if(speedMod != 0){

                } else {

                }
            }

        }

    }

    private double getSpeed() {

    }




}
