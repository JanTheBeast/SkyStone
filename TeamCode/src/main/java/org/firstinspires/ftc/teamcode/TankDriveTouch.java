package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "TankDriveTouch", group = "")
public class TankDriveTouch extends OpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    private DigitalChannel sensorTouch;

    int intakeState;
    //boolean intakeMotorOut;
   // boolean intakeMotorIn;
    //boolean intakeSwitchOut;
   // boolean intakeSwitchIn;

    double drivedirectionspeed = 1;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        intakeState = 0;
        //intakeMotorIn = false;
        //intakeMotorOut = false;
        //intakeSwitchIn = false;
        //intakeSwitchOut = false;
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");

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

        switch(intakeState){
            case 0:
                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                if(gamepad2.y){
                    intakeState = 10;
                }

                break;

            case 10:
                intakeLeft.setPower(-intake);
                intakeRight.setPower(intake);

                if(sensorTouch.getState() == false){
                    intakeState = 20;
                }
                break;

            case 20:
                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                if(gamepad2.y){
                    intakeState = 30;
                }

                break;

            case 30:
                intakeLeft.setPower(intake);
                intakeRight.setPower(-intake);

                if(gamepad2.x){
                    intakeState = 0;
                }

                break;
        }

//--------------------------------------------------------------------------

        /*if (gamepad2.y && !intakeSwitchIn){
            if(intakeMotorIn){
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }else{
                intakeLeft.setPower(intake);
                intakeRight.setPower(intake);
                intakeMotorIn = true;
            }
            intakeSwitchIn = true;
        }else if(!gamepad2.y){
            intakeSwitchIn = false;
        }

        if (gamepad2.x && !intakeSwitchOut){
            if(intakeMotorOut){
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }else{
                intakeLeft.setPower(-intake);
                intakeRight.setPower(-intake);
                intakeMotorOut = true;
            }
            intakeSwitchOut = true;
        }else if(!gamepad2.x){
            intakeSwitchOut = false;
        }*/

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
