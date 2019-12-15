package edu.nyu.cs.sc7443;

import java.util.ArrayList;

/** A class that represents a word; implements the SequentiallyOrdered interface and is a child of the OrderedThing class.
 * 
 * @author Sivan Cooperman
 * @version 1.0
 */
public class Word extends OrderedThing implements SequentiallyOrdered  {
	/**
	 * An arraylist in which to store objects of the Character class (not java.lang.Character) that belong to the Word.
	 */
	private ArrayList<Character> chars = new ArrayList<Character>();
	
	/**
	 * The word's location in a sentence, starting at 0.
	 */
	private int index;
	
	/**
	 * Constructor that takes a word and its position in a sentence.
	 * @param word The word.
	 * @param index The word's position in the sentence.
	 */
	public Word(String word, int index) {
		for (int i = 0; i < word.length(); i++) {
			Character c = new Character(word.substring(i, i+1), i);
			this.chars.add(c);
		}
		this.index = index;
	}
	
	/**
	 * Returns the first character of a word.
	 * @return The first character of a word.
	 */
	public OrderedThing getFirst() {
		OrderedThing first = this.chars.get(0);
		return first;
	}
	
	/**
	 * Returns the last character of a word.
	 * @return The last character of a word.
	 */
	public OrderedThing getLast() {
		OrderedThing last = this.chars.get(chars.size() - 1);
		return last;
	}
	
	/**
	 * Returns an arraylist containing all Character objects that make up a word.
	 * @return An arraylist containing all Character objects that make up a word.
	 */
	public ArrayList<OrderedThing> getSequence() {
		ArrayList<OrderedThing> a = new ArrayList<OrderedThing>();
		for (int i = 0; i < chars.size(); i++) {
			OrderedThing ot = this.chars.get(i);
			a.add(ot);
		}
		
		return a;
	}
	
	/**
	 * Returns an integer representing the word's location in a sentence, starting at 0.
	 * @return An integer representing the word's location in a sentence, starting at 0.
	 */
	public int getPosition() {
		return this.index;
	}
	
	/**
	 * Returns a string representation of the Word object.
	 * @return A string representation of the Word object.
	 */
	public String toString() {
		String s = "";
		for (OrderedThing i : chars) {
			s += i.toString();
		}
		return s;
	}
}
