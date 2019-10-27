package edu.nyu.cs.sc7443;

import java.util.Scanner;
import java.util.Arrays;

public class TestDrive {
	
	public static void main(String[] args) {
		// getting user input
		Scanner scn = new Scanner(System.in);
		// detecting a crash
		boolean crash = false;
		// instantiating the mopeds
		Moped moped = new Moped("Sivan's Moped");
		Moped cpuMoped1 = new Moped("CPU 1", 15, 7, true);
		Moped cpuMoped2 = new Moped("CPU 2", 5, 3, true);
		
		// printing their locations at the start
		moped.getLocation();
		cpuMoped1.getLocation();
		cpuMoped2.getLocation();
		
		while (!moped.isParked() && !moped.outOfGas() && !crash) {
			
			System.out.println("\n\nWhat would you like to do? Type 'help' for help.\n");
			String task = scn.nextLine().toLowerCase();
			
			switch (task) {
				case "go left":
				case "turn left":
				case "left":
					moped.move("left");
					break;
				case "go right":
				case "turn right":
				case "right":
					moped.move("right");
					break;
				case "straight on":
				case "go straight":
				case "go forwards":
				case "straight":
					moped.move("straight");
					break;
				case "back up":
				case "go back":
				case "go backwards":
				case "back":
					moped.move("back");
					break;
				case "park":
					moped.park();
					break;
				case "how we doin'?":
				case "check gas":
					moped.getGas();
					break;
				case "fill 'er up":
				case "fill tank":
					moped.fillTank();
					break;
				case "help":
					moped.getHelp();
					break;
				case "go to petite abeille":
					moped.goToPetiteAbeille();
					break;
				default:
					System.out.print("\nSorry, please try a different command.");
			}
			
			// the cpus do their actions
			cpuMoped1.drunkAction();
			cpuMoped2.drunkAction();
			
			if (Arrays.equals(moped.getPos(), cpuMoped1.getPos()) || Arrays.equals(moped.getPos(), cpuMoped2.getPos())) {
				System.out.println("\n\nYou have crashed! Your moped has been rendered unusable.");
				crash = true;
			}
		}
		
		scn.close();
	}

}