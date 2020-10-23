/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.robot;

import java.util.ArrayList;
import java.util.List;

public class utilities {
    //just some nice methods

    public static double angleWrap(double angle){
        //ensures that the angle is from -pi to pi
        while(angle>Math.PI){
            angle-= 2*Math.PI;
        }
        while(angle<-Math.PI){
            angle+=2*Math.PI;
        }
        return angle;
    }
    public static List<Point> proximityPathIntersects(Point center, double radius, Point lineStart, Point lineEnd) {
        if (Math.abs(lineStart.y - lineEnd.y) < .003) {
            lineStart.y = lineEnd.y + .003;
        }
        if (Math.abs(lineStart.x - lineEnd.x) < .003) {
            lineStart.x = lineEnd.x + .003;
        }
        double slopeLine=(lineEnd.y-lineStart.y)/(lineEnd.x-lineStart.x);

        double transX1=lineStart.x-center.x;
        double transY1=lineStart.y-center.y;


        //using linestart as point in point slope intercept form
        //using quad to find x with x^2+y^2=r^2 and y-y1=m(x-x1)
        double quadA=1.0+Math.pow(slopeLine, 2);
        double quadB= -2.0*Math.pow(slopeLine, 2)*transX1+2.0*slopeLine*transY1;
        double quadC=Math.pow(slopeLine, 2.0)*Math.pow(transX1, 2)-2.0*slopeLine*transY1*transX1+Math.pow(transY1, 2.0)-Math.pow(radius, 2.0);

        double minX=Math.min(lineStart.x, lineEnd.x);
        double maxX=Math.max(lineStart.x, lineEnd.x);

        double minY=Math.min(lineStart.y, lineEnd.y);
        double maxY=Math.max(lineStart.y, lineEnd.y);
        List<Point> intersections = new ArrayList<>();
        try {
            double point1x=(-quadB+Math.sqrt(Math.pow(quadB,2.0)-4.0*quadA*quadC))/2/quadA;
            //point slope form
            double point1y= slopeLine*(point1x-transX1)+transY1;

            point1x+=center.x;
            point1y+=center.y;

            if(point1x> minX && point1x<maxX && point1y>minY && point1y<maxY){
                intersections.add(new Point(point1x, point1y));
            }
        }
        catch (Exception e){

        }
        try{
            double point2x=(-quadB-Math.sqrt(Math.pow(quadB,2.0)-4.0*quadA*quadC))/2/quadA;
            double point2y= slopeLine*(point2x-transX1)+transY1;


            point2x+=center.x;
            point2y+=center.y;

            if(point2x> minX && point2x<maxX && point2y>minY && point2y<maxY){
                intersections.add(new Point(point2x, point2y));
            }

        }
        catch (Exception e){

        }
        return intersections;
    }
    
    //finds distance from (x1,y1) to (x2,y2)
    public static double distanceToPoint (double x1, double y1, double x2, double y2){
        return Math.hypot(x2-x1, y2-y1);
    }
    public static double distanceToPoint(Point x, Point y){
        return Math.hypot(y.x-x.x, y.y-x.y);
    }

    //returns the power of motors from wanted y and x and turn powers
    public static double[] motorPower(double x, double y, double turn){

        double[] powers=new double[4];
        //double power=Math.hypot(x,y);
        double angle=Math.atan2(y,x);

        //fl, bl, br, fr
        powers[0]=-1* Math.sin(angle+Math.PI/4)-turn;
        powers[1]=-1*Math.cos(angle+Math.PI/4)-turn;
        powers[2]=-1*Math.sin(angle+Math.PI/4)+turn;
        powers[3]=-1*Math.cos(angle+Math.PI/4)+turn;

        return powers;
    }
    //updated version of method above
    public static double[] getPowers(double y, double x, double angular, double maxPower){
        double[][] inputs={{y}, {x}, {angular}};
        double div=1.0;
        double[][] og={ {1,1,-1/div}, {1,-1,-1/div}, {1,1,1/div}, {1,-1,1/div}};
        
        double[][] firstIt=matrixMultiplication(og, inputs);
        
        double greatest= Math.abs(firstIt[0][0]);
        for(int i=0; i< firstIt.length; i++){
            for(int j=0; j<firstIt[i].length; j++){
                if(Math.abs(firstIt[i][j])>greatest){
                    greatest=Math.abs(firstIt[i][j]);
                }
            }
        }

        if(greatest<=maxPower){
            greatest=1;
        }

        double constant=maxPower/greatest;

        //System.out.println(constant);
        double[][] karray= { {constant,constant,constant/div}, {constant,-constant,constant/div}, {constant,constant,-constant/div},{constant,-constant,-constant/div} };
        double[][] finalIt=matrixMultiplication(karray, inputs);
        double[] powers= new double[finalIt.length];
        for(int i=0; i< finalIt.length; i++){
            powers[i]=finalIt[i][0];
        }
        return powers;
        
        
    }

    //just standard matrix multiplication
    public static double[][] matrixMultiplication(double[][] d, double[][] d1){
        if(d[0].length!=d1.length){
            return null;
        }
        double[][] products= new double[d.length][d1[0].length];

        for(int i=0; i<d.length; i++){
            for(int i1=0; i1<d1[0].length; i1++){
                double sum=0;
                for(int j=0; j<d1.length; j++){
                    sum+=d[i][j]*d1[j][i1];
                }
                products[i][i1]=sum;
            }
            
        }
        return products;
    }
    
}
