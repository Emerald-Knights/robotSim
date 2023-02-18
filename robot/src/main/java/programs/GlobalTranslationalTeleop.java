package programs;

import robot.LinearOpMode.*;
import robot.*;

@LinearOpMode.Teleop(name="teleop", group="sus")
public class GlobalTranslationalTeleop extends OpMode{
    robot wucru = RobotSimulator.robert;

    @Override
    public void init() {
        wucru.init();
    }

    @Override
    public void start() {
        //smile emoji
    }

    @Override
    public void loop() {
        double lx = (keyboard1.d ? 1 : 0) + (keyboard1.a ? -1 : 0) ;
        double ly = (keyboard1.w ? 1 : 0) + (keyboard1.s ? -1 : 0) ;
        double rx = (keyboard1.e ? 1 : 0) + (keyboard1.q ? -1 : 0) ;
        double currentAngle = wucru.getHeading();
        System.out.println("currentangle" + wucru.getHeading());

        double maxInput = Math.max(Math.abs(lx), Math.abs(ly));
        double direction = Math.atan2(-ly, lx); //set direction
        double lf = Math.sin(currentAngle + Math.PI*3/4 + direction) * maxInput;
        double rf = Math.sin(currentAngle + Math.PI*5/4 + direction) * maxInput;
        double turnPower = -rx; //turn power can be changed to a magnitude and direction
        double maxTrans = Math.max(Math.abs(rf), Math.abs(lf));
        double denominator = Math.abs(turnPower) + maxTrans;
        double transRatio = maxTrans / (denominator == 0 ? 1 : denominator);
        double rotRatio = Math.abs(turnPower) / (denominator == 0 ? 1 : denominator);

        wucru.leftFront.setPower(0.8 * ((transRatio * lf) + (turnPower * rotRatio)));
        wucru.leftBack.setPower(0.8 * ((transRatio * rf) + (turnPower * rotRatio)));
        wucru.rightFront.setPower(0.8 * ((transRatio * rf) - (turnPower * rotRatio)));
        wucru.rightBack.setPower(0.8 * ((transRatio * lf) - (turnPower * rotRatio)));

        System.out.println("pos (" + wucru.getX() + ", " + wucru.getY() + ") angle: " + wucru.getHeading());
    }
}
