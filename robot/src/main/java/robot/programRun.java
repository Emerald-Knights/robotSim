package robot;

public class programRun extends Thread{
    private LinearOpMode running;

    public programRun(LinearOpMode running){
        this.running=running;
    }

    @Override
    public void run() {
        running.runOpMode();
        try{
            Thread.sleep(5);
        }
        catch(InterruptedException e){}
        //System.out.println("end1");
        this.interrupt();
        //System.out.println("end2");
    }
}
