package edu.nyu.cs.sc7443;

import java.util.Scanner;

public class Assignment1 {

	public static void main(String[] args) {
		// Problem 1.7
		// a)
		double closeToPi = 4 * (1.0 - 1.0/3 + 1.0/5 - 1.0/7 + 1.0/9 - 1.0/11);
		// b)
		double closerToPi = 4 * (1.0 - 1.0/3 + 1.0/5 - 1.0/7 + 1.0/9 - 1.0/11 + 1.0/13);
		System.out.println(closeToPi);
		System.out.println(closerToPi);
		
		// Problem 1.10
		// 14 km/45.5 min * 60min/1hr = 14*60km/45.5hr / 45.5/45.5 = 
		// (840/45.5) km / 1 hr * 1 mi / 1.6 km = (840/72.8) mi / 1 hr
		double mph = (14.0/45.5)*60.0/1.6;
		System.out.println(mph);
		
		// Problem 1.11
		int secondsInYear = 365*24*60*60;
		int pop = 312032486;
		
		/* I used integers because the fractional part doesn't actual represent a person;
		you can't add 0.5 of a birth and 0.5 of an immigrant to create an additional person. */
		
		System.out.println("Initial population: " + pop);
		
		for (int i=1; i < 6; i++) {
			
			pop += secondsInYear / 7;
			pop -= secondsInYear / 13;
			pop += secondsInYear / 45;
			
			System.out.println("Population in year " + i + ":" + pop);
		}
		
		// Problem 2.5
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter the subtotal and a gratuity rate:");
		
		String price = scanner.nextLine();
		String[] subRateList = price.split(" ");
		
		double subtotal = Double.parseDouble(subRateList[0]);
		double gratuity = Double.parseDouble(subRateList[1]) / 100;
		
		System.out.println("The gratuity is " + subtotal*gratuity + " and the total is " + (subtotal + subtotal*gratuity));
		
		// Problem 2.9
		
		System.out.println("Enter v0, v1, and t:");
		
		String vars = scanner.nextLine();
		String[] physVarList = vars.split(" ");
		
		double v0 = Double.parseDouble(physVarList[0]);
		double v1 = Double.parseDouble(physVarList[1]);
		double t = Double.parseDouble(physVarList[2]);
		
		System.out.println("The average acceleration is " + ((v1 - v0)/t));
		
		// Problem 2.13
		
		System.out.println("Enter the monthly savings amount: ");
		
		String pString = scanner.nextLine();
		double p = Double.parseDouble(pString);
		double rate = 0.05/12;
		double a = 0;
		
		for (int j = 0; j < 6; j++) {
			a += p;
			a *= 1 + rate;
		}
		
		System.out.println("After the sixth month, the account value is " + a);
		
		scanner.close();
	}

}