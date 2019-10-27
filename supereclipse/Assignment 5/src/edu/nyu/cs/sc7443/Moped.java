package edu.nyu.cs.sc7443;

import java.lang.Math;

public class Moped {
	// constructor with just a name
	public Moped(String name) {
		this.name = name;
	}
	// constructor to set a moped's position
	public Moped(String name, int street, int ave) {
		this.name = name;
		this.street = street;
		this.ave = ave;
	}
	// constructor to set a moped's position and who controls it
	public Moped(String name, int street, int ave, boolean drunk) {
		this.name = name;
		this.street = street;
		this.ave = ave;
		this.drunk = drunk;
	}
	
	// "go left" and "go right", as well as which direction you're facing, change depending on your gear.
	static private final boolean DRIVE = true;
	static private final boolean REVERSE = false;
	
	// how much gas you have in twentieths of a gallon; each block traveled burns 1/20 of a gallon
	private int gas = 20;
	
	private int street = 10; // streets go from 1 to 200, get bigger in the north
	private int ave = 5; // avenues go from 1 to 10, get bigger in the west
	
	// associating the directions with each like this helps to switch directions more easily
	private String[] cardinals = {"south", "east", "north", "west"}; 
	private int cardinal = 0;
	
	// to tell the program when to stop
	private boolean parked = false;
	
	// a non-static variable representing the gears above
	private boolean gear = Moped.DRIVE;
	
	// is this a cpu controlled moped or not
	private boolean drunk = false;
	// to identify multiple mopeds
	private String name;
	
	// park the moped
	public void park() {
		System.out.printf("%nThe moped has been parked on the sidewalk at %s Street and %s Avenue.",
				street, ave);
		this.parked = true;
	}
	
	// getter for gas as a percentage of the tank
	public void getGas() {
		if (!drunk) System.out.print("\nYou have " + (this.gas * 100)/20 + "% gas left.");
		else System.out.printf("%n%s checked their gas. ", this.name);
	}
	
	// getter for whether the tank is empty; not combined with the above function because it 
	// sounds better when called and I would have to print the gas every time I checked it
	public boolean outOfGas() {
		if (this.gas == 0) return true;
		else return false;
	}
	
	// getter of our location (street, ave, direction, gear)
	public void getLocation() {
		System.out.printf("%n%s is currently on %s Street and %s Avenue facing %s, in %s. ",
							this.name, this.street, this.ave, this.cardinals[this.cardinal], (this.gear) ? "drive" : "reverse");
	}
	
	public int[] getPos() {
		int[] pos = {this.street, this.ave};
		return pos;
	}
	
	// getter of the list of commands available to the user
	public void getHelp() {
		System.out.print("\nAvailable commands: go left, go right, straight on, back up, how we doin'?, fill 'er up, park, go to Petite Abeille, help");
	}
	
	// getter for parking status of the moped
	public boolean isParked() {
		return this.parked;
	}
	
	// fills the tank to the maximum
	public void fillTank() {
		System.out.printf("%n%s's gas tank has been filled up!", this.name);
		this.gas = 20;
	}
	
	// sets the direction to a given cardinal direction
	public void setDirection(int cardinal) {
		this.cardinal = cardinal;
	}
	
	// sets the direction to a cardinal direction based on the relative orientation of the moped
	public void setDirection(String direction) {
		int newCardinal = this.cardinal;
		
		// going left/right doesn't change my gear, just my orientation
		// (also, going left in drive and right in reverse are the same in terms of which
		// direction I'm facing, and vice versa)
		if ((direction.equals("left") && gear) || (direction.equals("right") && !gear)) {
			// here the simplicity of the directions array is demonstrated
			// in that I don't have to repeat the operation for both axes
			// conditional operator to loop back around to the beginning
			newCardinal = (this.cardinal == 3) ? 0 : newCardinal + 1;
		} else if ((direction.equals("right") && gear) || (direction.equals("left") && !gear)) {
			newCardinal = (this.cardinal == 0) ? 3 : newCardinal - 1;
		// going forwards/backwards doesn't change my orientation, just my gear
		} else if (direction.equals("back")) this.gear = Moped.REVERSE;
		else if (direction.equals("straight")) this.gear = Moped.DRIVE;
		
		// now that we've derived the cardinal direction from the relative we can plug it into the other setDirection() function
		this.setDirection(newCardinal);
	}
	
	// moves the moped in a given cardinal direction based on a given direction relative to the moped's orientation
	public void move(String direction) {
		// first discern our cardinal direction based on the relative one
		this.setDirection(direction);
		
		// flag so we can know whether a movement is valid laters
		boolean canMove = true;
		switch (this.cardinal) {
			// if facing south
			case 0:
				// if we're in drive
				if (gear) {
					// if we can move south, go south
					if (street > 1) street--;
					// otherwise raise a flag
					else canMove = false;
				// if we are in reverse and can move north, do so
				} else if (street < 200) street++;
				// otherwise raise a flag
				else canMove = false;
				break;
			case 1:
				if (gear) {
					if (ave > 1) ave--;
					else canMove = false;
				} else if (ave < 10) ave++;
				else canMove = false;
				break;
			case 2:
				if (gear) {
					if (street < 200) street++;
					else canMove = false;
				} else if (street > 1) street--;
				else canMove = false;
				break;
			case 3:
				if (gear) {
					if (ave < 10) ave++;
					else canMove = false;
				} else if (ave > 1) ave--;
				else canMove = false;
				break;
		}
		// print out our location
		this.getLocation();
		
		// if we've made a legal movement, burn some fuel and print out an ad if we need to
		if (canMove) {
			if (!drunk) {
				if (street == 79 && ave == 8) System.out.println("While you're here, why don't you visit the American Museum of Natural History's T. rex exhibit?\n");
				else if (street == 74 && ave == 1) System.out.println("Did you know the Memorial Sloan Kettering Cancer Center is the oldest and largest private cancer center in the world?\n");
				else if (street == 12 && ave == 4) System.out.println("The Strand bookstore has an amazing rare book collection on the third floor. Check it out!\n");
				else if (street == 3 && ave == 6) System.out.println("Fay Da Bakery has a plethora of delicious pastries!\n");
			}
			
			this.gas -= 1;
			// automatically fill up low gas for cpu
			if (this.drunk && this.gas < 5) this.fillTank();
		// otherwise tell them no dice on the movement
		} else System.out.println("Sorry, you can't move in that direction right now.");
		
		// if we're out of gas, say so
		if (this.gas == 0) {
			System.out.printf("%n%s has run out of gas.", this.name);
		}
	}
	
	// if instead we're given a cardinal direction to move in irrespective of our orientation
	public void move(int cardinal) {
		this.setDirection(cardinal);
		this.move("straight");
	}
	
	public void drunkAction() {
		// can only be performed by CPU-controlled mopeds
		if (this.drunk) {
			// randomly generate a number between 0 and 6
			int action = (int) (Math.random() * 7);
			// obviously the computer doesn't need to use help and shouldn't park, so only 7 actions
			switch (action) {
				case 0:
					this.move("left");
					break;
				case 1:
					this.move("right");
					break;
				case 2:
					this.move("straight");
					break;
				case 3:
					this.move("back");
					break;
				case 4:
					this.fillTank();
					break;
				case 5:
					this.getGas();
					break;
				case 6:
					this.goToPetiteAbeille();
			}
		}
	}
	
	// method that takes the user to 17th Street and 6th Avenue; assumes that the user can turn the moped around and does not have to go around the block
	// note that this is considered one action, and thus multiple blocks are travelled in one "turn"
	public void goToPetiteAbeille() {
		// while we're not there already
		while (this.street != 17 || ave != 6) {
			// if we're north of it, go south, or vice versa
			if (this.street > 17) this.move(0);
			else if (this.street < 17) this.move(2);
			
			// same down here
			if (this.ave > 6) this.move(1);
			else if (this.ave < 6) this.move(3);
		}
		// we're here
		System.out.printf("%n%s has arrived! Bon apetit.", this.name);
	}
}
