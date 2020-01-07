package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Autonomous (name = "intake_red_extended", group = "")

public class auto_intake_extended extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private long intakeTime;
    private DigitalChannel sensorTouch;
    private boolean autoIntake;
    public int Runstate = 0;
    private long DriveBack;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");
        autoIntake = false;
        Runstate = 0;
        DriveBack = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        intakeTime = System.currentTimeMillis();

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
                        autoIntake = true;
                        DriveForwardIntake(1, 2000);
                        Runstate = 20;
                        break;

                    case 20:
                        DriveForward(-1,(int) DriveBack - 250);
                        TurnAxis(0.5, 750);
                        DriveForward(1,1600);
                        Runstate = 30;
                        break;

                    case 30:
                        IntakeOut(1, 500);
                        DriveForward(-1,1800);
                        TurnAxis(-0.5, 750);
                        DriveForwardIntake(1, 2000);
                        Runstate = 40;
                        break;

                    case 40:
                        DriveForward(-1, (int) DriveBack - 250);
                        TurnAxis(0.5, 750);
                        DriveForward(1,1800);
                        IntakeOut(1, 500);
                        DriveForward(-1,500);
                        stop();
                        break;

                }


            }
        }


    }

    private void TurnAxis(double power, int time) {
        motorRight.setPower(-power);
        motorLeft.setPower(-power);

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

    void DriveForwardIntake(double power, int time) {
        while(autoIntake) {
            motorRight.setPower(power);
            motorLeft.setPower(-power);
            intakeLeft.setPower(-power);
            intakeRight.setPower(power);

            //sleep(time);
            if(sensorTouch.getState() == false || System.currentTimeMillis() - intakeTime > time){
                DriveBack = System.currentTimeMillis() - intakeTime;
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                motorLeft.setPower(0);
                motorRight.setPower(0);
                autoIntake = false;
            }

        }
    }

    void IntakeIn(double power){
        intakeLeft.setPower(-power);
        intakeRight.setPower(power);

        if(sensorTouch.getState() == false){
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }

    }

    void IntakeOut(double power, int time){
        intakeLeft.setPower(power);
        intakeRight.setPower(-power);

        sleep(time);

        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }

}