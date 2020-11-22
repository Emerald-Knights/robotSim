/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import static robot.utilities.*;
/**
 *
 * @author thomas
 */
public class BNO055IMU {
    //lol this class just returns potition's position because making it look like the real thing

    Position position;
    double angleDiff;
    public BNO055IMU(Position position, double angleDiff){
        this.position=position;
        this.angleDiff=angleDiff;
    }
    public Orientation getAngularOrientation(){

        return new Orientation(angleWrap(RobotSimulator.robert.position.heading-angleDiff));
    }
}
