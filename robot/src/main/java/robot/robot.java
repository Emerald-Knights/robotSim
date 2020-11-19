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
}
