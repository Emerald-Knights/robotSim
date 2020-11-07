/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

/**
 *
 * @author thomas
 */
public class DcMotor {
    private double power;
    int position;
    double wantPos;

    private RunMode mode=RunMode.RUN_USING_ENCODER;

    //just saves power and has a lot of random other methods to make it look like motor
    public enum RunMode{
        RUN_WITHOUT_ENCODER, RUN_USING_ENCODER, RUN_TO_POSITION, STOP_AND_RESET_ENCODER
    }
    public DcMotor(){
        power=0;
    }
    public void setMode(RunMode mode){
        this.mode=mode;
        if(mode==RunMode.STOP_AND_RESET_ENCODER){
            setPower(0);
            position = 0;
        }
    }
    public RunMode getMode(){
        return mode;
    }
    public void setPower(double pow){
        if(pow>1.0){
            pow=1;
        }
        power=pow;
        
    }
    public void setTargetPosition(int position){
        this.wantPos=position;
    }
    public double getTargetPosition(){
        return wantPos;
    }
    public double getPower(){
        return power;
    }
    public int getCurrentPosition(){
        return position;
    }
    public boolean isBusy(){
        return Math.abs(power)>0;
    }
    
}
