package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous (name = "DO NOT USE", group = "")
@Disabled
public class auto_experimental extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private BNO055IMU imu;
    public int Runstate = 0;
    private float startHeading;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        double StartTimeDetection = 0;
        //IMU start
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        startHeading = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        telemetry.addData("IMU status",imu.isGyroCalibrated());


        Orientation a = imu.getAngularOrientation();
        telemetry.addData("StartHeading",  startHeading);

        Runstate = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {
                //logUtils.Log(logUtils.logType.normal,String.valueOf( Runstate), 3);
                telemetry.update();

                switch (Runstate) {
                    case 0:
                        telemetry.addData("Status: ", "Starting");
                        Runstate = 10;
                        break;

                    case 10:
                        telemetry.addData("Status: ", "Driving Forward");
                        DriveForward(1, 1000);
                        Runstate = 20;
                        break;

                    case 20:
                        stop();
                }


            }
        }


    }

    private void TurnAxis(double power, int time) {
        motorRight.setPower(power);
        motorLeft.setPower(power);

        sleep(time);

        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    void DriveForward(double power, int time) {
        motorRight.setPower(power);
        motorLeft.setPower(-power);

        sleep(time);

        motorLeft.setPower(0);
        motorRight.setPower(0);
    }


}