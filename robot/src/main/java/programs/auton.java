package programs;

import robot.LinearOpMode.*;
import robot.*;

/** Use this as a model for all programs you want to write. Copy the imports down. The Autonomous
 *  annotation at the beginning does nothing, but it makes it seem like a real program lol. Also
 *  make sure any movement commands you wish to call get called on robert, as that is the only way
 *  they will register. Remember the opModeIsActives in your while loops
 */

@Autonomous(name = "auton", group ="sus")
public class auton extends LinearOpMode {
    robot robert= RobotSimulator.robert;
    @Override
    public void runOpMode(){
        while(robert.leftBack.getCurrentPosition()<1000 && opModeIsActive()){
            for(int i=0; i<4; i++){
                robert.driveTrain[i].setPower(.8);
            }
        }
        for(int i=0; i<4; i++){
            robert.driveTrain[i].setPower(0);
        }
    }
}
