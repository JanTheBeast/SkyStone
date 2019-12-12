package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name = "all_direction_rijden", group = "")
@Disabled
public class all_direction_rijden extends OpMode{
    private DcMotor motorBackLeft;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorFrontRight;
    double drivedirectionspeed = 1;

    //variables for driving function
    double yMovement = 0;
    double xMovement = 0;


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

        double frontLeft = 0;
        double frontRight = 0;
        double backLeft = 0;
        double backRight = 0;
        yMovement = gamepad1.left_stick_y;
        xMovement = gamepad1.left_stick_x;

        frontLeft += yMovement;
        frontRight -= yMovement;
        backLeft += yMovement;
        backRight -= yMovement;

        frontLeft -= xMovement;
        frontRight -= xMovement;
        backLeft += xMovement;
        backRight += xMovement;

        motorBackLeft.setPower(backLeft);
        motorFrontLeft.setPower(frontLeft);
        motorFrontRight.setPower(frontRight);
        motorBackRight.setPower(backRight);

    }

}


