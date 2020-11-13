package robot;

//import com.studiohartman.jamepad.ControllerManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public class LinearOpMode {
    //static ArrayList<Class> programs= new ArrayList<Class>();
    //public robot robert=RobotSimulator.robert;
    boolean active=true;
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Autonomous{
        public String name() default "";
        public String group() default "";
    }


    public LinearOpMode(){

    }

    //lol this entire thing does nothing just makes it look like a real opmode
    public void runOpMode(){

    }
    public void waitForStart(){
        //lol this does nothing but makes program look like an actual auton

    }
    public boolean opModeIsActive(){
        //lol also does nothing
        return active;
    }

}
