package com.example.robot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class inputs implements KeyListener {
    //array of booleans with all the keys
    boolean[] keys= new boolean[256];

    @Override
    public void keyPressed(KeyEvent e){
        //set the key that is pressed to true
        keys[e.getKeyCode()]=true;
    }
    @Override
    public void keyReleased(KeyEvent e){
        //when it is released it is now false (wow amazing ikr)
        keys[e.getKeyCode()]=false;
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
}
