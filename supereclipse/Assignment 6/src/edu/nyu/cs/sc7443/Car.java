package edu.nyu.cs.sc7443;

import processing.core.*;

/**
 * A class that replicates the behavior of the cars from Frogger.
 * @author Sivan Cooperman
 * @version 1.5
 */
public class Car {
	/**
	 * Constructor that accepts PApplet parent.
	 * @param parent Parent object that is a child of the PApplet class.
	 */
	public Car(PApplet parent) {
		this.parent = parent;
	}
	
	/**
	 * Constructor that accepts PApplet parent and designated lane position for the car.
	 * @param parent Parent object that is a child of PApplet class.
	 * @param lane 0-4, determines which lane the car drives across on screen.
	 */
	public Car(PApplet parent, int lane) {
		this.parent = parent;
		this.lane = lane;
	}
	
	/**
	 * Constructor that accepts PApplet parent, designated y value for the car, 
	 * and speed of the car.
	 * @param parent Parent object that is a child of PApplet class.
	 * @param y Directly assigns the car to a given y value.
	 * @param speed Speed of the car in pixels per second.
	 */
	public Car(PApplet parent, int y, int speed, int[] color) {
		this.parent = parent;
		this.y = y;
		this.speed = speed;
		this.color = color;
	}
	
	/**
	 * PApplet extended by the main program.
	 */
	private PApplet parent;
	/**
	 * X-coordinate of the car's on-screen position in pixels.
	 */
	private int x = (int) (Math.random() * 1000);
	/**
	 * Y-coordinate of the car's on-screen position in pixels.
	 */
	private int y;
	/**
	 * Designated lane the car appears in on screen, 0-4, 0 being the bottom lane.
	 */
	private int lane = 0;
	/**
	 * Speed of the car on screen in pixels per second, automatically 
	 * determined by the lane unless otherwise specified in the constructor.
	 */
	private int speed = 0;
	
	/**
	 * Array of RGB values indicating the car's color.
	 */
	private int[] color = {230, 160, 22};
	
	/**
	 * Accepts a lane and sets the car's initial position, speed, and color based upon it.
	 * @param lane The lane for the car to drive in, 0-4. Zero is the bottommost lane.
	 */
	public void setPos(int lane) {
		switch(lane) {
			case 0:
				this.y = 549;
				this.speed = 2;
				this.color = new int[] {70, 150, 224};
				break;
			case 1:
				this.y = 458;
				this.speed = -4;
				this.color = new int[] {93, 207, 95};
				break;
			case 2:
				this.y = 345;
				this.speed = 6;
				break;
			case 3:
				this.y = 254;
				this.speed = -8;
				this.color = new int[] {240, 194, 96};
				break;
			case 4:
				this.y = 149;
				this.speed = 10;
				this.color = new int[] {240, 71, 46};
		}
	}
	
	/**
	 * Returns the bounds of the car (leftmost, rightmost, topmost, bottommost).
	 * @return
	 */
	public int[] getBounds() {
		return new int[] {this.x - 50, this.x + 50, this.y - 30, this.y + 30};
	}
	
	/**
	 * Moves the car on-screen. If the car drives off screen it wraps back around to the other side of the screen.
	 */
	public void move() {
		if (this.speed != 0) {
			this.x += this.speed;
			if (this.x > 1000) {
				this.x = 0;
			} else if (this.x < 0) {
				this.x = 1000;
			}
		} else this.setPos(this.lane);
	}
	
	/**
	 * Draws the car on screen.
	 */
	public void show() {
		parent.rectMode(2);
		parent.fill(this.color[0], this.color[1], this.color[2]);
		parent.rect(this.x, this.y, 50, 30);
	}
}
