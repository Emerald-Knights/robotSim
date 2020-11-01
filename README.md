A very simple mecanum drive simulator for FTC teams by Team 10582, Emerald Knights

**Setup**
---
To set up, first download the zip and extract it somewhere you will remember (like documents). Then, open the extraced folder in Android Studio as an existing project. When the gradle finishes syncing, there should be 3 folders in Android view: app, robot, and Gradle Scripts. app can be ignored, as it is only to make this project work better with Android Studio. Within the robot folder, find the RobotSimulator class.

![class](/pics/RobotSimulator.png)

This is where the main method is located. To run the program, press the green arrow located next to the main method.

![runarrow](/pics/runArrow.png)

If this does not appear, a run configuration can be made manually.

![runConfig](/pics/runConfig.png)

The other class of importance is the auton class, where all of your auton/ teleop code will go. Right now, the simulator only supports LinearOpModes, and only one can be run at a time. Type any code you wish for the simulator to run within that class. The robot object robert contains all motors, imus, etc.

![auton](/pics/auton.png)

**Features**
---
The simulator only supports mecanum drives and a single LinearOpMode as of right now. Gamepad inputs will be read through the gamepad1 class, and keyboard inputs with keyboard1 (no, the FTC SDK does not support keyboards but I added it to help with testing for those without a gamepad). Telemetry will be displayed in the bottom left. Telemetry works a bit differently in which telemetry will display strings. Remember telemetry.update() or else the program will run out of ram. The R button at the top left will reset the position of the robot, and the P button will pause the simulation, preventing any change in robot movement or telemetry. The robot can also be moved around by clicking and dragging it, and rotation happens with a right click and hold.


**How it works**
---
The simulator first needs to find the movement of the robot relative to itself, so it reads the power that every motor is being set to. Using this, it forms a vector where the left back and right front motors form the y part and the left front and right back motors form the x part. The magnitude can be determined with pythagoreans, while the angle is the arctangent of y/x.

![triangle](/pics/triangle.png)

However, this vector needs to be rotated 45 degrees to the left to accurately show the direction that the robot is moving. After a simple rotation of 45 degrees, the direction that the robot is moving relative to itsef can be calculated. However, in order to find the robot's movements relative to the field instead of just itself, the robot's global heading is added to the angle that the robot is moving relative to itself. The robot's movements relative to the global coordinate system found, so now the robot's x and y is changed accordingly with the sine and cosine of its movement. Finally, heading is adjusted based off the difference in the robot's left and right side motors.

