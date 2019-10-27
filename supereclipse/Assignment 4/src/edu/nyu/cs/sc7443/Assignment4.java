// File: Assignment4.java
// Author: Sivan Cooperman
// Usage: Run the file, and then enter a 
// Description: Mines data from the NYC Open Data Bridge Strike dataset (https://data.cityofnewyork.us/Transportation/NYC-Bridge-Strike-Data/jdn9-td9w)
// 			    and displays it to the user. I am using the following columns: Date, Borough, Main Street, Cross Street, State of Operator's License.

package edu.nyu.cs.sc7443;

import java.io.*;
import java.util.*;
import java.util.regex.*;


public class Assignment4 {
	// Instantiate scanner to take use input
	static final Scanner scn = new Scanner(System.in);
	
	public static String[][] readFile() throws FileNotFoundException, IOException {
		// Instantiate file reader to read the .csv
		BufferedReader reader = new BufferedReader(new FileReader("NYC_Bridge_Strike_Data.csv"), 16384);
		
		// new ArrayList to store my rows before I put them in the 2d array; easiest way I can think of to do the multi-dimensional array dynamically
		List<String[]> data = new ArrayList<String[]>();
		
		// Flagged while loop to read all the lines of the file and add them to an ArrayList
		boolean read = true;
		while (read) {
			String row = reader.readLine();
			// Stop if we're out of lines
			if (row == null) {
				read = false;
			// Take those five indices and add them to the ArrayList
			} else {
				String[] splitRow = row.split(",");
				String[] splice = {splitRow[0], splitRow[1], splitRow[3], splitRow[4], splitRow[8]};
				data.add(splice);
			}
		}
		// bye bye reader
		reader.close();
		// Converting the ArrayList to a 2d string array
		String[][] dataArray = data.toArray(new String[data.size()][]);
		
		return dataArray;
	}
	
	public static String[][] formatData(String[][] data) {
		// In this method I correct capitalization of some columns to make them more readable
		for (String[] row : data) {
			row[1] = row[1].substring(0,1) + row[1].substring(1).toLowerCase();
			row[2] = row[2].substring(0,1) + row[2].substring(1).toLowerCase();
			// Some of the cross-street cells are empty
			if (!row[3].equals("")) row[3] = row[3].substring(0,1) + row[3].substring(1).toLowerCase();
		}
		
		return data;
	}
	
	public static void printData(String[][] data, Scanner scn) {
		
		// First message the user sees when launching the program
		System.out.println("Welcome to the NYC Bridge Strike Database! Please choose from these options:\n"
				+ "a) Enter a date to see all bridge strikes on that date (MM/DD/YYYY, 01/07/2013 to 08/15/2019).\n"
				+ "b) Enter a street to see all bridge strikes that occurred on that street.\n"
				+ "c) Enter a borough to see all bridge strikes in that borough.\n");
		// Getting the user's query
		String response = scn.nextLine().toLowerCase();
		
		// Logic to determine what option the user is inputting; no street regex bc that's a lot harder
		boolean dateMatch = Pattern.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d", response);
		boolean boroughMatch = Pattern.matches("bronx|queens|staten island|manhattan|brooklyn", response.toLowerCase());
		
		// New ArrayList to hold only the rows we want to print for the user, again dynamically so using ArrayList first
		List<String[]> toPrint = new ArrayList<String[]>();
		
		// If it's a date
		if (dateMatch) {
			for (String[] row : data) {
				// All crashes on the given date
				if (row[0].equals(response)) {
					toPrint.add(row);
				}
			}
		// If it's a borough
		} else if (boroughMatch) {
			for (String[] row : data) {
				// All crashes in the given borough
				if (row[1].toLowerCase().equals(response.toLowerCase())) {
					toPrint.add(row);
				}
			}
		// Otherwise, it's a street
		} else {
			for (String[] row : data) {
				// All instances of the street name in the table
				if (row[2].toLowerCase().equals(response.toLowerCase()) || row[3].toLowerCase().equals(response.toLowerCase())) {
					toPrint.add(row);
				}
			}
		}
		
		// Converting once again from ArrayList to String[][]
		String[][] formatted = toPrint.toArray(new String[toPrint.size()][]);
		
		// If there aren't any results we say so and terminate the program.
		if (formatted.length == 0) {
			System.out.println("\nOops! We could not find any results for your keyword \""+response+"\".");
		} else {
			// Getting rid of the brackets all over the place
			String[] statement = Arrays.deepToString(formatted).replace("[[", "").replace("]]", "").split(Pattern.quote("], ["));
			
			// Various variables to help with keeping track of where we are in the paginated search results
			
			int l = statement.length;
			// keeps track of where to start each page
			int startIndex = 0;
			// can change this to change how many results are displayed, currently set to 10 per page
			int increment = (10 > l) ? l : 10;
			boolean terminate = false;

			// While we haven't run out of results and the user still wants results
			while (!terminate) {
				// Header for the table, labels are right-aligned with the columns
				System.out.println("\nYear       |  Borough       |  Main Street                         |  Cross Street                        |  State");
				
				int cap = (startIndex + increment < l) ? startIndex + increment : l;
				// While we haven't reached the limit for this page of results
				while (startIndex < cap) {
					String[] splitLine = statement[startIndex].split(",");
					// Some formatted printing of each line
					System.out.printf("%s | %-14s | %-36s | %-36s | %-8s%n", 
							splitLine[0], 
							splitLine[1], 
							splitLine[2], 
							splitLine[3], 
							splitLine[4]
							);
					// Incrementing the start index so that when we start the outer while loop again we're in a different place
					startIndex++;
				}
				System.out.printf("%nShowing results %d-%d of %d results.",
						// nesting is fun! just kidding, this is gross. It was almost a triple ? too.
						// are we at the beginning? use 1. if not, are we in the middle?
						// use the startIndex minus the increment, kind of. Same for if the total number of entries is evenly divisible by the increment.
						// are we at the end with a non-divisible number of entries? Subtract the remainder, kind of. gross. sorry.
						(startIndex <= increment) ? 1 : (startIndex == l && l % increment != 0) ? l - ((l % increment) - 1) : startIndex - (increment - 1),
						startIndex,
						l);
				
				// If we haven't run out of results, ask the user if they want to see more
				if (startIndex != l) {
					// I think I'm addicted to the conditional operator.
					System.out.printf(" Would you like to see the next %d? (y/n)%n", (startIndex + increment > l) ? l - startIndex:increment);
					String seeMoreResults = scn.nextLine().toLowerCase();
					// If they don't say 'y' or 'yes', end it, otherwise we go through the big while loop again
					if (!seeMoreResults.equals("y") && !seeMoreResults.equals("yes")) {
						terminate = true;
					}
				} else if (startIndex == cap) {
					terminate = true;
				}
			}
		}
		// thought it could get away with being left open!
		scn.close();
	}
		
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[][] data = readFile();
		String[][] formattedData = formatData(data);
		printData(formattedData, scn);
	}
}
