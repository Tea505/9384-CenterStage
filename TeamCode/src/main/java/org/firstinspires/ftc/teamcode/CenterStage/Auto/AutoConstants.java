package org.firstinspires.ftc.teamcode.CenterStage.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public class AutoConstants {

    // VELOCITY AND ACCELERATION
    public static final int MAX_VEL = 100;
    public static final int MAX_ACCEL = 100;
    public static final int FAST_VEL = 74;
    public static final int FAST_ACCEL = 74;
    public static final int VELO = 58;
    public static final int ACCEL = 58;

    public static final TrajectoryVelocityConstraint Vel0 = SampleMecanumDrive.getVelocityConstraint(VELO,
            Math.toRadians(180), Math.toRadians(180));
    public static final TrajectoryAccelerationConstraint Accel0 = SampleMecanumDrive.getAccelerationConstraint(ACCEL);


    // BLUE RIGHT START POSE
    public static final int BR_X = -36;
    public static final int BR_Y = 62;
    public static final double BR_H = Math.toRadians(270);
    public static final Pose2d BR_START = new Pose2d(BR_X, BR_Y, BR_H);

    // BLUE LEFT START POSE
    public static final int BL_X = 12;
    public static final int BL_Y = 62;
    public static final double BL_H = Math.toRadians(270);
    public static final Pose2d BL_START = new Pose2d(BL_X, BL_Y, BL_H);

    // RED RIGHT START POSE
    public static final int RR_X = 12;
    public static final int RR_Y = -62;
    public static final double RR_H = Math.toRadians(180);
    public static final Pose2d RR_START = new Pose2d(RR_X, RR_Y, RR_H);

    // RED LEFT START POSE
    public static final int RL_X = -36;
    public static final int RL_Y = -62;
    public static final double RL_H = Math.toRadians(180);
    public static final Pose2d RL_START = new Pose2d(RL_X, RL_Y, RL_H);

    // WHITE STACK POSES



}
