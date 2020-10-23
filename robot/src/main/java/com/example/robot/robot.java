/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.robot;

import java.util.ArrayList;
import java.util.List;
import static com.example.robot.RobotSimulator.frame;
import static com.example.robot.RobotSimulator.robert;
import static com.example.robot.utilities.*;
/**
 *
 * @author thomas
 */
public class robot {
    Position position;
    int currentPath=0;
    double maxI=10;
    
    DcMotor leftFront, leftBack, rightBack, rightFront;
    public DcMotor[] driveTrain = new DcMotor[4];
    BNO055IMU imu;
    //class that just defines the robot as a sum of its parts
    public robot(double x, double y, double heading) {
        position = new Position(x, y, heading, this);
        leftFront = new DcMotor();
        leftBack = new DcMotor();
        rightBack = new DcMotor();
        rightFront = new DcMotor();
        imu = new BNO055IMU(position, heading);

        driveTrain[0] = leftFront;
        driveTrain[1] = leftBack;
        driveTrain[2] = rightBack;
        driveTrain[3] = rightFront;
    }
        public void moveToPosition(double x, double y, double speed, double endAngle, double turnSpeed){
            double xDis=x-position.x; //x distance to point global
            double yDis=y-position.y; //y distance to point global

            double totalDis=Math.hypot(xDis,yDis); //total distance to point global
            double angleToPos=Math.atan2(yDis, xDis); //angle from robot to point
            double relativeAngle=angleWrap(position.heading-angleWrap(-angleToPos+Math.PI/2)); //angle robot is facing from where path ends

            double relativeX=Math.cos(relativeAngle)*totalDis; //x velocity robot needs to get to locaction, robot point
            double relativeY=Math.sin(relativeAngle)*totalDis; //y velocity robot needs to get to location, robot point

            double xPower=relativeX/(Math.abs(relativeX)+Math.abs(relativeY))*speed; //setting speeds
            double yPower=relativeY/(Math.abs(relativeX)+Math.abs(relativeY))*speed;

            double turnAngle=position.heading+endAngle; //amount u want to turn
            double turnPower=turnAngle*turnSpeed;

            for(int i=0; i<driveTrain.length; i++){
                //driveTrain[i].setPower(motorPower(xPower,yPower,turnPower)[i]*.8);
                driveTrain[i].setPower(getPowers(xPower,yPower,turnPower, .8)[i]);

            }


        }

        public CurvePoint getFollowPointPath(List<CurvePoint> pathPoints, double xPosRobot, double yPosRobot, double followRadius){

            CurvePoint follow=new CurvePoint(pathPoints.get(currentPath));
            int previousC=currentPath;
            for(int i=currentPath; i<previousC+2 && i<pathPoints.size()-1; i++){
                CurvePoint start=pathPoints.get(i);
                CurvePoint end=pathPoints.get(i+1);

                List<Point> intersections=proximityPathIntersects(new Point(xPosRobot, yPosRobot), followRadius, start.toPoint(), end.toPoint());
                if(intersections.size()>0){
                    follow.setPoint(intersections.get(0));
                    follow.angle=end.angle;
                    currentPath=i;

                    //double closestAngle=Integer.MAX_VALUE;
                    for(Point intersect: intersections){
                        if(Math.abs(distanceToPoint(intersect.x, intersect.y, end.x, end.y)) < Math.abs(distanceToPoint(follow.x, follow.y, end.x, end.y))){
                            follow.setPoint(intersect);
                        }
                    }
                }


            }
            return follow;

        }

        public void followCurve(List<CurvePoint> allPoints, double followDistance, double speed, double turnSpeed){
            if(turnSpeed>speed){
                speed=0;
            }
            CurvePoint follow=getFollowPointPath(allPoints, position.x, position.y, followDistance);
            //main.follow=follow;
            moveToPosition(follow.x, follow.y, speed, follow.angle, turnSpeed);
        }

        public void followCurveSync(List<CurvePoint> allPoints, double followDistance, double speed, double stopDis) {
            currentPath = 0;
            List<CurvePoint> allPointsF = new ArrayList<>(allPoints);
            double deltY = allPoints.get(allPoints.size() - 1).y - allPoints.get(allPoints.size() - 2).y;
            double deltX = allPoints.get(allPoints.size() - 1).x - allPoints.get(allPoints.size() - 2).x;

            allPointsF.remove(allPointsF.size() - 1);
            allPointsF.add(new CurvePoint(allPoints.get(allPoints.size() - 1).x + deltX, allPoints.get(allPoints.size() - 1).y + deltY, allPoints.get(allPoints.size() - 1).angle));
            //System.out.println("point " + allPointsF.get(allPoints.size() - 1).x + " " + allPointsF.get(allPoints.size() - 1).y);

            double kp=1;
            double ki=1;
            double kd=1;
            double error=Math.abs(distanceToPoint(position.x, position.y, allPoints.get(allPoints.size() - 1).x, allPoints.get(allPoints.size() - 1).y));
            double previousError=0;
            double p;
            double i=0;
            double d;

            //Point initialPos= robert.position.getPos();
            double velocity=0;
            Point previousPos=robert.position.getPos();
            while (Math.abs(distanceToPoint(position.x, position.y, allPoints.get(allPoints.size() - 1).x, allPoints.get(allPoints.size() - 1).y)) > stopDis) {
                Point pos= robert.position.getPos();
                velocity=distanceToPoint(previousPos, pos)/.1;


                previousError=error;
                error=Math.abs(distanceToPoint(position.x, position.y, allPoints.get(allPoints.size() - 1).x, allPoints.get(allPoints.size() - 1).y));



                p=error*kp;

                i+=ki*(error*(.01));
                if(i>maxI){
                    i=maxI;
                }
                else if(i<-maxI){
                    i=-maxI;
                }

                d=kd*(error-previousError)/.01;

                double pid=p+i+d;


                double turnSpeed = Math.abs(speed) * (angleWrap(allPoints.get(currentPath + 1).angle - position.heading)) / 3;
                followCurve(allPointsF, followDistance, speed - turnSpeed, turnSpeed);
                //System.out.println("x: " + position.x + " y: " + position.y + " speed: " + (speed - speed * (allPoints.get(currentPath + 1).angle - position.heading) / 10));
                //System.out.println("angle: " + position.heading + " angle speed: " + speed * (allPoints.get(currentPath + 1).angle - position.heading) / 10);


                previousPos=pos;
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }

        }
}
