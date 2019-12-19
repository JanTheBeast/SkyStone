package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name = "TankDrive", group = "")
public class TankDrive extends OpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    double drivedirectionspeed;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        drivedirectionspeed = 1;
    }

    @Override
    public void loop() { DriveChecks(); }

    void DriveChecks() {
        double right = -gamepad1.right_stick_y * drivedirectionspeed;
        double left = gamepad1.left_stick_y * drivedirectionspeed;
        double intake = 1;

//--------------------------------------------------------------------------

        motorLeft.setPower(left);
        motorRight.setPower(right);

//--------------------------------------------------------------------------

        if (gamepad2.y) {
            intakeLeft.setPower(intake);
            intakeRight.setPower(-intake);
        }else if(gamepad2.x){
            intakeLeft.setPower(-intake);
            intakeRight.setPower(intake);
        }else {
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }

//--------------------------------------------------------------------------

        if (gamepad1.right_trigger > 0.5 && drivedirectionspeed < 5) {
            drivedirectionspeed += 0.0025f;
        } else if (gamepad1.left_trigger > 0.5 && drivedirectionspeed > 0.003) {
            drivedirectionspeed -= 0.0025f;
        }

        telemetry.addData("DriveDirectionSpeed", drivedirectionspeed);
        telemetry.update();
    }
}
