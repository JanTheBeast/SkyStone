package org.firstinspires.ftc.teamcode.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.Hardware.IMU;

@Autonomous (name = "DO NOT USE", group = "")
@Disabled
public class auto_experimental extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private Drivetrain drivetrain;
    private IMU imu;

    public int Runstate = 0;
    private float startHeading;

    @Override
    public void runOpMode() {
        drivetrain = new Drivetrain(frontLeft,frontRight,backLeft,backRight);

        imu.initialize();

        Runstate = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {

                switch (Runstate) {
                    case 0:
                        telemetry.addData("Status: ", "Starting");
                        Runstate = 10;
                        break;

                    case 10:
                        telemetry.addData("Status: ", "Turn 90 degrees");
                        drivetrain.ForwardCorrection(500,1);
                        drivetrain.turnToAngle(90,1,3);
                        drivetrain.ForwardCorrection(1000,1);
                        Runstate = 20;
                        break;

                    case 20:
                        stop();
                }

                telemetry.update();

            }
        }
    }
}