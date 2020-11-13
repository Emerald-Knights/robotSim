package programs;

import robot.LinearOpMode;
import robot.LinearOpMode.*;
import robot.RobotSimulator;
import robot.robot;

/** Use this as a model for all programs you want to write. Copy the imports down. The Autonomous
 *  annotation at the beginning does nothing, but it makes it seem like a real program lol. Also
 *  make sure any movement commands you wish to call get called on robert, as that is the only way
 *  they will register
 */

@Autonomous(name = "auton", group ="f")
public class auton extends LinearOpMode {
    robot robert= RobotSimulator.robert;
    @Override
    public void runOpMode(){

    }
}
