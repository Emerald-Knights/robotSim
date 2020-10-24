package com.example.robot;

public class ElapsedTime {
    private double startTime;
    static double startMoment;
    public ElapsedTime(){
        startTime=System.currentTimeMillis()-startMoment;
    }
    public double milliseconds(){
        return (System.currentTimeMillis()-(startTime+System.currentTimeMillis()));
    }
    public double seconds(){
        return milliseconds()/1000;
    }
    public void reset(){
        startTime=System.currentTimeMillis()-startMoment;
    }
}
