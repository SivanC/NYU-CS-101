package edu.nyu.cs.sc7443;

import processing.core.*;

/**
 * A class that recreates the behavior of the frog from Frogger.
 * @author Sivan Cooperman
 * @version 1.5
 */
public class Frog {
	
	/**
	 * Constructor that accepts PApplet parent.
	 * @param parent Parent object that is a child of PApplet class.
	 */
	Frog (PApplet parent) {
		this.parent = parent;
	}
	
	/**
	 * Parent object that is a child of PApplet class.
	 */
	private PApplet parent;
	/**
	 * X-coordinate of the frog's on-screen position in pixels from the left.
	 */
	private int x = 500;
	/**
	 * Y-coordinate of the frog's on-screen position in pixels from the top.
	 */
	private int y = 660;
	/**
	 * Speed of the frog in pixels per second.
	 */
	private int speed = 15;

	/**
	 * Accepts a set of bounds and detects whether the center of the frog
	 * lies within them.
	 * @param bounds An integer array of bounds (leftmost, rightmost, topmost, bottommost)
	 * @return True if the center of the frog lies inside the bounds, false otherwise.
	 */
	public boolean detectCollision(int[] bounds) {
		// if we're within the y bounds
		if (this.y > bounds[2] && this.y < bounds[3]) {
			// if we're withtin the x bounds
			if (this.x > bounds[0] && this.x < bounds[1]) {
				return true;
			}
		} return false;
	}
	
	/**
	 * Called whenever a key is pressed. Moves the frog around 
	 * with the arrow keys.
	 */
	public void move() {
		// how processing distinguishes between ASCII keys and other keys
		if (parent.key == PApplet.CODED) {
			// if up, go up, etc......
			if (parent.keyCode == PApplet.UP) {
				this.y = (this.y - 26 > speed) ? this.y - speed : 26;
			} else if (parent.keyCode == PApplet.DOWN) {
				this.y = (this.y + 26 < 700 - speed) ? this.y + speed : 674;
			} else if (parent.keyCode == PApplet.LEFT) {
				this.x = (this.x - 26 > speed) ? this.x - speed : 26;
			} else if (parent.keyCode == PApplet.RIGHT) {
				this.x = (this.x + 26 < 1000 - speed) ? this.x + speed : 974;
			}
		}
	}
	
	/**
	 * Draws the frog on the screen as a dark green circle.
	 */
	public void show() {
		// color of the frog
		parent.fill(50, 168, 82);
		// draw a circle with that color
		parent.ellipse(this.x, this.y, 26, 26);
	}
}
