package config;

import java.awt.Color;

public class settings {
    public final static String botImage="robot/src/main/java/robot/robotH.png"; //if you have a picture of your own robot put it here
    public final static String fieldImage="robot/src/main/java/robot/field.png"; //if you hate the custom colored field image you can replace it here
    public final static Color fieldColor= new Color(142, 210, 153); //you can choose the color of the background too

    // different computers with different displays will need to adjust this number so the field is big enough
    public final static int frameLengthWidth=576; //i found 576 works well at 1920x1080 and 1200 works well at 3000x2000

    //our robot goes at about 2700 encoders per second so im gonna assume everyone elses robot goes about that fast (if yours doesn't that's too bad lol)
    public final static double gearRatio=2/1; //how many times does the wheel rotate if the motor rotates once
    public final static int encoderTickPerRev=1120; //search up how many encoder ticks = 1 revolution for your motor, neverrest 40s have 1120 per revolution
    public final static int wheelRadius=2; //radius of your wheel in inches
    public final static double robotRadius=9.5; //inches, approx distance from the center of the robot to the wheels, honestly just to approximate how fast your robot turns, doesn't matter too much
}
