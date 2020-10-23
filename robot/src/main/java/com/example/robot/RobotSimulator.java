/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.robot;

//import com.studiohartman.jamepad.ControllerManager;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static com.example.robot.utilities.*;
import static com.example.robot.telemetry.*;

/**
 *
 * @author thomas
 */
public class RobotSimulator extends Canvas {

    static robot robert; //globally used robot
    static JFrame frame; //jframe that animation will take place in
    static Canvas pic; //canvas to draw on
    static Image bot; //global image of robot
    static Image field;

    //48 pixesl per inch, field is 12x12ft
    static final int length=576; //size of frame 576
    static final int height =576;
    
    static List<String> tele=new ArrayList<>(); //list that includes all the string that telemetry wants to display
    static JButton reset;
    public static void main(String[] args) {
        pic= new RobotSimulator(); //making the canvas
        frame= new JFrame("pog"); //making the frame
        reset = new JButton("R"); //new button made to reset robot position
        paint t= new paint(); //making a new paint object, which will be made into a thread
        Thread draw= new Thread(t); //making t into a thread

        reset.setBounds(length-60, 20, 50, 30); //size and position of button
        reset.addActionListener(new ActionListener (){
            public void actionPerformed (ActionEvent e) { //when button pressed reset robert position
                robert.position.x=0;
                robert.position.y=0;
                robert.position.heading=Math.PI/2;
            }
        });
        frame.add(reset); //add button to frame


        try{
            bot=ImageIO.read(new File("robot\\src\\main\\java\\com\\example\\robot\\robotH.png")); //robot picture file path
            field=ImageIO.read(new File("robot\\src\\main\\java\\com\\example\\robot\\field.png")); //field picture file
        }
        catch(IOException e){}
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        robert=new robot(0,0,Math.PI/2); //new robot at 0, 0, facing 180 degrees from horizontal
        robert.position.start(); //start the position thread
        pic.setSize(length, height); //canvas now is the size of the frame

        frame.setBackground(new Color(142, 210, 153)); //make the background color olivia used on the logbook epic idea so its not "ugly"
        frame.add(pic); //make the frame have the canvas


        frame.pack();


        frame.setVisible(true); //make it visible
        
        draw.start(); //start the painting thread

        //run whatever is in the auton runOpMode
        auton lol= new auton();
        lol.runOpMode();
    }
    
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

        reset.setBounds(frame.getWidth()-80, 20, 50, 30);


        //bufferG.drawImage(field, frame.getWidth()/2-length/2, frame.getHeight()/2-width/2, length, width, this);
        bufferG.drawImage(field, getWidth()/2-length/2, getHeight()/2-height/2, length, height, this);

        //draw everything in the telemetry

        List<String> telem= new ArrayList<>();
        telem.addAll(tele);

        for(int i=0; i<telem.size(); i++){
            String message=telem.get(i);
            if(message!=null){
                bufferG.drawString(message, 0, i*10+10);
            }
        }

        //rotate based on robot rotation and draw it
        bufferG.rotate(-robert.position.heading, robert.position.x +getWidth()/2, robert.position.y +getHeight()/2);
        bufferG.drawImage(bot, (int)robert.position.x-robotLen/2+getWidth()/2,(int)robert.position.y-robotWid/2+getHeight()/2, robotLen, robotWid,this);

        //replace current image with image we were preparing in the background
        g.drawImage(buffer, 0, 0, this);
    }

}
