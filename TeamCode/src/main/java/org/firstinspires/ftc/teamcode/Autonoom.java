package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous (name = "Red", group = "Autonoom")
public class Autonoom extends LinearOpMode {
    private DcMotor motorBackLeft;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorFrontRight;
    private Orientation lastAngles = new Orientation();
    private double globalAngle, power = .30, correction;
    private BNO055IMU imu;
    public int Runstate = 0;

    @Override
    public void runOpMode() {
        motorBackLeft = hardwareMap.dcMotor.get("MotorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("MotorBackRight");
        motorFrontLeft = hardwareMap.dcMotor.get("MotorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("MotorFrontRight");
        Runstate = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if(opModeIsActive()){

            while(opModeIsActive()){
                //logUtils.Log(logUtils.logType.normal,String.valueOf( Runstate), 3);
                telemetry.update();

                switch(Runstate){
                    case 0:
                        telemetry.addData("Status: ", "Starting");
                        Runstate = 10;
                        break;

                    case 10:
                        telemetry.addData("Status: ", "Driving Forward");
                        DriveSideways(1, 1000);
                        Runstate = 20;

                    case 20:
                        telemetry.addData("Status: ", "Driving Forward");
                        DriveForward(1, 1000);
                        Runstate = 30;
                        break;

                    case 30:
                        stop();
                }



            }
        }





    }
    private void TurnAxis(double power, int time){
        double right = -power;
        double left = -power;

        motorFrontRight.setPower(right);
        motorBackRight.setPower(right);
        motorFrontLeft.setPower(left);
        motorBackLeft.setPower(left);

        sleep(time);

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    private void DriveSideways(double power, int time){

        motorFrontRight.setPower(-power);
        motorBackRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackLeft.setPower(power);

        sleep(time);

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }

    void DriveForward(double power, int time){
        double right = power;
        double left = -power;

        motorFrontRight.setPower(right);
        motorBackRight.setPower(right);
        motorFrontLeft.setPower(left);
        motorBackLeft.setPower(left);

        sleep(time);

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
    }


    /*private double getAngle()
    {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }*/

    /*private double checkDirection(){
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }*/
}
