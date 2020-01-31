package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU {

    BNO055IMU imu;
    Orientation angles;

    public IMU(BNO055IMU imu){
        this.imu = imu;
    }

    public void initialize(){
        BNO055IMU.Parameters IMUParameters = new BNO055IMU.Parameters();
        IMUParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        IMUParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        IMUParameters.calibrationDataFile = "AdafruitIMUCalibration.json";

        imu.initialize(IMUParameters);
    }

    public double currentHeading(){
        double currentHeading;
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        currentHeading = angles.firstAngle;
        return currentHeading;
    }

    public double[] printAngles(){
        double[] values;

        values = new double[3];
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        values[0] = angles.firstAngle;
        values[1] = angles.secondAngle;
        values[2] = angles.thirdAngle;

        return values;
    }

}
