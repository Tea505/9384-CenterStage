package org.firstinspires.ftc.teamcode.opmodes.Auto.Basic;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Tuning.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.common.Commands.Auto.IntakeReverseCommand;
import org.firstinspires.ftc.teamcode.common.Commands.Auto.IntakeStopCommand;
import org.firstinspires.ftc.teamcode.common.Commands.Auto.LiftCommand;
import org.firstinspires.ftc.teamcode.common.Commands.Auto.OuttakeCommand;
import org.firstinspires.ftc.teamcode.common.Hardware.Constants.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware.Contraptions.Intake;
import org.firstinspires.ftc.teamcode.common.Hardware.Contraptions.Mitsumi;
import org.firstinspires.ftc.teamcode.common.Util.InfoFiles;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous (name = "BASIC_Blue Left")
public class Bsc_BlueLeftBK extends CommandOpMode {

    // Contraptions
    private final Mitsumi mitsumi = new Mitsumi(this);
    private final Intake intake = new Intake(this);

    // telemetry
    private final InfoFiles infoFiles = new InfoFiles(telemetry);

    // drivetrain
    public SampleMecanumDrive drive;

    // Timer
    private final ElapsedTime timer = new ElapsedTime();
    private double endTime = 0;

    // Trajectories
    public static TrajectorySequence preloadCenter;

    public static TrajectorySequence toBackdrop;

    public static TrajectorySequence park;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        drive = new SampleMecanumDrive(hardwareMap);
        mitsumi.initialize(hardwareMap);
        mitsumi.autoInit();

        intake.initialize(hardwareMap);

        Pose2d startpose = new Pose2d(0,0, Math.toRadians(0));

        while (!isStarted()) {
            telemetry.addLine("Auto in Init");
            telemetry.addLine();

            telemetry.addData("X", startpose.getX());
            telemetry.addData("Y", startpose.getY());
            telemetry.addData("Heading", startpose.getHeading());
            telemetry.update();
        }

        // Center Trajectory
        preloadCenter = drive.trajectorySequenceBuilder(startpose)
            .setConstraints(Globals.MaxVel, Globals.MaxAccel)
            .forward(29)

            .build();

        toBackdrop = drive.trajectorySequenceBuilder(preloadCenter.end())
            .setConstraints(Globals.MaxVel, Globals.MaxAccel)

            .back(4)
            .turn(Math.toRadians(-90))
            .back(33.8)
            .strafeLeft(3.2)

            .build();

        park = drive.trajectorySequenceBuilder(new Pose2d())
            .setConstraints(Globals.MaxVel, Globals.MaxAccel)

            .strafeRight(15)
            .back(5)

            .build();


       CommandScheduler.getInstance().schedule(
               new SequentialCommandGroup(
                       new InstantCommand(timer::reset),

                       new InstantCommand(() -> drive.followTrajectorySequence(preloadCenter))
                               .alongWith(new IntakeReverseCommand()),

                       new WaitCommand(700),
                       new IntakeStopCommand(),

                       new InstantCommand(() -> drive.followTrajectorySequence(toBackdrop))
                               .alongWith(new LiftCommand(1500, 1)),

                       new WaitCommand(700),
                       new OuttakeCommand(),

                       new LiftCommand(100, 0.65)
                               .alongWith(new InstantCommand(() -> drive.followTrajectorySequence(park))),

                       new InstantCommand(() -> endTime = timer.seconds())
               )
       );
    }


    @Override
    public void run() {
        // Run OpMode
        super.run();

        // ☺
        telemetry.addData("Runtime: ", endTime == 0 ? timer.seconds() : endTime);
        telemetry.addLine();

        infoFiles.Telemetry(telemetry);
        telemetry.update();
    }
}
