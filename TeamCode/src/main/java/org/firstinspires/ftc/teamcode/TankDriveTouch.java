package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TankDriveTouch", group = "")
public class TankDriveTouch extends OpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;
    private Servo capStone1;
    private Servo capStone2;
    private Servo claw1;
    private Servo claw2;
    private DigitalChannel sensorTouch;

    private final double increment = 0.05;
    private final double pos_max = 1;
    private final double pos_min = 0;
    private int ms_cycle;

    int intakeState;

    int xbutton;
    //boolean intakeMotorOut;
   // boolean intakeMotorIn;
    //boolean intakeSwitchOut;
   // boolean intakeSwitchIn;

    double drivedirectionspeed;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        capStone1 = hardwareMap.servo.get("capStone1");
        capStone2 = hardwareMap.servo.get("capStone2");
        claw1 = hardwareMap.servo.get("claw1");
        claw2 = hardwareMap.servo.get("claw2");

        intakeState = 0;
        xbutton = 0;
        //intakeMotorIn = false;
        //intakeMotorOut = false;
        //intakeSwitchIn = false;
        //intakeSwitchOut = false;
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");

        drivedirectionspeed = 0.75;
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


        if(gamepad2.dpad_up){
            capStone1.setPosition(0);
            capStone2.setPosition(0.30);
        }else if(gamepad2.dpad_down){
            capStone1.setPosition(0.30);
            capStone2.setPosition(0);
        }

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
                }else if(gamepad2.x){
                    intakeState = 0;
                }
                break;

            case 20:
                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                if(gamepad2.x){
                    intakeState = 30;
                }

                break;

            case 30:
                if(gamepad2.x){
                    intakeLeft.setPower(intake);
                    intakeRight.setPower(-intake);
                }else if(!gamepad2.x){
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

        if (gamepad1.right_trigger > 0.5 && drivedirectionspeed < 1) {
            drivedirectionspeed += 0.005f;
        } else if (gamepad1.left_trigger > 0.5 && drivedirectionspeed > 0.2) {
            drivedirectionspeed -= 0.005f;
        } else if (gamepad1.right_bumper){
            drivedirectionspeed = 1f;
        } else if (gamepad1.left_bumper){
            drivedirectionspeed = 0.33f;
        }

        telemetry.addData("DriveDirectionSpeed", drivedirectionspeed);
        telemetry.update();
    }
}
