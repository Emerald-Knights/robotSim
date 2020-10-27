package com.example.robot;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import javax.swing.JComponent;
import java.awt.Point;

/**
 *
 * @author thomas
 */
public class mouseInputs extends MouseAdapter{
    boolean holding=false;
    boolean spinning=false;
    double lastX;
    double lastY;
    //mouse
    @Override
    public void mouseClicked(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){

    }
    @Override
    public void mouseExited(MouseEvent e){

    }
    @Override
    public void mousePressed(MouseEvent e){
        //when button pressed see if it is within box
        if(e.getButton()==1 ){
            double x=e.getX();
            double y= e.getY();

            x=x-RobotSimulator.pic.getWidth()/2;
            y=y-RobotSimulator.pic.getHeight()/2;

            double x1 = x - RobotSimulator.robert.position.getX();
            double y1 = y - RobotSimulator.robert.position.getY();

            double angle=RobotSimulator.robert.position.getHeading();
            double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
            double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

            x = x2 + RobotSimulator.robert.position.getX();
            y = y2 + RobotSimulator.robert.position.getY();


            if(x>=RobotSimulator.robert.position.getX()-RobotSimulator.length/6 && x<=RobotSimulator.robert.position.getX()+RobotSimulator.length/6 &&
                y>=RobotSimulator.robert.position.getY()-RobotSimulator.height/6 && y<=RobotSimulator.robert.position.getY()+RobotSimulator.height/6){
                holding=true;

            }

            lastX=e.getX();
            lastY=e.getY();

        }
        //if right click is pressed
        else if(e.getButton()==3) {
            double x=e.getX();
            double y= e.getY();

            x=x-RobotSimulator.pic.getWidth()/2;
            y=y-RobotSimulator.pic.getHeight()/2;

            double x1 = x - RobotSimulator.robert.position.getX();
            double y1 = y - RobotSimulator.robert.position.getY();

            double angle=RobotSimulator.robert.position.getHeading();
            double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
            double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

            x = x2 + RobotSimulator.robert.position.getX();
            y = y2 + RobotSimulator.robert.position.getY();


            if(x>=RobotSimulator.robert.position.getX()-RobotSimulator.length/6 && x<=RobotSimulator.robert.position.getX()+RobotSimulator.length/6 &&
                    y>=RobotSimulator.robert.position.getY()-RobotSimulator.height/6 && y<=RobotSimulator.robert.position.getY()+RobotSimulator.height/6){
                spinning=true;

            }

            lastX=e.getX();
            lastY=e.getY();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        holding=false;
        spinning=false;

    }
    @Override
    public void mouseMoved(MouseEvent e){

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(holding){
            double x=e.getX();
            double y=e.getY();
            double deltaX=x-lastX;
            double deltaY=y-lastY;

            RobotSimulator.robert.position.x+=deltaX;
            RobotSimulator.robert.position.y+=deltaY;

            lastX=x;
            lastY=y;
        }
        else if(spinning){
            double x=e.getX();
            double deltaX=x-lastX;

            RobotSimulator.robert.position.heading+=deltaX*0.015;

            lastX=x;
        }
    }
}
