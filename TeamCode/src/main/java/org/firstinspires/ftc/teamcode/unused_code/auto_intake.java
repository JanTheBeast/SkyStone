package org.firstinspires.ftc.teamcode.unused_code;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Disabled
@Autonomous (name = "intake_red", group = "")

public class auto_intake extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private long intakeTime;
    private DigitalChannel sensorTouch;
    private boolean autoIntake;
    public int Runstate = 0;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        sensorTouch = hardwareMap.digitalChannel.get("sensorTouch");
        autoIntake = false;
        Runstate = 0;

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
                        DriveForward(-1,1000);
                        TurnAxis(0.5, 750);
                        DriveForward(1,1800);
                        Runstate = 30;
                        break;

                    case 30:
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

    @Disabled
    @TeleOp(name = "TankDrive", group = "")
    public static class TankDrive extends OpMode {
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
}