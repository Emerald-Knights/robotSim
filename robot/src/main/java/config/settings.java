package config;

import java.awt.Color;

public class settings {
    public final static String botImage="robot/src/main/java/robot/robotH.png"; //if you have a picture of your own robot put it here
    public final static String fieldImage="robot/src/main/java/robot/field.png"; //you can change the field image here. field1.png is a custom colored one lol
    public final static Color fieldColor= new Color(211, 211, 211); //you can choose the color of the background too. (142, 210, 153) goes well with the custom colored field
    public final static Color lineColor= new Color(0, 0,0 ); //color of lines if you doing pure pursuit stuff

    // different computers with different displays will need to adjust this number so the field is big enough
    public final static int frameLengthWidth=576; //i found 576 works well at 1920x1080 and 1200 works well at 3000x2000

    //these are all estimates so u don't need to be too accurate
    public final static double gearRatio=2/1; //how many times does the wheel rotate if the motor rotates once
    public final static int encoderTickPerRev=576; //search up how many encoder ticks = 1 revolution for your motor, neverrest 40s have 1120 per revolution
    public final static int wheelRadius=2; //radius of your wheel in inches
    public final static double robotLength=18; //inches
    public final static double robotWidth=18;
    public final static double maxVelocity=60; //inches per second
}
