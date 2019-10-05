package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Rijden", group="")

public class Rijden extends OpMode{
    private DcMotor MotorLeft;
    private DcMotor MotorRight;
    double drivedirectionspeed = 1;

    //declare motors
    @Override
    public void init() {
        MotorLeft = hardwareMap.dcMotor.get("MotorLeft");
        MotorRight = hardwareMap.dcMotor.get("MotorRight");
        drivedirectionspeed = 1;
    }

    //checking loop
    @Override
    public void loop() {
        DriveChecks();
    }
    //Drivecheck function
    void DriveChecks() {
        double Left = drivedirectionspeed * gamepad1.left_stick_y;
        double Right = drivedirectionspeed * -gamepad1.right_stick_y;

        MotorLeft.setPower(Left);
        MotorRight.setPower(Right);

        if (gamepad1.right_trigger > 0.5 && drivedirectionspeed < 5) {
            drivedirectionspeed += 0.0025f;
        } else if (gamepad1.left_trigger > 0.5 && drivedirectionspeed > 0.003) {
            drivedirectionspeed -= 0.0025f;
        }

        telemetry.addData("DriveDirectionSpeed", drivedirectionspeed);
    }

}

