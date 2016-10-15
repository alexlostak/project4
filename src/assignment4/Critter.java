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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
	
	public static void worldTimeStep() {
		Set critters = CritterWorld.getCritterList();		// Create an array from our current list of Critters
		Iterator myIt = critters.iterator();				// Iterate through the array, performing doTimeStep() on each critter
	}
	
	
	
	
	// Attempt at creating the encounter implementation but I think adding this class violates the conditions set by the instructor
	
/*	
	abstract class CritterWorld extends TestCritter {			// HOW DO I MAKE THIS WORK??????????????
		
		Set<Critter> myCritters = new HashSet<Critter>();
		int population = 0;
		
		public CritterWorld(){
			//Don't need constructor
		}
		
		//Adds critter to set
		public void addCritter(Critter c){
			myCritters.add(c);				//add critter					
			population += 1;				//increase tag count
		}
		
		public void removeCritter(Critter c){
			myCritters.remove(c);
			population -= 1;
		}
		
		 
		 * 
		 * The way this system works is by 
		 * 
		 * to access the getters and setters for the Critters we have to extend TestCritter AND.... (see below)
		 
		public ArrayList<Critter> findEncounters(){
			
			ArrayList<Critter> battleList = new ArrayList();	// Our list of critters we need to battle
			
			for(int x = 0; x < Params.world_width; x += 1){
				for(int y = 0; y < Params.world_height; y += 1){
					Iterator<Critter> myIt = myCritters.iterator();
					while(myIt.hasNext()){
						TestCritter thisCritter = (TestCritter)myIt.next();	// ... We have to mask each Critter as a TestCritter to access the x/y coordinates 
	// -->					// HOPEFULLY by changing the object it doesn't eliminate the parameters we need in the first place lol
						if(thisCritter.getX_coord() == x && thisCritter.getY_coord() == y){ battleList.add(thisCritter); } //If the critter is at this location add them to the list
					}
					battleList.add(null);	// a null specifies we have reached the end of the list of the critters at a specific location.
				}
			}
			return battleList;		// After adding every critter to every specified location we can handle encounters
		}
		
	}
*/
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
		Critter[][] display = new Critter[Params.world_width][Params.world_height];
		for (Critter c : critterWorld) {
			display[c.x_coord][c.y_coord] = c;
		}
		//now have all the critters stored
		//need to print top border first
		//print left side border
		//print interior
		//print right side border
		
		int row = 0;
		int col = 0;
		printRowBorder(Params.world_width + 2);
		
		
		
		
	}
}
