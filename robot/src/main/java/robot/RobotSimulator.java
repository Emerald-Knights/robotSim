/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import org.reflections.Reflections;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import config.settings;

/**
 *
 * @author thomas
 */
public class RobotSimulator extends Canvas {

    public static robot robert; //globally used robot
    static JFrame frame; //jframe that animation will take place in
    static Canvas pic; //canvas to draw on
    static Image bot; //global image of robot
    static Image field;
    static Image pauseSim;
    static boolean pause= true;

    static boolean end=false;
    static Canvas startMenu;
    static JFrame startFrame;
    static boolean start=false;

    //48 pixesl per inch, field is 12x12ft
    static final int length=settings.frameLengthWidth; //size of frame 576
    static final int height =settings.frameLengthWidth;
    
    static List<String> tele=new ArrayList<>(); //list that includes all the string that telemetry wants to display

    static JComboBox sus;
    static JButton startButton;

    static JButton reset;
    static JButton stop;
    static JButton repeat;

    static inputs keys;
    static mouseInputs rice;
    static programBox dropDown;

    static int programNum=0;

    static Color background=settings.fieldColor;

    static Font writting =new Font("Roboto", Font.PLAIN, settings.frameLengthWidth/50);
    static Font telemetryFont= new Font("Roboto", Font.PLAIN, settings.frameLengthWidth/40);

    public static void main(String[] args) {
        startFrame= new JFrame("EK 10582"); //making a small frame for program selection
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setBackground(background); //the background is whatever background you set it to in setting
        startMenu= new Canvas();
        startMenu.setBackground(background);

        int sL=(int)Math.round(length*4.0/5); //make the initial frame a fraction of the main frame
        int sW=(int)Math.round(length*3.0/5);

        Reflections reflections = new Reflections("programs");
        Set<Class<? extends LinearOpMode>> programs =reflections.getSubTypesOf(LinearOpMode.class); //get all programs in programs folder tha extend LinearOpMode

        if(programs.contains(OpMode.class)){ //idk why OpMode is included in the list but remove it because it is
            programs.remove(OpMode.class);
        }

        //make a list of all the programs as strings and display it inside a comboBox
        String[] programNames= new String[programs.size()];
        for(int i=0; i<programs.size(); i++){
            programNames[i]= programs.toArray()[i].toString();
        }
        sus = new JComboBox(programNames);

        //do the other stuff to set up a box
        dropDown= new programBox();
        sus.addActionListener(dropDown);
        sus.setBounds(sL/4, sL/5, sL/2, sW/6);
        sus.setFocusable(false);
        sus.setFont(writting);
        startFrame.add(sus);

        //make start button
        startButton= new JButton("S");
        startButton.setFocusable(false);
        startButton.setBounds(sL/2-sL/10, sW/2, sL/5, sW/8);
        startButton.setFont(writting);
        startButton.addActionListener( new ActionListener (){
            public void actionPerformed(ActionEvent e){
                start=true;
            }
        });
        startFrame.add(startButton);

        //do final things needed to make frame showable
        startMenu.setSize(sL, sW);
        startFrame.add(startMenu);
        startFrame.pack();
        startFrame.setResizable(false);

        startFrame.setVisible(true); //make it visible

        //wait for user to press start button
        while(!start){
            try {
                Thread.sleep(5);
            }
            catch(InterruptedException e){}
        }
        //make a new frame for actual robot simulator
        startFrame.dispose();
        //same frame setup as before
        frame= new JFrame("EK 10582"); //making the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(background);
        pic= new RobotSimulator(); //making the canvas

        //a lot of buttons lol
        reset = new JButton("R");
        stop= new JButton("P");
        repeat= new JButton("F");
        reset.setFocusable(false);
        stop.setFocusable(false);
        repeat.setFocusable(false);

        robert=new robot(0,0,0); //new robot at 0, 0, facing 180 degrees from horizontal
        robert.position.start(); //start the position thread

        paint t= new paint(); //making a new paint object, which will be made into a thread
        Thread draw= new Thread(t); //making t into a thread

        //make keyboard and mouse listeners for keyboard inputs and mouse dragging
        keys=new inputs();
        rice=new mouseInputs();
        pic.addKeyListener(keys);
        pic.addMouseListener(rice);
        pic.addMouseMotionListener(rice);

        //setting up a ton of buttons with the power of ctrl + c and ctrl + v
        reset.setBounds(10, 10, length/10, height/20); //size and position of button
        reset.addActionListener(new ActionListener (){
            public void actionPerformed (ActionEvent e) { //when button pressed reset robert position
                robert.position.x=0;
                robert.position.y=0;
                robert.position.heading=Math.PI/2;
            }
        });
        reset.setFont(writting);
        frame.add(reset); //add button to frame

        stop.setBounds(length/8+10, 10, length/10, height/20);
        stop.addActionListener( new ActionListener (){
            public void actionPerformed(ActionEvent e){
                pause=!pause;
                if(robert.imu==null){
                    robert.imu= new BNO055IMU(robert.position, robert.position.heading);
                }
            }
        });
        stop.setFont(writting);
        frame.add(stop);

        repeat.setBounds(2*length/8+10, 10, length/10, height/20);
        repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end=true;
            }
        });
        repeat.setFont(writting);
        frame.add(repeat);

        //get the pictures for a bunch of stuff
        try{
            bot=ImageIO.read(new File(settings.botImage)); //robot picture file path
            field=ImageIO.read(new File(settings.fieldImage)); //thx Ally 16953-FIRE for making the field image
            pauseSim=ImageIO.read(new File("robot/src/main/java/robot/pause.png")); //THERE OLIVIA DOES THAT MAKE U HAPPY
        }
        catch(IOException e){
            System.out.println("oof");
        }

        pic.setSize(length, height); //canvas now is the size of the frame
        frame.add(pic); //make the frame have the canvas
        frame.pack();
        frame.setVisible(true); //make it visible

        draw.start(); //start the painting thread
        ElapsedTime.startMoment=System.currentTimeMillis(); //set up start time for ElapsedTime

        if(programs.size()>0) { //make sure there are programs that can be run
            while(true){
                try {
                    //reset stuff
                    pause = true;
                    robert.position.x = 0;
                    robert.position.y = 0;
                    robert.position.heading = 0;
                    for (int i = 0; i < 4; i++) {
                        robert.driveTrain[i].setPower(0);
                        robert.driveTrain[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        robert.driveTrain[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                    robert.imu=null;

                    ElapsedTime.startMoment = System.currentTimeMillis();

                    //get the class that the user wants to run
                    Class robotClass = (Class) programs.toArray()[programNum];
                    //make a new instance of it
                    LinearOpMode lol = (LinearOpMode) robotClass.newInstance();

                    //make a new thread that will run the auton
                    programRun runPro = new programRun(lol);

                    while(pause){
                        try {
                            Thread.sleep(1);
                        }
                        catch (InterruptedException e) {
                        }
                    }

                    runPro.start();

                    //wait for the thread to finish
                    while (!runPro.isInterrupted()) {
                        //wait for the user to press the f button if they do
                        if (end) {
                            runPro.interrupt();
                            lol.active = false;
                            runPro.interrupt();
                            break;
                        }
                    }
                    try {
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e) {
                    }
                    lol.active=false;

                    //wait for user to press the f button to run their program again if they ever do
                    while (!end) {
                        try {
                            Thread.sleep(5);
                        }
                        catch (InterruptedException e) {
                        }
                    }
                    //yes ik stop is depricated but this thing didn't work before and stop fixed it so if it works it works
                    runPro.interrupt();
                    runPro.stop();

                    end = false;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    
    @Override
    public void update(Graphics g){paint(g);}
    @Override
    public void paint(Graphics g){

        int robotLen=(int)Math.round(settings.robotLength*robert.position.pixelPerInch);
        int robotWid=(int)Math.round(settings.robotWidth*robert.position.pixelPerInch);

        //make a second frame that we will edit while current frame being displayed
        Image buffer= frame.createImage(frame.getWidth(), frame.getHeight());
        Graphics2D bufferG=(Graphics2D) buffer.getGraphics();

        bufferG.setFont(telemetryFont);
        bufferG.setColor(settings.lineColor);

        bufferG.drawImage(field, getWidth()/2-length/2, getHeight()/2-height/2, length, height, this);

        //draw everything in the telemetry
        List<String> telem= new ArrayList<>();
        telem.addAll(tele);

        for(int i=0; i<telem.size(); i++){
            String message=telem.get(i);
            if(message!=null){
                bufferG.drawString(message, 0, getHeight()-telem.size()*length/40+(i*length/40+length/40)-length/50);

            }
        }

        bufferG.setStroke(new BasicStroke(length/200));
        if(robert.path!= null && robert.path.size()>0){
            for(int i=0; i<robert.path.size()-1; i++){
                bufferG.drawLine((int)(-robert.path.get(i).y*robert.position.pixelPerInch+getWidth()/2), (int)(-robert.path.get(i).x*robert.position.pixelPerInch+getHeight()/2), (int)(-robert.path.get(i+1).y*robert.position.pixelPerInch+getWidth()/2), (int)(-robert.path.get(i+1).x*robert.position.pixelPerInch+getHeight()/2));
            }
            for(int i=0; i<robert.path.size(); i++){
                bufferG.fillOval((int)(-robert.path.get(i).y*robert.position.pixelPerInch+getWidth()/2-length/100), (int)(-robert.path.get(i).x*robert.position.pixelPerInch+getHeight()/2-height/100), length/50, height/50);
            }
        }

        bufferG.drawOval((int)(robert.position.getX()-robert.lookahead*robert.position.pixelPerInch+getWidth()/2), (int) (robert.position.getY()-robert.lookahead-robert.lookahead*robert.position.pixelPerInch+getHeight()/2), (int)(2*robert.lookahead*robert.position.pixelPerInch), (int)(2*robert.lookahead*robert.position.pixelPerInch));


        //rotate based on robot rotation and draw it
        bufferG.rotate(-robert.position.heading, robert.position.x +getWidth()/2, robert.position.y +getHeight()/2);
        bufferG.drawImage(bot, (int)robert.position.x-robotLen/2+getWidth()/2,(int)robert.position.y-robotWid/2+getHeight()/2, robotLen, robotWid,this);

        bufferG.rotate(robert.position.heading, robert.position.x +getWidth()/2, robert.position.y +getHeight()/2);
        if(pause){
            bufferG.drawImage(pauseSim, getWidth()-length/10-10, 10, length/10, height/10, this);
        }

        //replace current image with image we were preparing in the background
        g.drawImage(buffer, 0, 0, this);
    }

}
