package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
FUNCTION:
    Autonomous: Uses gyro only

    Steps:
        <optional delay>
        Drive within shooting distance
        Shoot ball(s)
        Drive to center park position
        Stop
*/

@Autonomous(name="2 Center Park (Gyro)", group="Autonomous")
@Disabled
public class P_Auto_Center_Park_with_Gyro_Drive extends LinearOpMode
{
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry,this);
    private Initialize init = new Initialize(telemetry);

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        //cmds.InitializeHW(robot);
        init.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.TIME_AUTO_DELAY);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Move close enough to shoot balls
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.GyroDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_LONG_TO_SHOOT, 0, 5.0);
        }
        else //SHORT
        {
            cmds.GyroDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_SHORT_TO_SHOOT, 0, 5.0);
        }

        robot.motorLaunch.setPower(Configuration.POWER_LAUNCH);

        //Use delay until ball launch is ready for use
        sleep(2000);

        robot.motorCollect.setPower(1.0);

        cmds.Shoot(robot);

        robot.motorCollect.setPower(0);

        //Drive to center
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.GyroDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_LONG_TO_PARK, 0, 5.0);
        }
        else //SHORT
        {
            cmds.GyroDrive(robot, Configuration.POWER_DRIVE, Configuration.DIST_SHORT_TO_PARK, 0, 5.0);
            cmds.GyroDrive(robot, Configuration.POWER_DRIVE, 6, 0, 5.0);
        }

        cmds.StopDriving(robot);

        telemetry.addData("Status","Autonomous Complete!");
        telemetry.update();
    }
}
