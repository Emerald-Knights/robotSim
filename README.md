A very simple mecanum drive simulator for FTC teams by Team 10582, Emerald Knights

Use this to test out drive paths and what not.

IMPORTANT: DO NOT RUN THE APP, PRETEND THAT THE ENTIRE FOLDER DOESN'T EXIST, THAT IS JUST THERE SO IT WORKS WELL WITH ANDROID STUDIO AND WE CAN USE ANDROID VIEW (project view looks ugly and you know it).

This is probably the biggest pile of spaghetti code you've ever seen but it works at least partially and that's good enough for me

**Setup**
---
To set up, first download the zip and extract it somewhere you will remember (like documents). Then, open the extraced folder in Android Studio as an existing project. When the gradle finishes syncing, there should be 3 folders in Android view: app, robot, and Gradle Scripts. app can be ignored, as it is only to make this project work better with Android Studio. 


![folder](/pics/folder.png)

Within the robot folder, find the RobotSimulator class.

![class](/pics/RobotSimulator.png)

This is where the main method is located. To run the program, press the green arrow located next to the main method.

![runarrow](/pics/runArrow.png)

If this does not appear, a run configuration can be made manually.

![runConfig](/pics/runConfig.png)

The other class of importance is the auton class, where all of your auton/ teleop code will go. Right now, the simulator only supports LinearOpModes, and only one can be run at a time. Type any code you wish for the simulator to run within that class. The robot object robert contains all motors, imus, etc.

![auton](/pics/auton.png)

DO NOT RUN THE APP CAUSE IT LITERALLY DOES NOTHING

**Configuring**
---
Inside the config folder, there should be a java class called settings. Use this to input a few details about your robot (or you can just use the settings from our robot). You can also change the appearance of the simulator if you just despise EK green for whatever reason

![settings](/pics/settings.png)

**How to use**
---
After pressing the run button, a green popup should appear. You can use the dropdown box to select which program you want to run. All of your programs need to extend LinearOpMode or OpMode in order to show up. When you choose one you wish to run, press the S button (s stands for start but there wasn't enough room to put the entire word).

![start](/pics/startScreen.png)

After pressing start, the program should run and look like this

![running](/pics/programRunning.png)

**Features**
---
The simulator only supports mecanum drives (because mecanum is obviously superior and also I'm lazy). Gamepad inputs will be read through the gamepad1 class, and keyboard inputs with keyboard1 (no, the FTC SDK does not support keyboards but I added it to help with testing for people without a gamepad). Telemetry will be displayed in the bottom left. Telemetry works a bit differently in which telemetry will display strings. Remember telemetry.update() or else the program will run out of ram and die. 

If your robot runs off screen but you still want the program to continue running (like if your doing a teleop and you drive the robot off screeen), you can press the R button to reset its position and angle. (R stands for reset)

If your program failed and you want to run it again to try seeing what went wrong, you can press the F button to reset the robot AND reset the auton or teleop so it is ready to be run again. (F stands for F in the chat because your program failed)

Finally, to pause the simulation at a given moment to check out telemetry or adjust the robot's position or something, press the P button and a grey pause icon will show up in the top right, showing that you are paused. (P stands for pause)

![button](/pics/buttons.png)

The robot can also be dragged to different parts of the screen, so you can move it to wherever you wish to start the program. Left clicking and dragging changes its position, and right clicking will change its angle.

**Odo**
---
To simulate odo, you can call the getX(), getY(), and getHeading() method on robot (do not call it on robert.position) to get your position on XY coordinate field (X and Y will return the way Vuforia does it, in inches). 

You can also set robert.lookahead and robert.path to equal a lookahead distance and an arrayList of path points and the simulator will draw it on the field

![odo](/pics/odo.png)

![odoDrive](/pics/odoDrive.png)

**Methods**
---
All operations you want the robot to do must be called on RobotSimulator.robert. In the sample programs you can see how to do that. Since the robot is not actually a real robot, you don't need to have an actual init phase. I've only included methods that I actually use, so there is stuff missing that would be in the actual FTC SDK. For example, on an Orientation object you can call first, second, and third angle, but I've only included first because that is the one my team uses. 

Motors have basic setPower(), getCurrentPosition(), and setMode() functionality and the simulator will move according to what power is set to its drive motors (there is no friction or whatever because that would take a lot of time and effort to make, so whatever power is set is proportional to how fast the robot moves).

The imu can only grab the robot's angle using the first angle (does anyone seriously use the imu's getMagneticField function anyways?). The inital angle is taken when the pause button is pressed for the first time, thus unpausing the program, and the angle is given in radians (because radians were better for coding how the simulator works and I was too lazy to convert them to degrees).

gamepad1 is there, no gamepad2 because lazyness and I doubt you'd be using this with 2 gamepads. Call it like you usually would, like gamepad1.x;

I added a keyboard1 too because some people probably don't have a controller to use all the time. At the moment it only has WASDQE because I was too lazy to put in the other keys.

Telemetry is here, you just need to call telemetry.addData() and input a String. Yes, the parameters are different from what telemetry normally accepts, but it's annoying doing it the way telemetry normally does. Make sure to call telemetry.update() or else bad stuff happens. The telemetry appears at the bottom left corner. Also, sadly, I was not able to add in telemetry.speak().

You can add waitForStart() before a program and have opModeIsReady() in your while loops for practice, it is not required in the simulator but I would recommend you have those there to develop good habbits.


**How it works**
---
The simulator first needs to find the movement of the robot relative to itself, so it reads the power that every motor is being set to. Using this, it forms a vector where the left back and right front motors form the y part and the left front and right back motors form the x part. The magnitude can be determined with pythagoreans, while the angle is the arctangent of y/x.

![triangle](/pics/triangle.png)

However, this vector needs to be rotated 45 degrees to the left to accurately show the direction that the robot is moving. After a simple rotation of 45 degrees, the direction that the robot is moving relative to itsef can be calculated. However, in order to find the robot's movements relative to the field instead of just itself, the robot's global heading is added to the angle that the robot is moving relative to itself. The robot's movements relative to the global coordinate system found, so now the robot's x and y is changed accordingly with the sine and cosine of its movement. Finally, heading is adjusted based off the difference in the robot's left and right side motors.

**Bugs and Other Issues**
---
Any bugs or issues can be reported via the Issues section of this repository.
