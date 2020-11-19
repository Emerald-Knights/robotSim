package robot;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public abstract class LinearOpMode {

    boolean active=true;
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Autonomous{
        public String name() default "";
        public String group() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface  Teleop{
        public String name() default"";
        public String group() default"";
    }

    public abstract void runOpMode();


    public void waitForStart(){
        //lol this does nothing but makes program look like an actual auton

    }
    public boolean opModeIsActive(){
        try{
            Thread.sleep(1);
        }
        catch(InterruptedException e){}
        return active;
    }

}
