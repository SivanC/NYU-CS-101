package edu.nyu.cs.sc7443;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example class to test the Character, Word, and Sentence classes
 * @author Sivan Cooperman
 * @version 1.0
 */
public class TestSequence {
	public static void main(String[] args) {
		
		Sentence sentence = new Sentence("This is a test sentence.");
		
		Word firstWord = (Word) sentence.getFirst();
		Word lastWord = (Word) sentence.getLast();
		ArrayList<OrderedThing> sentenceArrayList = sentence.getSequence();
		
		Character firstChar = (Character) firstWord.getFirst();
		Character lastChar = (Character) lastWord.getLast();
		ArrayList<OrderedThing> wordArrayList = firstWord.getSequence();
		
		System.out.println(sentence.toString());
		System.out.println(firstWord.toString());
		System.out.println(lastWord.toString());
		System.out.println(Arrays.toString(sentenceArrayList.toArray()));
		
		System.out.println(firstChar.toString());
		System.out.println(lastChar.toString());
		System.out.println(Arrays.toString(wordArrayList.toArray()));
		
		System.out.println(firstWord.getPosition());
		System.out.println(firstChar.getPosition());
	}
}
