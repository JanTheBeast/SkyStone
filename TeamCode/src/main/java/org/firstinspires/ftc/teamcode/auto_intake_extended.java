package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous (name = "intake_red_extended", group = "")

public class auto_intake_extended extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private Servo capStone1;
    private Servo capStone2;
    private Servo claw1;
    private Servo claw2;

    //private long intakeTime;
    //private long intakeTime2;
    private DigitalChannel sensorTouch;
    private boolean autoIntake;
    //private boolean autoIntake2;
    public int Runstate = 0;
    public long startTime;
    private long DriveBack;

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
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");
        autoIntake = false;
        //autoIntake2 = false;
        Runstate = 0;
        DriveBack = 0;
        startTime = 0;

        capStone1.setPosition(0);
        capStone2.setPosition(1);
        claw1.setPosition(0.60);
        claw2.setPosition(0.60);

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        //intakeTime = System.currentTimeMillis();
        //intakeTime2 = System.currentTimeMillis();

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
                        DriveForward(-1,(int) DriveBack - 500);
                        TurnAxis(0.5, 650);
                        DriveForward(1,1600);
                        Runstate = 30;
                        break;

                    case 30:
                        IntakeOut(1, 500);
                        DriveForward(-1,2400);
                        TurnAxis(-0.5, 700);
                        autoIntake = true;
                        DriveForwardIntake(1, 2000);
                        Runstate = 40;
                        break;

                    case 40:
                        DriveForward(-1, (int) DriveBack - 50);
                        TurnAxis(0.5, 650);
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
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
                motorRight.setPower(power);
                motorLeft.setPower(-power);
                intakeLeft.setPower(-power);
                intakeRight.setPower(power);

                //sleep(time);
                if (sensorTouch.getState() == false || startTime + time < System.currentTimeMillis()) {
                    DriveBack = System.currentTimeMillis() - startTime;
                    intakeLeft.setPower(0);
                    intakeRight.setPower(0);
                    motorLeft.setPower(0);
                    motorRight.setPower(0);
                    startTime = 0;
                    autoIntake = false;
                }
        }
    }

    /*void DriveForwardIntake2(double power, int time) {
        while(autoIntake2) {
            motorRight.setPower(power);
            motorLeft.setPower(-power);
            intakeLeft.setPower(-power);
            intakeRight.setPower(power);

            //sleep(time);
            if(sensorTouch.getState() == false || System.currentTimeMillis() - intakeTime2 > time){
                DriveBack = System.currentTimeMillis() - intakeTime2;
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                motorLeft.setPower(0);
                motorRight.setPower(0);
                autoIntake2 = false;
            }

        }
    }*/

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