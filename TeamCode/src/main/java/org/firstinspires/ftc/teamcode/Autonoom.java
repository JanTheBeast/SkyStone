package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;

@Autonomous (name = "Test", group = "Autonoom")
public class Autonoom extends LinearOpMode {
    private DcMotor motorLeft;
    private DcMotor motorRight;
    public int Runstate = 0;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("MotorLeft");
        motorRight = hardwareMap.dcMotor.get("MotorRight");
        Runstate = 0;

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if(opModeIsActive()){

            while(opModeIsActive()){
                //logUtils.Log(logUtils.logType.normal,String.valueOf( Runstate), 3);
                telemetry.update();

                switch(Runstate){
                    case 0:
                        telemetry.addData("Status: ", "Starting");
                        Runstate = 10;
                        break;

                    case 10:
                        telemetry.addData("Status: ", "Driving Forward");
                        turnLeftTrue(475, .5f, false);
                        Runstate = 20;

                    case 20:
                        Runstate = 30;
                        break;

                    case 30:
                        stop();
                }



            }
        }





    }
    public void linearMotion(long ms, float speed){
        double left, right;

            right = speed;
            left = -speed;

            motorLeft.setPower(left);
            motorRight.setPower(right);

            sleep(ms);

        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    //LEFT = TRUE & RIGHT = FALSE
    public void turnLeftTrue(long ms, float speed, boolean leftTurn){
        double left = 0;
        double right = 0;

            if(leftTurn){
                right = speed;
                left = speed;
            }else if(!leftTurn){
                right = -speed;
                left = -speed;
            }
            motorLeft.setPower(left);
            motorRight.setPower(right);

            sleep(ms);

        motorRight.setPower(0);
        motorLeft.setPower(0);
    }
}
