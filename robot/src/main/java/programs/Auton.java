package programs;

import java.util.ArrayList;

import robot.LinearOpMode.*;
import robot.*;

/**Very very basic example program
 * Use this as a model for all programs you want to write. Copy the imports down. The Autonomous
 *  annotation at the beginning does nothing, but it makes it seem like a real program lol. Also
 *  make sure any movement commands you wish to call get called on robert, as that is the only way
 *  they will register. Remember the opModeIsActives in your while loops. Also telemetry is demonstrated at the bottom
 */

@Autonomous(name = "auton", group ="sus")
public class Auton extends LinearOpMode {
    robot robert= RobotSimulator.robert;
    @Override
    public void runOpMode(){
        robert.init(); //does nothing but can help simulate an actual program
        waitForStart(); //also does nothing
        while(robert.leftBack.getCurrentPosition()<1000 && opModeIsActive()){
            for(int i=0; i<4; i++){
                robert.driveTrain[i].setPower(.8);
            }
            telemetry.addData("x position: "+ robert.getX());
            telemetry.addData("y position: "+robert.getY());
            telemetry.addData("heading: "+robert.getHeading());
            telemetry.update();
        }
        for(int i=0; i<4; i++){
            robert.driveTrain[i].setPower(0);
        }
    }
}
