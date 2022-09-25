package programs;

import robot.LinearOpMode.*;
import robot.*;

/** Very very basic example drive
 * A sample OpMode with how to use gamepad inputs. The Teleop
 * annotation at the beginning does nothing, but it makes it seem like a real program lol. Also
 * make sure any movement commands you wish to call get called on robert, as that is the only way
 * they will register
 */
@robot.LinearOpMode.Teleop(name="teleop", group="sus")
public class GlobalTranslationalTeleop extends OpMode{
    robot robert=RobotSimulator.robert;
    @Override
    public void init() {
        robert.init(); //does nothing but can help simulate an actual program
    }
    @Override
    public void start() {

    }

    @Override
    public void loop() {
        double lx=gamepad1.left_stick_x;
        double ly=-gamepad1.left_stick_y;
        double rx=gamepad1.right_stick_x;

        double direction = Math.atan2(-ly, lx);
        double lf = Math.sin(robert.getHeading() + Math.PI*3/4 + direction);
        double rf = Math.sin(robert.getHeading() + Math.PI*5/4 + direction);
        double turnPower = rx;

        double ratio;
        double max = Math.max(Math.abs(rf), Math.abs(lf));
        double magnitude = Math.sqrt((lx * lx) + (ly * ly) + (rx * rx));
        if (max == 0) {
            ratio = 0;
        }
        else {
            ratio = magnitude / max ;
        }

        robert.leftFront.setPower(lf*ratio + turnPower);
        robert.leftBack.setPower(rf*ratio + turnPower);
        robert.rightFront.setPower(rf*ratio - turnPower);
        robert.rightBack.setPower(lf*ratio - turnPower);
    }
}
