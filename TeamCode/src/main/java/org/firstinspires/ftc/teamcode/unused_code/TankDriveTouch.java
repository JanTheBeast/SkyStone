package org.firstinspires.ftc.teamcode.unused_code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TankDriveTouch", group = "")
public class TankDriveTouch extends OpMode{

    //declaring motor variables
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeRight;
    private DcMotor intakeLeft;

    //declaring servo variables
    private Servo capStone1;
    private Servo capStone2;
    private Servo claw1;
    private Servo claw2;

    //declaring sensor variables
    private DigitalChannel sensorTouch;

    //declaring variables
    private int intakeState;
    private double drivedirectionspeed;

    @Override
    public void init() {
        //initializing motor names
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");

        //initializing servo names
        capStone1 = hardwareMap.servo.get("capStone1");
        capStone2 = hardwareMap.servo.get("capStone2");
        claw1 = hardwareMap.servo.get("claw1");
        claw2 = hardwareMap.servo.get("claw2");

        //initializing sensor names
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");

        //initializing variables
        intakeState = 0;
        drivedirectionspeed = 0.75;
    }

    @Override
    public void loop() {
        DriveChecks();
        IntakeChecks();
        ClawChecks();
    }

    //Drivecheck function to keep loop() clean
    private void DriveChecks() {

        //the variables for the motor speeds
        double right = -gamepad1.right_stick_y * drivedirectionspeed;
        double left = gamepad1.left_stick_y * drivedirectionspeed;

        //setting the motor speeds
        motorLeft.setPower(left);
        motorRight.setPower(right);

        //clamping the dds and making it go up and down with a button
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

//------------------------------------------------------------------------------

    //intakechecks function to keep loop() clean
    private void IntakeChecks() {

        //standard speed for motors
        double intake = 1;

        //state maching which switches between the intakestates
        switch(intakeState){
            case 0:
                //intake is off
                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                if(gamepad2.y){
                    intakeState = 10;
                }

                break;

            case 10:
                //intake takes in block
                intakeLeft.setPower(-intake);
                intakeRight.setPower(intake);

                //when block is picked up is goes on
                if(!sensorTouch.getState()){
                    intakeState = 20;
                }else if(gamepad2.x){
                    //when the it is on with no block you can turn it off with x
                    intakeState = 0;
                }
                break;

            case 20:
                //intake is off
                intakeLeft.setPower(0);
                intakeRight.setPower(0);

                if(gamepad2.x){
                    intakeState = 30;
                }

                break;

            case 30:
                //intake spits out block
                if(gamepad2.x){
                    intakeLeft.setPower(intake);
                    intakeRight.setPower(-intake);
                }else if(!gamepad2.x){
                    intakeState = 0;
                }

                break;
        }
    }

    //--------------------------------------------------------------------------

    //Clawcheck function to keep loop() clean
    private void ClawChecks() {

        if(gamepad2.dpad_up){
            capStone1.setPosition(0);
            capStone2.setPosition(1);
        }else if(gamepad2.dpad_down){
            capStone1.setPosition(0.33);
            capStone2.setPosition(0.60);
        }

        if(gamepad2.left_bumper){
            claw1.setPosition(0.60);
            claw2.setPosition(0.60);
        }else if(gamepad2.right_bumper){
            claw1.setPosition(0.80);
            claw2.setPosition(0.40);
        }

    }
}
