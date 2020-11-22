/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import java.util.ArrayList;
import java.util.List;

import static robot.utilities.*;
import static robot.RobotSimulator.robert;

/**
 *
 * @author thomas
 */
public class robot {
    public Position position;
    public DcMotor leftFront, leftBack, rightBack, rightFront;
    public DcMotor[] driveTrain = new DcMotor[4];
    public BNO055IMU imu;

    //Odometry stuff
    //set this list equal to something if testing an odometry/ pure pursit thing
    public List<Point> path;
    public double lookahead; //lookahead distance, inches


    //class that just defines the robot and parts
    public robot(double x, double y, double heading) {
        position = new Position(x, y, heading, this);
        leftFront = new DcMotor();
        leftBack = new DcMotor();
        rightBack = new DcMotor();
        rightFront = new DcMotor();

        driveTrain[0] = leftFront;
        driveTrain[1] = leftBack;
        driveTrain[2] = rightBack;
        driveTrain[3] = rightFront;
    }
    public void init(){
        //this method does nothing but might help simulate your actual programs
    }


    /** use these methods for odometry, returns position in xy axis as defined by Vuforia
     *
     * As a refresher,
     * If you are standing in the Red Alliance Station looking towards the center of the field,
     *     - The X axis runs from your left to the right. (positive from the center to the right)
     *     - The Y axis runs from the Red Alliance Station towards the other side of the field
     *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
     *
     *
     */
    public double getX(){
        try{
            Thread.sleep(1);
        }
        catch(InterruptedException e){}
        return -position.y / position.pixelPerInch;
    }
    public double getY(){
        try{
            Thread.sleep(1);
        }
        catch(InterruptedException e){}
        return -position.x / position.pixelPerInch;
    }
    public double getHeading(){
        try{
            Thread.sleep(1);
        }
        catch(InterruptedException e){}
        return angleWrap(position.heading);
    }

}
