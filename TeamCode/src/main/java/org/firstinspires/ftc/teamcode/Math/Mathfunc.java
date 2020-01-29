package org.firstinspires.ftc.teamcode.Math;

public class Mathfunc {

    public double clamp(double min, double max, double var){
        if (var < min){
            var = min;
        } else if (var > max) {
            var = max;
        }

        return var;
    }

    public boolean range(double offset, double var, double goal){
        if (var > goal - offset && var < goal + offset) {
            return true;
        } else {
            return false;
        }
    }




}
