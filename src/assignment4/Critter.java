/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {						
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private void initializePos() {
		y_coord = getRandomInt(Params.world_height);
		x_coord = getRandomInt(Params.world_width);
		return;
	}
	protected final void walk(int direction) {
		
	}
	
	protected final void run(int direction) {
		
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.) //don't need to worry about this
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		// Use Class.forName() and newInstance (which I think would be Class.newInstance()
		try{
			Class className = Class.forName(critter_class_name);
			Critter newCritter = (Critter) className.newInstance();	// Should work; if not a real subclass then will fail in general
			newCritter.energy = Params.start_energy;	// Initialize with start energy
			newCritter.initializePos();
			CritterWorld.addCritter(newCritter);
		} catch (Exception e){		// Not sure if correct; please check this :D
			throw new InvalidCritterException(critter_class_name);
		}
		return;
	}
	
	
	
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	}
	
	private String getPositionKey() {
		int x = x_coord;
		int y = y_coord;
		return (Integer.toString(x) + Integer.toString(y));
	}
	
	private static void removeWorldDead(Set critters) {
		Iterator worldIt = critters.iterator();
		//iterate through world
			//check if dead
			//if dead remove from world

		while (worldIt.hasNext()) {
			Critter c = (Critter) worldIt.next();
			int totalEnergy = c.energy - Params.rest_energy_cost;
			if(totalEnergy <= 0) {
				worldIt.remove();
			}
		}
	}
	
	public static void worldTimeStep() {
		Set critters = CritterWorld.getCritterList();		// Create an array from our current list of Critters
		Iterator worldIt = critters.iterator();
		Map<String, ArrayList<Critter>> positionLog = CritterWorld.getPositionLog();
		// Iterate through the array, performing doTimeStep() on each critter
		//iterate through list of critters
			//call do time step
			//add position calculation to position log
		//iterate through list for the dead
			//if dead remove
		while(worldIt.hasNext()) {
			Critter c = (Critter) worldIt.next();
			c.doTimeStep();
			String positionKey = c.getPositionKey();
			ArrayList<Critter> positionList = positionLog.get(positionKey);
			if (positionList == null) {
				//add critter to list of critters
				positionList.add(c);
			} else {
				//add critter with hashcode of position
				positionLog.put(positionKey, new ArrayList<Critter>());
				positionList.add(c);
			}
		}
		
		removeWorldDead(critters);
		//deal with encounter
		
	}
	

	private static void printRowBorder(int rowLength) {
		for (int i = 0; i < rowLength; i++) {
			if (i == 0) {
				System.out.print("+");
				
			} else if (i == (rowLength - 1)) {
				System.out.print("+");
			} else {
				System.out.print("-");
			};
		}
		return;
		
	}
	public static void displayWorld() {
		Set<Critter> critterWorld = CritterWorld.getCritterList();
		int displayLength = (Params.world_height + 2) * (Params.world_width + 2);
		Critter[][] display = new Critter[Params.world_width + 2][Params.world_height + 2];
		for (Critter c : critterWorld) {
			display[c.x_coord + 1][c.y_coord + 1] = c;
		}
		Iterator worldIt = critterWorld.iterator();
		while (worldIt.hasNext()) {
			Critter c = (Critter) worldIt.next();
			display[c.x_coord + 1] [c.y_coord + 1] = c;
		}
		int displayHeight = Params.world_height + 2;
		int displayWidth = Params.world_width + 2;
		String printStream = new String();
			for (int y = 0; y < displayHeight; y++) {
				
				for (int x = 0; x < displayWidth; x++) {
					//check for border
					if ((x == 0) || (y == 0)) {
						if ((x == 0) && (y == 0)) {printStream += "+";}
						else if (x == (displayWidth - 1)) {printStream += "+\n";}
						else if (y == (displayHeight - 1)) {printStream += "+";;}
						else if (x == 0) {printStream += "|";}
						else if (y == 0) {printStream += "-";}
					} else if (x == (displayWidth - 1)) {
						if (y == displayHeight -1) {printStream += "+\n";}
						else {printStream += "|\n";}
		 			} else if (y == (displayHeight - 1)) {
		 				printStream += "-";
		 				
					}
		 			else {
		 				//if this far, then not the border
		 				//check if critter
		 				//if so print critter
		 				//else print space
		 				Critter critterToPrint = display[x][y];
		 				if (critterToPrint != null) {
		 					printStream += critterToPrint.toString();
		 				} else {
		 					printStream += " ";
		 				}
		 			}
				}
				
			}
		
		System.out.print(printStream);
		
		
		
		
	}
}
