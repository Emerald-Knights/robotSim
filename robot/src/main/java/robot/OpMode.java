package robot;

public abstract class OpMode extends LinearOpMode{

    //yes, i coded opModes as just modified linearOpModes lol
    @Override
    public void runOpMode() {
        init();
        waitForStart();
        start();
        while(opModeIsActive()){
            loop();
            try{
                Thread.sleep(1);

            }
            catch(InterruptedException e){}
        }
    }
    public abstract void init();
    public abstract void start();
    public abstract void loop();
}
