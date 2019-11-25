package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mechanum_rijden", group = "")
public class Mechanum_rijden extends OpMode{
    private DcMotor motorBackLeft;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorFrontRight;
    double drivedirectionspeed = 1;

    @Override
    public void init() {
        motorBackLeft = hardwareMap.dcMotor.get("MotorBackLeft");
        motorBackRight = hardwareMap.dcMotor.get("MotorBackRight");
        motorFrontLeft = hardwareMap.dcMotor.get("MotorFrontLeft");
        motorFrontRight = hardwareMap.dcMotor.get("MotorFrontRight");
        drivedirectionspeed = 1;
    }

    @Override
    public void loop() { DriveChecks(); }

    void DriveChecks() {
        if(Math.abs(gamepad1.left_stick_x) < Math.abs(gamepad1.left_stick_y)){
            DriveForward();
        }else if(Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y)){
            DriveSideways();
        }

        TurnAxis();

        if (gamepad1.right_trigger > 0.5 && drivedirectionspeed < 5) {
            drivedirectionspeed += 0.0025f;
        } else if (gamepad1.left_trigger > 0.5 && drivedirectionspeed > 0.003) {
            drivedirectionspeed -= 0.0025f;
        }

        telemetry.addData("DriveDirectionSpeed", drivedirectionspeed);
    }

    void DriveForward(){
        double direction = Math.abs(gamepad1.left_stick_y)/gamepad1.left_stick_y;
        double right = -direction * drivedirectionspeed;
        double left = direction * drivedirectionspeed;

        motorFrontRight.setPower(right);
        motorBackRight.setPower(right);
        motorFrontLeft.setPower(left);
        motorBackLeft.setPower(left);
    }

    void DriveSideways(){
        double direction = Math.abs(gamepad1.left_stick_x)/gamepad1.left_stick_x;
        double power = direction * drivedirectionspeed;

        motorFrontRight.setPower(-power);
        motorBackRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackLeft.setPower(power);
    }

    void TurnAxis(){
        double right = -gamepad1.right_stick_x * drivedirectionspeed;
        double left = -gamepad1.right_stick_x * drivedirectionspeed;

        motorFrontRight.setPower(right);
        motorBackRight.setPower(right);
        motorFrontLeft.setPower(left);
        motorBackLeft.setPower(left);
    }

}

