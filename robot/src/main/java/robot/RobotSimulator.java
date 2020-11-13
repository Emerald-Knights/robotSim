/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

//import com.studiohartman.jamepad.ControllerManager;

import org.reflections.Reflections;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.sound.sampled.Line;
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
    static final int length=576; //size of frame 576
    static final int height =576;
    
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



    public static void main(String[] args) {
        startFrame= new JFrame("EK 10582"); //making the frame
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setBackground(new Color(142, 210, 153)); //make the background color olivia used on the logbook epic idea so its not "ugly"
        startMenu= new Canvas();
        startMenu.setBackground(new Color(142, 210, 153));

        Reflections reflections = new Reflections("programs");
        Set<Class<? extends LinearOpMode>> programs =reflections.getSubTypesOf(LinearOpMode.class);
        String[] programNames= new String[programs.size()];

        for(int i=0; i<programs.size(); i++){
            programNames[i]= programs.toArray()[i].toString();
        }

        sus = new JComboBox(programNames);

        dropDown= new programBox();
        sus.addActionListener(dropDown);
        sus.setBounds(100, 50, 200, 50);
        sus.setFocusable(false);
        startFrame.add(sus);

        startButton= new JButton("S");
        startButton.setFocusable(false);

        startButton.setBounds(175, 250, 50, 30);
        startButton.addActionListener( new ActionListener (){
            public void actionPerformed(ActionEvent e){
                start=true;
            }
        });
        startFrame.add(startButton);

        startMenu.setSize(400, 300);
        startFrame.add(startMenu);

        startFrame.pack();
        startFrame.setResizable(false);


        startFrame.setVisible(true); //make it visible

        while(!start){
            try {
                Thread.sleep(5);
            }
            catch(InterruptedException e){}
        }
        startFrame.dispose();

        frame= new JFrame("EK 10582"); //making the frame

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(142, 210, 153)); //make the background color olivia used on the logbook epic idea so its not "ugly"


        pic= new RobotSimulator(); //making the canvas

        reset = new JButton("R"); //new button made to reset robot position
        stop= new JButton("P");
        repeat= new JButton("F");
        reset.setFocusable(false);
        stop.setFocusable(false);
        repeat.setFocusable(false);

        robert=new robot(0,0,Math.PI/2); //new robot at 0, 0, facing 180 degrees from horizontal
        robert.position.start(); //start the position thread

        paint t= new paint(); //making a new paint object, which will be made into a thread
        Thread draw= new Thread(t); //making t into a thread

        keys=new inputs();
        rice=new mouseInputs();
        pic.addKeyListener(keys);
        pic.addMouseListener(rice);
        pic.addMouseMotionListener(rice);

        stop.setBounds(70, 10, 50, 30);
        stop.addActionListener( new ActionListener (){
            public void actionPerformed(ActionEvent e){
                pause=!pause;
            }
        });
        frame.add(stop);

        reset.setBounds(10, 10, 50, 30); //size and position of button
        reset.addActionListener(new ActionListener (){
            public void actionPerformed (ActionEvent e) { //when button pressed reset robert position
                robert.position.x=0;
                robert.position.y=0;
                robert.position.heading=Math.PI/2;
            }
        });
        frame.add(reset); //add button to frame

        repeat.setBounds(130, 10, 50, 30);
        repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end=true;
            }
        });
        frame.add(repeat);

        try{
            bot=ImageIO.read(new File("robot/src/main/java/robot/robotH.png")); //robot picture file path
            field=ImageIO.read(new File("robot/src/main/java/robot/field.png")); //thx ally for making the field image
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
        ElapsedTime.startMoment=System.currentTimeMillis();

        if(programs.size()>0) {
            while(true){

                try {
                    System.out.println("num thread: " +Thread.activeCount());
                    pause = true;
                    robert.position.x = 0;
                    robert.position.y = 0;
                    robert.position.heading = Math.PI / 2;
                    for (int i = 0; i < 4; i++) {
                        robert.driveTrain[i].setPower(0);
                        robert.driveTrain[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        robert.driveTrain[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }

                    ElapsedTime.startMoment = System.currentTimeMillis();


                    Class robotClass = (Class) programs.toArray()[programNum];
                    LinearOpMode lol = (LinearOpMode) robotClass.newInstance();

                    programRun runPro = new programRun(lol);
                    runPro.start();

                    while (!runPro.isInterrupted()) {
                        if (end) {
                            runPro.interrupt();
                            lol.active = false;
                            runPro.interrupt();
                            break;
                        }
                    }
                    try {
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e) {
                    }
                    lol.active=false;

                    //System.out.println(lol.active);
                    while (!end) {
                        try {
                            Thread.sleep(5);
                        }
                        catch (InterruptedException e) {
                        }
                    }

                    runPro.interrupt();
                    runPro.stop();
                    //runPro.setPriority(10);
                    //runPro.join();

                    end = false;

                    //runPro.finalize();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }


        //run whatever is in the auton runOpMode
        //auton lol= new auton();
        //lol.runOpMode();
    }
    /*
    public static void runProgram(LinearOpMode lol){
        lol.active=true;

        pause=true;
        robert.position.x=0;
        robert.position.y=0;
        robert.position.heading=Math.PI/2;
        for(int i=0; i<4; i++){
            robert.driveTrain[i].setPower(0);
            robert.driveTrain[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robert.driveTrain[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        ElapsedTime.startMoment=System.currentTimeMillis();


        runProgram(lol);
    }

     */
    
    @Override
    public void update(Graphics g){paint(g);}
    @Override
    public void paint(Graphics g){
        //robot will be 1/36 the size of the frame
        int robotLen=length/6;
        int robotWid=height/6;

        //make a second frame that we will edit while current frame being displayed
        Image buffer= frame.createImage(frame.getWidth(), frame.getHeight());
        Graphics2D bufferG=(Graphics2D) buffer.getGraphics();

        //frame.setBackground(new Color(142, 210, 153));

        //reset.setBounds(pic.getWidth()-80, 20, 50, 30);


        //bufferG.drawImage(field, frame.getWidth()/2-length/2, frame.getHeight()/2-width/2, length, width, this);
        bufferG.drawImage(field, getWidth()/2-length/2, getHeight()/2-height/2, length, height, this);

        //draw everything in the telemetry
        List<String> telem= new ArrayList<>();
        telem.addAll(tele);

        for(int i=0; i<telem.size(); i++){
            String message=telem.get(i);
            if(message!=null){
                bufferG.drawString(message, 0, getHeight()-telem.size()*10+(i*10+10));
            }
        }

        //rotate based on robot rotation and draw it
        bufferG.rotate(-robert.position.heading, robert.position.x +getWidth()/2, robert.position.y +getHeight()/2);
        bufferG.drawImage(bot, (int)robert.position.x-robotLen/2+getWidth()/2,(int)robert.position.y-robotWid/2+getHeight()/2, robotLen, robotWid,this);

        bufferG.rotate(robert.position.heading, robert.position.x +getWidth()/2, robert.position.y +getHeight()/2);
        if(pause){
            bufferG.drawImage(pauseSim, getWidth()-75, 10, 50, 50, this);
        }

        //replace current image with image we were preparing in the background
        g.drawImage(buffer, 0, 0, this);
    }

}
