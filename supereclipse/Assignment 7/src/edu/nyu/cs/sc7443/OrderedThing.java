package edu.nyu.cs.sc7443;

/**
 * A class representing an object that can be ordered.
 * @author Sivan Cooperman
 * @version 1.0
 */
public abstract class OrderedThing {
	
	/**
	 * The object's order in a sequence
	 */
	private int index;
	
	/**
	 * Return the position of the object in the sequence.
	 * @return The position of the object in the sequence.
	 */
	public abstract int getPosition();
}
