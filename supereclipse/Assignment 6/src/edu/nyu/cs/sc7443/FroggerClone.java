package edu.nyu.cs.sc7443;

import java.util.ArrayList;
import processing.core.*;
import java.lang.Math;

/**
 * The main program-running class for a recreation of the game Frogger.
 * @author Sivan Cooperman
 * @version 1.5
 */
public class FroggerClone extends PApplet {
	
	/**
	 * The frog object that moves around the screen.
	 */
	private Frog frog = new Frog(this);
	/**
	 * An ArrayList to hold Cars.
	 */
	private ArrayList<Car> cars = new ArrayList<Car>();
	/**
	 * Indicates whether the frog has crashed into a car or not.
	 */
	private boolean crashed;
	/**
	 * 1/10 chance per game to have a supercar with special properties spawn
	 */
	private boolean superCar = ((int) (Math.random() * 10) == 1);
	/**
	 * Checks whether the user has won or not.
	 */
	private boolean win = false;
	
	public static void main(String[] args) {
		// run Processing for this class
		PApplet.main("edu.nyu.cs.sc7443.FroggerClone");
		
	}
	
	/**
	 * Sets the size of the screen to 1000px by 700px.
	 */
	public void settings() {
		size(1000, 700);
	}
	
	/**
	 * Loads the background image, adds 15 cars to the car ArrayList and semi-randomly assigns them lanes.
	 */
	public void setup() {
		// load the background image
		PImage img = loadImage("FroggerBackground.png");
		background(img);
		
		// make a new integer array where each index represents the lane of a car
		int[] carLanes = new int[15];
		// do the following five times
		for (int i = 0; i < 5; i++) {
			// the first five cars go one in each lane, so that there's at least one car per lane
			carLanes[i] = i;
			// the next ten are random
			carLanes[i+5] = (int) (Math.random()*5);
			carLanes[i+10] = (int) (Math.random()*5);
		}
		// for each car
		for (int i = 0; i < 15; i++) {
			// if this round has been flagged to have a supercar (1/10 chance)
			if (superCar) {
				// don't make more after this one
				this.superCar = false;
				// make a supercar
				Car car = new Car(this, 139, 25, new int[] {255, 0, 247});
				// add it to our arraylist
				cars.add(car);
			} else {
				// otherwise just make a normal car with its assigned lane
				Car car = new Car(this, carLanes[i]);
				cars.add(car);
			}
		}
	}

	/**
	 * Runs 60 times a second to redraw the background, check for win/loss conditions, and move the cars.
	 */
	public void draw() {
		if (this.win) {
			// if we win, show win screen
			PImage youWin = loadImage("youWin.png");
			background(youWin);
		} else if (this.crashed) {
			// if we crash, show game over screen
			PImage gameOver = loadImage("gameOver.png");
			background(gameOver);
		// if we haven't lost or won
		} else {
			// load the street background
			PImage bg = loadImage("FroggerBackground.png");
			background(bg);
			// draw the frog
			frog.show();
			// for each car
			for (Car car : cars) {
				// draw and then move the cars
				car.show();
				car.move();
				// check for a crash if we haven't crashed
				if (!this.crashed) this.crashed = frog.detectCollision(car.getBounds());
				// check for a win if we haven't won
			} if (frog.detectCollision(new int[] {0, 1000, 0, 100})) this.win = true;
		}
	}
	
	/**
	 * Runs when a key is pressed, moving the frog in the direction indicated by the arrow keys.
	 */
	public void keyPressed() {
		if (!this.crashed && !this.win) {
			frog.move();
		}
	}
}
