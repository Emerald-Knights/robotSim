/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;
import java.awt.Robot;

import static robot.utilities.*;

public class Position extends Thread{
    double x,y,heading;
    /*
    double xPow;
    double yPow;
    double anglePow;
    */
    final double encoderToInch=1; //will need to tune
    final double horizEncoderToInch=1; //will need to tune
    boolean keepRun=true;
    robot rob;

    //use inches for x and y positions
    //sets up the initial position of the robot
    public Position(double x, double y, double heading, robot rob){
        this.x=x;
        this.y=y;
        this.heading=heading;
        this.rob=rob;
    }

    @Override
    public void run(){             
        while(keepRun){
            if(!RobotSimulator.pause) {

                //Cycles through all motors, seeing what mode they are in and thus whether or not to increment encoder position
                for (int i = 0; i < rob.driveTrain.length; i++) {
                    if (rob.driveTrain[i].getMode() ==DcMotor.RunMode.RUN_USING_ENCODER || rob.driveTrain[i].getMode() ==DcMotor.RunMode.RUN_TO_POSITION) {
                        rob.driveTrain[i].position += Math.round(rob.driveTrain[i].getPower() *27);
                    } else if (rob.driveTrain[i].getMode() ==DcMotor.RunMode.STOP_AND_RESET_ENCODER) {
                        rob.driveTrain[i].setPower(0);
                        rob.driveTrain[i].position = 0;
                    }
                    if (rob.driveTrain[i].getMode() ==DcMotor.RunMode.RUN_TO_POSITION && rob.driveTrain[i].position > rob.driveTrain[i].wantPos) {
                        rob.driveTrain[i].setPower(0);
                    }
                }

                //Here we want to find the direction the robot is traveling relative to itself, eg if it is driving forwards or strafing to the right
                //robot system is analogous to right triangle, base is made up of leftfront and rightback which runs along x axis while height is leftback and rightfront that runs along y axis
                //Using this model, the angle of the vector that is formed by adding the leftfront and rightback vector with the leftback and rightfront vector (perpendicular) can be calculated
                //The angle of the vector is 45 degrees to the right of the direction the robot will end up travelling so we add 45 degrees to get the actual direction the robot will travel in
                //Speed will be magnitude of above vector
                double angleRobot = angleWrap(Math.PI / 4 + Math.atan2(rob.leftBack.getPower() + rob.rightFront.getPower(), rob.leftFront.getPower() + rob.rightBack.getPower())); //if y is backwards, negate
                double powRobot = Math.hypot(rob.leftBack.getPower() + rob.rightFront.getPower(), rob.leftFront.getPower() + rob.rightBack.getPower())*.849;

                //we calculated the angle the robot was moving relative to where its facing, now need to calculate the angle the robot is moving relative to field and update its position accordingly
                //The angle it is traveling in relative to the field is the direction it is traveling relative to the robot added to the angle the robot is facing relative to the field
                double angleWorld = angleWrap(angleRobot + heading);
                setX(getX() + Math.cos(angleWorld) * powRobot );
                setY(getY() - Math.sin(angleWorld) * powRobot );
                //x+=Math.cos(angleWorld)*powRobot;
                //y-=Math.sin(angleWorld)*powRobot;
                setHeading(angleWrap(getHeading() + (rob.rightBack.getPower() + rob.rightFront.getPower() - rob.leftBack.getPower() - rob.leftFront.getPower()) *.06/4));


                //heading=angleWrap(heading+(rob.rightBack.getPower() + rob.rightFront.getPower()-rob.leftBack.getPower() -rob.leftFront.getPower())/50);
            }
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e){}
        }
        
    }

    public void setX(double x) {
        if(!RobotSimulator.pause){
            this.x = x;
        }
    }

    public void setY(double y) {
        if(!RobotSimulator.pause){
            this.y = y;
        }
    }

    public void setHeading(double heading) {
        if(!RobotSimulator.pause){
            this.heading = heading;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    public Point getPos(){
        return new Point(this.x, this.y);
    }
    public void setPosition(double x, double y, double heading){
        this.x=x;
        this.y=y;
        this.heading=heading;
    }
}
