package edu.nyu.cs.sc7443;

import java.util.ArrayList;

/**
 * A class that represents a sentence; implements the SequentiallyOrdered interface.
 * @author Sivan Cooperman
 * @version 1.0
 */
public class Sentence implements SequentiallyOrdered {
	/**
	 * An arraylist in which to store objects of the Word class that belong to the Sentence.
	 */
	private ArrayList<Word> words = new ArrayList<Word>();
	
	/**
	 * Constructor that takes a String and splits it into words that are added to the `words` arraylist.
	 * @param sentence A string containing a sentence.
	 */
	public Sentence(String sentence) {
		String[] splitWords = sentence.split("[^\\w']+");
		for (int i = 0; i < splitWords.length; i++) {
			Word word = new Word(splitWords[i], i);
			this.words.add(word);
		}
	}
	
	/**
	 * Returns the word at position 0 of a sentence.
	 * @return The word at position 0 of a sentence.
	 */
	public OrderedThing getFirst() {
		OrderedThing first = this.words.get(0);
		return first;
	}
	
	/**
	 * Returns the word at the last position of a sentence.
	 * @return the word at the last position of a sentence.
	 */
	public OrderedThing getLast() {
		OrderedThing last = this.words.get(words.size() - 1);
		return last;
	}
	
	/**
	 * Returns an arraylist containing all the words belonging to a Sentence.
	 * @return an arraylist containing all the words belonging to a Sentence.
	 */
	public ArrayList<OrderedThing> getSequence() {
		ArrayList<OrderedThing> a = new ArrayList<OrderedThing>();
		for (int i = 0; i < words.size(); i++) {
			OrderedThing ot = this.words.get(i);
			a.add(ot);
		}
		
		return a;
	}
	
	/**
	 * Returns a string representation of the Sentence object.
	 * @return A string representation of the Sentence object.
	 */
	public String toString() {
		String s = "";
		for (OrderedThing word : words) {
			s += word.toString() + " ";
		}
		return s;
	}
}
