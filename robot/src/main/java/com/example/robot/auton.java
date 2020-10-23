package com.example.robot;

import static com.example.robot.utilities.getPowers;


public class auton extends LinearOpMode {
    robot robert= RobotSimulator.robert;
    @Override
    public void runOpMode(){
        waitForStart();
        //TODO write ur actual "auton" here
        while(opModeIsActive()) {
            boolean x1= gamepad1.x;
            boolean y1= gamepad1.y;

            double lx=gamepad1.left_stick_x;
            double ly=-gamepad1.left_stick_y;
            double rx=gamepad1.right_stick_x;

            if (Math.abs(lx) <= 0.15) {
                lx = 0;
            }

            if (Math.abs(ly) <= 0.15) {
                ly = 0;
            }

            if (Math.abs(rx) <= 0.15) {
                rx = 0;
            }



            double lf = lx + ly + rx;
            double lb = ly - lx + rx;
            double rf = ly - lx - rx;
            double rb = lx + ly - rx;


            double max = Math.max(Math.max(Math.abs(lb), Math.abs(lf)), Math.max(Math.abs(rb), Math.abs(rf)));
            double magnitude = Math.sqrt((lx * lx) + (ly * ly) + (rx * rx));
            double ratio = magnitude / max;

            if (max == 0) {
                ratio=0;
            }


            robert.leftFront.setPower(lf * ratio);
            robert.leftBack.setPower(lb * ratio);
            robert.rightFront.setPower(rf * ratio);
            robert.rightBack.setPower(rb * ratio);

            telemetry.addData(robert.leftFront.getPower()+ "");
            telemetry.addData(robert.leftBack.getPower()+ "");
            telemetry.addData(robert.rightBack.getPower()+ "");
            telemetry.addData(robert.rightFront.getPower()+ "");
            telemetry.addData(ratio + "");
            telemetry.addData(lx + "");
            telemetry.addData(ly + "");
            telemetry.addData(rx + "");
            telemetry.update();
        }
    }
}
