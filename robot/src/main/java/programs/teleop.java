package programs;

import robot.LinearOpMode.*;
import robot.*;

/**
 * A sample OpMode with how to use gamepad inputs. The Teleop
 * annotation at the beginning does nothing, but it makes it seem like a real program lol. Also
 * make sure any movement commands you wish to call get called on robert, as that is the only way
 * they will register
 */
@Teleop(name="teleop", group="sus")
public class teleop extends OpMode{
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

        robert.leftFront.setPower(lx + ly + rx);
        robert.leftBack.setPower(ly - lx + rx);
        robert.rightFront.setPower(ly - lx - rx);
        robert.rightBack.setPower(lx + ly - rx);
        telemetry.addData("imu: " + robert.imu.getAngularOrientation().firstAngle);
        telemetry.update();
    }
}
