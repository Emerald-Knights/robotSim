/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.robot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
public class telemetry {
    static List<String> info=new ArrayList<>();

    //add stuff to list
    public static void addData(String add){
        if(!RobotSimulator.pause){
            info.add(add);

        }

    }

    //robot sim has its own list that is drawn, so we clear it and replace it with current telemetry list
    public static void update(){
        if(!RobotSimulator.pause){
            RobotSimulator.tele.clear();
            for(String component: info){
                if(info!=null){
                    RobotSimulator.tele.add(component);

                }
            }
            info.clear();
        }

        
    }
}
