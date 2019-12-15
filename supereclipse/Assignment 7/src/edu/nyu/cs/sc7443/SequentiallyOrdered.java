package edu.nyu.cs.sc7443;

import java.util.ArrayList;

/**
 * An interface for objects who are comprised of other ordered objects in sequence.
 * @author Sivan Cooperman
 * @version 1.0
 */
public interface SequentiallyOrdered {
	
	/**
	 * Returns the first item of the sequence.
	 * @return The first item of the sequence.
	 */
	public abstract OrderedThing getFirst();
	/**
	 * Returns the last item of the sequence.
	 * @return The last item of the sequence.
	 */
	public abstract OrderedThing getLast();
	/**
	 * Returns the sequence of ordered Objects.
	 * @return The sequence of ordered Objects.
	 */
	public abstract ArrayList<OrderedThing> getSequence();

}