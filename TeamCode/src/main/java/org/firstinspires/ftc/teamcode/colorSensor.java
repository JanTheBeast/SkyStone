package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "colorTest", group = "")
public class colorSensor extends LinearOpMode {
    private ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException{

        colorSensor = hardwareMap.colorSensor.get("ColorSensor");

        waitForStart();

        while (opModeIsActive() && isStopRequested()){

            telemetry.addData("red: ", colorSensor.red());
            telemetry.addData("green: ", colorSensor.green());
            telemetry.addData("blue: ", colorSensor.blue());
            telemetry.addData("alpha: ", colorSensor.alpha());
            telemetry.update();
        }

    }

}
