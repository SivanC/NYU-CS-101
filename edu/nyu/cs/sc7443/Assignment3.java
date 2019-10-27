package edu.nyu.cs.sc7443;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.Math;

public class Assignment3 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// New scanner for user input
		Scanner scanner = new Scanner(System.in);

		// Getting the file name. Doesn't have to be a .txt file.
		System.out.println("\nWhat file would you like to open? \n");
		String filename = scanner.nextLine();

		// readFile retrieves the text from the file
		ArrayList text = readFile(filename, scanner);
		double numWords = text.size();

		// The catch blocks in readFile have to return an ArrayList,
		// so I made it empty in order to check if there was a problem reading the file.
		if (text.isEmpty() == false) {
			// Getting the user's tic list
			System.out.println("What words would you like to search for? \n");
			String searchWordsInput = scanner.nextLine();
			String[] searchWords = searchWordsInput.split(",");
			// Getting the number of times each tic appeared using
			// regular expressions.
			int[] frequencies = regexMatcher(text, searchWords);
			// Printing out the results
			printStats(searchWords, frequencies, numWords);
		scanner.close();
		}
	}

	public static ArrayList readFile(String filename, Scanner scanner) throws FileNotFoundException, IOException {
		System.out.println("\nReading file...\n");
		try {
			// Creating a new file reading object
			BufferedReader reader = new BufferedReader(new FileReader(filename), 16384);

			ArrayList text = new ArrayList();
			boolean keepReading = true;
			// Reading line by line and adding words, separated by spaces,
			// to our arraylist.
			while(keepReading) {
				String line = reader.readLine();

				if (line != null) {
					String[] splitLine = line.split(" ");
					
					for (String wordInText : splitLine) {
						text.add(wordInText);
					}
				} else {
					keepReading = false;
				}
			}

			return text;
		// Catch blocks for the errors that can arise from reading files.
		} catch (FileNotFoundException fnfe) {
			ArrayList errorReturnArray = new ArrayList();
			System.out.println("That\'s not a valid file name. Exiting.");
			return errorReturnArray;
		} catch (IOException ioe) {
			ArrayList errorReturnArray = new ArrayList();
			System.out.println("Oops, there was an error reading the file.");
			return errorReturnArray;
		}
	}

	public static int[] regexMatcher(ArrayList text, String[] searchWords) {
		System.out.println("\nMatching words...\n");
		int[] frequencies = new int[searchWords.length];
		/* For each word in the user's tic list, I compile a new
		regular expression and match it with the current word.
		Obviously not the most robust, but works well with punctuation and
		is case insensitive. Stores the matches in an array. */
		for (int i = 0; i < frequencies.length; i++) {
			String wordToMatch = searchWords[i].trim();
			Pattern pattern = Pattern.compile("(\\p{Space}|\\p{Punct}|\\n|^)+(" + wordToMatch + ")(\\p{Space}|\\p{Punct}|\\n|$)+", Pattern.CASE_INSENSITIVE);

			for (Object wordFromText : text) {
				Matcher matcher = pattern.matcher(wordFromText.toString());
				boolean isMatch = matcher.matches();

				if (isMatch) {
					frequencies[i] += 1;
				}
			}
		}

		return frequencies;
	}

	public static void printStats(String[] searchWords, int[] frequencies, double numWords) {
		System.out.println("StaTICstics:\n");
		// Getting the total number of tics
		int ticSum = 0;

		for (int freq : frequencies) {
			ticSum += freq;
		}
		
		System.out.println("Total number of tics: " + ticSum);
		System.out.println("Density of tics: " + Math.round((ticSum/numWords)*100)/100.0);

		ArrayList wordLengths = new ArrayList();

		for (int i = 0; i < searchWords.length; i++) {
			String searchWord = searchWords[i];
			searchWords[i] = searchWord.trim();
			int wordLength = searchWord.length();
			wordLengths.add(wordLength);
		}

		int maxLength = (int) Collections.max(wordLengths);

		for (int i = 0; i < frequencies.length; i++) {
			String tic = searchWords[i].toString();

			int occurences = frequencies[i];
			double percOfTics = (occurences/1.0)/ticSum;

			String spaces1 = new String(new char[maxLength + 2 - tic.length()]).replace("\0", " ");
			int length2 = 1;
			if (occurences != 0) {
				length2 = (int) Math.log10(occurences);
			}
			String spaces2 = new String(new char[(int) (4 - length2 + 1)]).replace("\0", " ");

			System.out.println(tic + spaces1 + " / " + occurences + " occurences" + spaces2 + " / " + Math.round(percOfTics*100) + "% of all tics");
		}
	}
}