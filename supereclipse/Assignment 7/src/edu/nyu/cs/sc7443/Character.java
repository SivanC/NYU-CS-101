package edu.nyu.cs.sc7443;

/**
 * A class that represents a word; inherits from the OrderedThing class.
 * @author Sivan Cooperman
 * @version 1.0
 */
public class Character extends OrderedThing {
	
	/**
	 * The character's position in the word
	 */
	private int index;
	/**
	 * The character to be stored.
	 */
	private String value;
	
	/**
	 * Constructor that takes a character and its position in the word.
	 * @param value The character.
	 * @param index Its position in the word.
	 */
	public Character(String value, int index) {
		this.index = index;
		this.value = value;
	}
	
	/**
	 * Returns the index of the character.
	 * @return The index of the character.
	 */
	public int getPosition() {
		return this.index;
	}
	
	/**
	 * Returns the value of the character.
	 * @return The value of the character.
	 */
	public String toString() {
		return this.value;
	}
}