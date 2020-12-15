package programs;

import java.util.ArrayList;

import robot.LinearOpMode.*;
import robot.*;

public class blue extends LinearOpMode {
    robot boWei = RobotSimulator.robert;

    enum Cases {
        one, two, three, four
    };
    Cases c= Cases.two;

    
    @Override
    public void runOpMode(){
        boWei.init();
        waitForStart();

        //start ur code here
        //case 1 (closest)
        if(c==Cases.one){
            move(1558, 1);
            move(200, -1);
            strafe(420, -1);
            move(250, 1);
        }
        //case 2 (we are going to need to turn on this one)
        else if(c==Cases.two){
            move(1857, 1);
            turning(-Math.PI/4.0);
            move(350, 1);
            move(300, -1);
            turning(-Math.PI);
            move(150, 1);
        }
        
        //case 3 (furthest)
        else if(c==Cases.three){
            move(2700, 1);
            move(950, -1);
        }
        
        else if (c == Cases.four) {
            turning(Math.PI);
            turning(Math.PI/2.0);
        }
        

    }
    
    public double checkAngle(double angle){
        if (angle == Math.PI){
            return Math.PI;
        }
        return angle % Math.PI;
    }

    //uses global angle
    public void turning(double radians){
        double angle = boWei.imu.getAngularOrientation().firstAngle;
        int direction = 1;
        while(Math.abs(angle) < Math.abs(radians)- 0.1) {
            angle = boWei.imu.getAngularOrientation().firstAngle;
            if (checkAngle(radians - angle) < 0){
                direction = -1;
            }
            else if (checkAngle(radians - angle) > 0){
                direction = 1;
            }
            if (angle <= radians/3) {
                boWei.leftFront.setPower(direction * (-( Math.abs(angle / (radians/3.0)) * 0.8 + 0.1)));
                boWei.leftBack.setPower (direction * (-( Math.abs(angle/ (radians/3.0)) * 0.8 + 0.1)));
                boWei.rightFront.setPower(direction * ( Math.abs(angle/ (radians/3.0)) * 0.8 + 0.1));
                boWei.rightBack.setPower(direction * ( Math.abs(angle/ (radians/3.0)) * 0.8 + 0.1));
                telemetry.addData("Angle:" + (angle / (radians/3)));

                //try {
                //Thread.sleep(10);
                //} catch (InterruptedException e) {
                //}
            }
            else if (angle < 2.0/3 *radians && radians/3.0 < angle) {
                boWei.leftFront.setPower(direction * (-0.8));
                boWei.leftBack.setPower(direction * (-0.8));
                boWei.rightFront.setPower(direction * (0.8));
                boWei.rightBack.setPower(direction * (0.8));
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                }
            }
            else if (angle >= 2.0/3.0 * radians){
                boWei.leftFront.setPower(direction * (-((Math.abs( checkAngle(radians - (angle) / (radians/3.0)))) * 0.8 + 0.1)));
                boWei.leftBack.setPower(direction * (-((Math.abs (checkAngle(radians - (angle)/ (radians/3.0)))) * 0.8 + 0.1)));
                boWei.rightFront.setPower(direction * ((Math.abs(checkAngle(radians - (angle)/ (radians/3.0)))) * 0.8 + 0.1));
                boWei.rightBack.setPower(direction * ((Math.abs(checkAngle(radians - (angle)/ (radians/3.0)))) * 0.8 + 0.1));
            }
            telemetry.addData("Angle:" + angle);
            telemetry.addData("Left:" + boWei.leftFront.getPower());
            telemetry.update();
        }
        boWei.leftFront.setPower(0);
        boWei.leftBack.setPower(0);
        boWei.rightFront.setPower(0);
        boWei.rightBack.setPower(0);
        boWei.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        boWei.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void move(int ticks, int direction) {
        while (Math.abs(boWei.leftFront.getCurrentPosition()) < Math.abs(ticks) && opModeIsActive()) {
            if (Math.abs(boWei.leftFront.getCurrentPosition()) <= Math.abs(ticks/3.0)) {
                boWei.leftFront.setPower(direction *((Math.abs(boWei.leftFront.getCurrentPosition())/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                boWei.leftBack.setPower(direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                boWei.rightFront.setPower(direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                boWei.rightBack.setPower(direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                telemetry.addData("rightFront:" + boWei.rightFront.getCurrentPosition());
                telemetry.addData("rightFront pow: "+ boWei.rightFront.getPower());
                
            } else if (Math.abs(boWei.leftFront.getCurrentPosition() )< 2.0/3 * Math.abs(ticks) && Math.abs(ticks/3.0) < Math.abs(boWei.leftFront.getCurrentPosition())) {
                boWei.leftFront.setPower(direction * (0.8));
                boWei.leftBack.setPower(direction * (0.8));
                boWei.rightFront.setPower(direction * (0.8));
                boWei.rightBack.setPower(direction * (0.8));
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                }
            }
            else if (Math.abs(boWei.leftFront.getCurrentPosition()) >= 2.0/3.0 * Math.abs(ticks)){
                boWei.leftFront.setPower(direction * (((Math.abs(ticks) - Math.abs(boWei.leftFront.getCurrentPosition()))/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                boWei.leftBack.setPower(direction * (((Math.abs(ticks) - Math.abs(boWei.leftFront.getCurrentPosition()))/ Math.abs(ticks/3.0)) * 0.8 + 0.1));
                boWei.rightFront.setPower(direction * (((Math.abs(ticks) - Math.abs(boWei.leftFront.getCurrentPosition()))/ Math.abs((ticks/3.0))) * 0.8 + 0.1));
                boWei.rightBack.setPower(direction * (((Math.abs(ticks) - Math.abs(boWei.leftFront.getCurrentPosition()))/ Math.abs((ticks/3.0))) * 0.8 + 0.1));
            }
            telemetry.addData("leftFront:" + Math.abs(boWei.leftFront.getCurrentPosition()));
            telemetry.update();

        }
        boWei.leftFront.setPower(0);
        boWei.leftBack.setPower(0);
        boWei.rightFront.setPower(0);
        boWei.rightBack.setPower(0);
        boWei.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        boWei.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    
    public void strafe(int ticks, int direction) {
        while (Math.abs(boWei.leftFront.getCurrentPosition()) < ticks && opModeIsActive()) {
            if (Math.abs(boWei.leftFront.getCurrentPosition()) <= ticks/3.0) {
                boWei.leftFront.setPower(-direction *((Math.abs(boWei.leftFront.getCurrentPosition())/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.leftBack.setPower(direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.rightFront.setPower(direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.rightBack.setPower(-direction * ((Math.abs(boWei.leftFront.getCurrentPosition())/ (ticks/3.0)) * 0.8 + 0.1));
                telemetry.addData("rightFront:" + boWei.rightFront.getCurrentPosition());
                telemetry.update();
                //try {
                    //Thread.sleep(10);
                //} catch (InterruptedException e) {
                //}
            } else if (Math.abs(boWei.leftFront.getCurrentPosition() )< 2.0/3 * ticks && ticks/3.0 < Math.abs(boWei.leftFront.getCurrentPosition())) {
                boWei.leftFront.setPower(-direction * (0.8));
                boWei.leftBack.setPower(direction * (0.8));
                boWei.rightFront.setPower(direction * (0.8));
                boWei.rightBack.setPower(-direction * (0.8));
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                }
            }
            else if (Math.abs(boWei.leftFront.getCurrentPosition()) >= 2.0/3.0 * ticks){
                boWei.leftFront.setPower(-direction * (((ticks - Math.abs(boWei.leftFront.getCurrentPosition()))/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.leftBack.setPower(direction * (((ticks - Math.abs(boWei.leftFront.getCurrentPosition()))/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.rightFront.setPower(direction * (((ticks - Math.abs(boWei.leftFront.getCurrentPosition()))/ (ticks/3.0)) * 0.8 + 0.1));
                boWei.rightBack.setPower(-direction * (((ticks - Math.abs(boWei.leftFront.getCurrentPosition()))/ (ticks/3.0)) * 0.8 + 0.1));
            }
            telemetry.addData("leftFront:" + Math.abs(boWei.leftFront.getCurrentPosition()));
            telemetry.update();

        }
        boWei.leftFront.setPower(0);
        boWei.leftBack.setPower(0);
        boWei.rightFront.setPower(0);
        boWei.rightBack.setPower(0);
        boWei.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        boWei.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
//yoink
//hi blah blah blah what is this