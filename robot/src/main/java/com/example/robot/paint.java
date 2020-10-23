/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.robot;

//import com.badlogic.gdx.controllers.ControllerManager;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

//import org.libsdl.SDL_Error;

//import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

//import com.studiohartman.jamepad.ControllerState;


/**
 *
 * @author thomas
 */
public class paint implements Runnable{
    boolean keepRun=true;
    //SDL2ControllerManager controllers= new SDL2ControllerManager();
    ControllerManager controller= new ControllerManager();

    @Override
    public void run() {

        controller.initSDLGamepad();
        //controllers.addListener(new listener());
        ControllerState state;
        while(keepRun){
            //calls for image to be redrawn
            RobotSimulator.pic.repaint();

            //get current state of controller
            state= controller.getState(0);

            //set all the things from the controller to its equivalent in gamepad1
            gamepad1.left_stick_x=state.leftStickX;
            gamepad1.left_stick_y=-state.leftStickY;
            gamepad1.right_stick_x=state.rightStickX;
            gamepad1.right_stick_y=-state.rightStickY;

            gamepad1.dpad_up=state.dpadUp;
            gamepad1.dpad_down=state.dpadDown;
            gamepad1.dpad_left=state.dpadLeft;
            gamepad1.dpad_right=state.dpadRight;

            gamepad1.start=state.start;

            gamepad1.x=state.x;
            gamepad1.y=state.y;
            gamepad1.a=state.a;
            gamepad1.b=state.b;

            gamepad1.left_bumper=state.lb;
            gamepad1.right_bumper=state.rb;
            gamepad1.left_trigger=state.leftTrigger;
            gamepad1.right_trigger=state.rightTrigger;

            //wait 50 milliseconds before repeating
            try{
                Thread.sleep(50);
            }
            catch(Exception e){}

            
        }
    }
}
