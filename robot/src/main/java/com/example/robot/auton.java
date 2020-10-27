package com.example.robot;

import java.awt.event.KeyEvent;

import static com.example.robot.utilities.getPowers;


public class auton extends LinearOpMode {
    robot robert= RobotSimulator.robert;
    @Override
    public void runOpMode(){
        while(opModeIsActive()){
            if(RobotSimulator.keys.keys[KeyEvent.VK_W]){
                for(int i=0; i<4; i++){
                    robert.driveTrain[i].setPower(.8);
                }
            }
            else{
                for (int i=0; i<4; i++){
                    robert.driveTrain[i].setPower(0);
                }
            }
        }

    }

}
