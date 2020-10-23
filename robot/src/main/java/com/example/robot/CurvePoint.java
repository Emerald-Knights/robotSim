package com.example.robot;

public class CurvePoint {
    public double x;
    public double y;
    public double angle;


    public CurvePoint(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle=angle;

    }

    public CurvePoint(CurvePoint thisPoint){
        this.x = thisPoint.x;
        this.y = thisPoint.y;
        this.angle = thisPoint.angle;

    }

    public Point toPoint(){
        return new Point(x, y);
    }
    public void setPoint(Point point){
        this.x=point.x;
        this.y=point.y;
    }
}
