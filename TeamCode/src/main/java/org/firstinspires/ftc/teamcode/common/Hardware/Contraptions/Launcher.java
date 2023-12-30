package org.firstinspires.ftc.teamcode.common.Hardware.Contraptions;

import com.acmerobotics.dashboard.config.Config;
import com.hydraulichydras.hydrauliclib.Util.Contraption;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Launcher extends Contraption {

    public static Servo launcher_angle;
    public static Servo droneTrigger;

    public static double SHOOT_POS = 0.52;
    public static double HORIZONTAL_POS = 0.35;

    public static double INIT_POS = 0.28;

    public static double SHOOT = 0.9;
    public static double LOAD = 0;
    public Launcher(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void initialize(HardwareMap hwMap) {

        launcher_angle = hwMap.get(Servo.class, "launcher_angle"); // no 4 control
        droneTrigger = hwMap.get(Servo.class, "droneTrigger");

        launcher_angle.setPosition(INIT_POS);

    }

    public void loop(Gamepad gamepad2) {

        if (gamepad2.dpad_up) {
            // shooting angle
            launcher_angle.setPosition(SHOOT_POS);
        } else if (gamepad2.dpad_down) {
            // horizontal angle
            launcher_angle.setPosition(HORIZONTAL_POS);
        }

        if (gamepad2.dpad_left) {
            // standby
            droneTrigger.setPosition(LOAD);
        } else if (gamepad2.dpad_right) {
            // shoot
            droneTrigger.setPosition(SHOOT);
        }
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Angle", launcher_angle.getPosition());
        telemetry.addData("Trigger", droneTrigger.getPosition());
        telemetry.update();
    }
}
