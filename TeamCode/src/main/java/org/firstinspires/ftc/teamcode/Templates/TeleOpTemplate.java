package org.firstinspires.ftc.teamcode.Templates;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Name", group = "")
public class TeleOpTemplate extends OpMode {

    //declaring motor variables
    private DcMotor motorLeft;
    private DcMotor motorRight;

    //declaring servo variables

    //declaring sensor variables

    //declaring variables
    private double drivedirectionspeed;

    @Override
    public void init(){
        //initializing motor names
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        //initializing servo names

        //initializing sensor names

        //initializing variables
        drivedirectionspeed = 0.75;

    }

    @Override
    public void loop(){
        //keep this function clean
        //do stuff in other functions
        DriveChecks();
    }

    private void DriveChecks(){
        //the variables for the motor speeds
        double right = -gamepad1.right_stick_y * drivedirectionspeed;
        double left = gamepad1.left_stick_y * drivedirectionspeed;

        //setting the motor speeds
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }
}
