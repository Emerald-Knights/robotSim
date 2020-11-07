/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import java.awt.event.KeyEvent;


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

            //keyboard
            keyboard1.w=RobotSimulator.keys.keys[KeyEvent.VK_W];
            keyboard1.a=RobotSimulator.keys.keys[KeyEvent.VK_A];
            keyboard1.s=RobotSimulator.keys.keys[KeyEvent.VK_S];
            keyboard1.d=RobotSimulator.keys.keys[KeyEvent.VK_D];
            keyboard1.q=RobotSimulator.keys.keys[KeyEvent.VK_Q];
            keyboard1.e=RobotSimulator.keys.keys[KeyEvent.VK_E];


            //RobotSimulator.stop.setBounds(RobotSimulator.pic.getWidth()-140, 20, 50, 30);
            if(RobotSimulator.pause){
                ElapsedTime.startMoment+=10;
            }

            //wait 50 milliseconds before repeating
            try{
                Thread.sleep(10);
            }
            catch(Exception e){}

            
        }
    }
}
