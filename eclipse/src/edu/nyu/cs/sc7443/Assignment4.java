// File: Assignment4.java
// Author: Sivan Cooperman
// Date: 10.11.2019
// Usage: Run the file, and then enter a name, year (2011-16), or sex
// Description: Mines data from the NYC Open Data Popular Baby Names dataset (https://data.cityofnewyork.us/Health/Popular-Baby-Names/25th-nujf)
// 			    and displays it to the user. I am using all columns of the dataset (Year of Birth, Gender, Ethnicity, Child's First Name, Count, Rank).

package edu.nyu.cs.sc7443;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Assignment4 {
	// Instantiate scanner to take use input
	static final Scanner scn = new Scanner(System.in);
	
	public static String[][] readFile() throws FileNotFoundException, IOException {
		// Instantiate file reader to read the .csv
		BufferedReader reader = new BufferedReader(new FileReader("Popular_Baby_Names.csv"), 16384);
		
		// new ArrayList to store my rows before I put them in the 2d array
		List<String[]> data = new ArrayList<String[]>();
		
		// Flagged while loop to read all the lines of the file and add them to an ArrayList
		boolean read = true;
		while (read) {
			String row = reader.readLine();
			
			if (row == null) {
				read = false;
			} else {
				String[] splitRow = row.split(",");
				// Make sure there are no duplicate rows in the file (because there were some), otherwise add the row to the ArrayList.
				// No-duplicates code found at https://stackoverflow.com/questions/4849051/using-contains-on-an-arraylist-with-integer-arrays
				if (!data.stream().anyMatch(a -> Arrays.equals(a, splitRow))) {
					data.add(splitRow);
				}
			}
		}
		
		reader.close();
		// Converting the ArrayList to a 2d string array
		String[][] dataArray = data.toArray(new String[data.size()][]);
		
		return dataArray;
	}
	
	public static String[][] formatData(String[][] data) {
		// In 2011 the ethnicity column was labeled differently, so I'm just fixing that here, and converting some columns to lowercase
		// Obviously in a more serious project I would write all this to the file once and be done with it
		for (String[] row : data) {
			row[1] = row[1].toLowerCase();
			row[2] = row[2].toLowerCase();
			row[3] = row[3].toLowerCase();
			
			if (row[2].equals("asian and paci")) {
				row[2] = "asian and pacific islander";
			} else if (row[2].equals("white non hisp")) {
				row[2] = "white non hispanic";
			} else if (row[2].equals("black non hisp")) {
				row[2] = "black non hispanic";
			}
		}
		return data;
	}
	
	public static void printData(String[][] data, Scanner scn) {
		
		// First message the user sees when launching the program
		System.out.println("Welcome to the Baby Name Database! Please choose from these options:\n"
				+ "a) Enter a name to see data about that name.\n"
				+ "b) Enter a year (between 2011-16) to see the most popular names that year.\n"
				+ "c) Enter a sex (male or female) to see the most popular names for that sex.\n");
		// Getting the user's query
		String response = scn.nextLine().toLowerCase();
		String category = "none";
		// Switch-case block to determine the kind of query
		switch(response) {
			case "2011":
			case "2012":
			case "2013":
			case "2014":
			case "2015":
			case "2016":
				category = "year";
				break;
			case "male":
			case "female":
				category = "sex";
				break;
			default:
				category = "name";
		}
		
		// New ArrayList to hold only the rows we want to print for the user
		List<String[]> toPrint = new ArrayList<String[]>();
		
		// If statements determine what we're looking for
		if (category.equals("year")) {
			for (String[] row : data) {
				// Top 5 names for each ethnicity for each year
				if (row[0].equals(response) && "1 2 3 4 5".contains(row[5])) {
					toPrint.add(row);
				}
			}
		} else if (category.equals("sex")) {
			for (String[] row : data) {
				// Top name for each ethnicity for each year for each sex
				if (row[1].equals(response) && row[5].equals("1")) {
					toPrint.add(row);
				}
			}
		} else if (category.equals("name")) {
			for (String[] row : data) {
				// All instances of the name in the table
				if (row[3].equals(response)) {
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
			// Various variables to help with keeping track of where we are in the paginated search results
			int startIndex = 0;
			int increment = 10;
			boolean terminate = false;
			
			// Getting rid of the brackets all over the place and creating a shortcut for a variable I use a lot
			String[] statement = Arrays.deepToString(formatted).replace("[[", "").replace("]]", "").split(Pattern.quote("], ["));
			int l = statement.length - 1;
			
			// While we haven't run out of results and the user still wants results
			while (!terminate) {
				// Set the index of the last result we want to display to either where we started + the increment or the end of the results
				int cap = (startIndex + increment < statement.length) ? startIndex + increment : l;
				// Header for the table, labels are right-aligned with the columns
				System.out.println("\nYear |  Sex     |  Ethnicity                   |  First Name     |  Count   |  Rank   ");
				// While we haven't reached the limit for this page of results
				while (startIndex <= cap) {
					String[] splitLine = statement[startIndex].split(",");
					// Some formatted printing of each line
					System.out.printf("%s | %-8s | %-28s | %-15s | %-8s | %-4s%n", 
							splitLine[0], 
							splitLine[1], 
							splitLine[2], 
							splitLine[3], 
							splitLine[4], 
							splitLine[5]
							);
					// Incrementing the start index so that when we start the outer while loop again we're in a different place
					startIndex++;
				}
				// If we're not at the end of our results
				if (cap != l) {
					System.out.printf("%nShowing results %d-%d of %d results. Would you like to see the next %d? (y/n)%n",
							startIndex-increment,
							cap, 
							l,
							(startIndex + increment > l) ? l - startIndex:increment
							);
				// If we are at the end of our results
				} else {
					System.out.printf("%nShowing results %d-%d of %d results.%n",
							// Some annoying math around how to display that first number based on how many results we have
							((l)%increment == 0 && l != 0) ? (l) - (increment - 1) : statement.length - (l)%increment, 
							l + 1, 
							l + 1
							);
					terminate = true;
				}
				// If we haven't run out of results, ask the user if they want to see more
				if (!terminate) {
					String seeMoreResults = scn.nextLine();
					// If they don't say 'y' or 'yes', otherwise we go through the big while loop again
					if (!seeMoreResults.equals("y") && !seeMoreResults.equals("yes")) {
						terminate = true;
					}
				}
			}
		}
		scn.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String[][] data = readFile();
		String[][] formattedData = formatData(data);
		printData(formattedData, scn);
	}
}