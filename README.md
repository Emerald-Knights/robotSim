A very simple mecanum drive simulator for FTC teams
**Setup**
To set up, first download the zip and extract it somewhere you will remember (like documents). Then, open the extraced folder in Android Studio as an existing project. When the gradle finishes syncing, there should be 3 folders in Android view: app, robot, and Gradle Scripts. app can be ignored, as it is only to make this project work better with Android Studio. Within the robot folder, find the RobotSimulator class.

![class](/pics/RobotSimulator.png)

This is where the main method is located. To run the program, press the green arrow located next to the main method.
![runarrow](/pics/runArrow.png)
If this does not appear, a run configuration can be made manually.
![runConfig](/pics/runConfig.png)
The other class of importance is the auton class, where all of your auton/ teleop code will go. Right now, the simulator only supports LinearOpModes, and only one can be run at a time. Type any code you wish for the simulator to run within that class. The robot object robert contains all motors, imus, etc.
![auton](/pics/auton.png)

**Features**
The simulator only supports mecanum drives and a single LinearOpMode as of right now. Gamepad inputs will be read through the gamepad1 class, and keyboard inputs with keyboard1 (no, the FTC SDK does not support keyboards but I added it to help with testing for those without a gamepad). Telemetry will be displayed in the bottom left. Telemetry works a bit differently in which telemetry will display strings. Remember telemetry.update() or else the program will run out of ram. The R button at the top left will reset the position of the robot, and the P button will pause the simulation, preventing any change in robot movement or telemetry. The robot can also be moved around by clicking and dragging it, and rotation happens with a right click and hold.

