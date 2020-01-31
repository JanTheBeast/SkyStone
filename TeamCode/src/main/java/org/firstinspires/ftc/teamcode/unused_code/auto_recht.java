package org.firstinspires.ftc.teamcode.unused_code;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "recht", group = "")

public class auto_recht extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private Servo capStone1;
    private Servo capStone2;
    private Servo claw1;
    private Servo claw2;

    public int Runstate = 0;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        capStone1 = hardwareMap.servo.get("capStone1");
        capStone2 = hardwareMap.servo.get("capStone2");
        claw1 = hardwareMap.servo.get("claw1");
        claw2 = hardwareMap.servo.get("claw2");
        Runstate = 0;

        capStone1.setPosition(0);
        capStone2.setPosition(1);
        claw1.setPosition(0.60);
        claw2.setPosition(0.60);

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