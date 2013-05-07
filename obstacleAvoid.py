
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD);
GPIO.setup(16, GPIO.OUT);
GPIO.setup(18, GPIO.OUT);
GPIO.setup(24, GPIO.OUT);
GPIO.setup(26, GPIO.OUT);
GPIO.setup(15, GPIO.IN);
GPIO.setup(13, GPIO.IN);
GPIO.setup(12, GPIO.IN);

if (GPIO.input(12) == 1):
    GPIO.cleanup();
    exit();

def rightWheelForward():
    GPIO.output(24, False);
    GPIO.output(26, True);

def rightWheelBackward():
    GPIO.output(24, True);
    GPIO.output(26, False);

def rightWheelStop():
    GPIO.output(24, False);
    GPIO.output(26, False);

def leftWheelForward():
    GPIO.output(16, True);
    GPIO.output(18, False);

def leftWheelBackward():
    GPIO.output(16, False);
    GPIO.output(18, True);

def leftWheelStop():
    GPIO.output(16, False);
    GPIO.output(18, False);

def goForward():
    leftWheelForward();
    rightWheelForward();

def goBackward():
    leftWheelBackward();
    rightWheelBackward();

def stop():
    leftWheelStop();
    rightWheelStop();

def spinRight():
    leftWheelForward();
    rightWheelBackward();

def spinLeft():
    leftWheelBackward();
    rightWheelForward();

def pivotForwardRight():
    leftWheelForward();
    rightWheelStop();

def pivotBackwardRight():
    leftWheelBackward();
    rightWheelStop();

def pivotForwardLeft():
    leftWheelStop();
    rightWheelForward();

def pivotBackwardLeft():
    leftWheelStop();
    rightWheelBackward();

def waitForNextCommand():
    time.sleep(3);

def objectInFront():
    return (GPIO.input(15) == 0);

def objectBelow():
    return (GPIO.input(13) == 0);

backingUp = False;
while (True):
    if (not objectBelow()):
	stop();
	time.sleep(1);
	while (not objectBelow()):
	    goBackward();
	stop();
	time.sleep(1);
	spinRight();
	time.sleep(1);
	stop();
	time.sleep(1);
    elif (objectInFront()):
	stop();
	time.sleep(1);
	while (objectInFront()):
	    spinRight();
	stop();
	time.sleep(1);
    else:
	goForward()
    time.sleep(0.1);


GPIO.cleanup();
