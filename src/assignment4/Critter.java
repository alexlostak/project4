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
	private static Map<String, ArrayList<Critter>> positionLog = new HashMap<String, ArrayList<Critter>>();
	private static boolean encounter_state = false; 		// Global for determining how our walk and run functions work depending on what we're doing!
	
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
	
	private void move(int speed, int direction){
			 if (direction == 0) { this.x_coord +=  speed; }
		else if (direction == 1) { this.x_coord +=  speed; this.y_coord += -speed; }
		else if (direction == 2) { 					       this.y_coord += -speed; }
		else if (direction == 3) { this.x_coord += -speed; this.y_coord += -speed; }
		else if (direction == 4) { this.x_coord += -speed; }
		else if (direction == 5) { this.x_coord += -speed; this.y_coord +=  speed; }
		else if (direction == 6) { 					       this.y_coord +=  speed; }
		else if (direction == 7) { this.x_coord +=  speed; this.y_coord +=  speed; }
		
		//Screen wrap-around catches! (Negative and over sizes)
		if (this.x_coord > Params.world_width)  { this.x_coord = this.x_coord - (Params.world_width + 1);  }
		if (this.y_coord > Params.world_height) { this.y_coord = this.y_coord - (Params.world_height + 1); }
		
		if (this.x_coord < 0) { this.x_coord = this.x_coord + (Params.world_width + 1);  }
		if (this.y_coord < 0) { this.y_coord = this.y_coord + (Params.world_height + 1); }
		
		//System.out.println("Critter x coordinate: " + this.x_coord);  //Testing
		//System.out.println("\tCritter y coordinate: " + this.y_coord);
	}
	
	protected final void walk(int direction) {
		if (!(encounter_state)){							// General call
			if (!(CritterWorld.hasCritterMoved(this))) {
				move(1, direction);
				CritterWorld.addMovedCritter(this);
			}
			this.energy -= Params.walk_energy_cost;
			//System.out.println(this.energy);
		}
		else if (encounter_state){							// Called for encounter
				int xTemp = this.x_coord;
				int yTemp = this.y_coord;
				move(1, direction);
				String key = getPositionKey();
				if (positionLog.containsKey(key)){	// If spot is taken put Critter back at original location
					this.x_coord = xTemp;
					this.y_coord = yTemp;
				}
				// Couldn't we just add the key now? Or would that throw an error? ---------
				this.energy -= Params.walk_energy_cost;
		}
	}
	
	protected final void run(int direction) {
		if (!(encounter_state)){
			if (!(CritterWorld.hasCritterMoved(this))) {
				move(2, direction);
				CritterWorld.addMovedCritter(this);
			}
			this.energy -= Params.run_energy_cost;
		}
		else if (encounter_state){
			int xTemp = this.x_coord;
			int yTemp = this.y_coord;
			move(2, direction);
			String key = getPositionKey();
			if (positionLog.containsKey(key)){	// If spot is taken put Critter back at original location
				this.x_coord = xTemp;
				this.y_coord = yTemp;
			}
			// Couldn't we just add the key now? Or would that throw an error? ---------
			this.energy -= Params.run_energy_cost;
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy){ return; }
		//Energy
		//System.out.println("Parent initial energy: " + this.energy);
		offspring.energy = this.energy/2;					// Half of parent's energy rounded down to child
		this.energy = (this.energy/2) + (this.energy%2);	// Half of parent's energy rounded up to parent
		//Placement
		int x_temp = this.x_coord;
		int y_temp = this.y_coord;
		move(1, direction);						//Find birth location
		offspring.x_coord = this.x_coord;		//Set offspring to adjacent location
		offspring.y_coord = this.y_coord;	
		this.x_coord = x_temp;					//Set parent back to normal
		this.y_coord = y_temp;
		//Store to be added later
		CritterWorld.addNewborn(offspring);
		//System.out.println("Parent E: " + this.energy + " Child E: " + offspring.energy);
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
			Class<?> className = Class.forName(myPackage + "." + critter_class_name);
			Critter newCritter = (Critter) className.newInstance();	// Should work; if not a real subclass then will fail in general
			newCritter.energy = Params.start_energy;	// Initialize with start energy
			newCritter.initializePos();
			CritterWorld.addCritter(newCritter);
		} catch (Exception e){		
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
		try{
			//System.out.println("Inside getInstances method");
			List<Critter> result = new java.util.ArrayList<Critter>();	// List of critters of specified class
			Set<Critter> crits = CritterWorld.getCritterList();			// Get our list of critters
			Class<?> className = Class.forName(myPackage + "." + critter_class_name);		// Get class of input
			for (Critter c : crits){
				if (className == c.getClass()) { result.add(c); }		// Check to see if classes are the same; if so, add to list to return
			}
			return result;												// Return list of specified critters
			
		} catch (Exception e){		// If class name is not valid, throw InvalidCritterExcetption
			throw new InvalidCritterException(critter_class_name);
		}
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
	
	
	/*
	 * Method for refreshing new Algae critters in the world after every time step
	 */
	private static void createNewAlgae(){
		try{
			for (int i = 0; i < Params.refresh_algae_count; i += 1){
				makeCritter("Algae");
			}
		} catch (InvalidCritterException e) {
			System.out.println(e.toString());
			return;
		}
	}
	
	private String getPositionKey() {
		int x = x_coord;
		int y = y_coord;
		return (Integer.toString(x) + Integer.toString(y));
	}
	
	private static ArrayList<Critter> getPositionCritterArray (Critter c, Map<String, ArrayList<Critter>> positionLog) {
		String positionKey = c.getPositionKey();
		ArrayList<Critter> positionList = positionLog.get(positionKey);
		return positionList;
	}
	
	private static void removeWorldDead(Set critters) {
		Iterator worldIt = critters.iterator();
		//iterate through world
			//check if dead
			//if dead remove from world
		
		while (worldIt.hasNext()) {
			Critter c = (Critter) worldIt.next();
			//may need to subtract energy from actual critter
			int totalEnergy = c.energy - Params.rest_energy_cost;
			if(totalEnergy <= 0) {
				worldIt.remove();
			}
		}
	}

	private static boolean hasEncounter(ArrayList<Critter> positionCritterArray) {
		if (positionCritterArray.size() > 1) {
			return true;
		}
		return false;
	}
		
	private void winEncounter(Critter loser) {
		energy += loser.energy / 2;
		return;
	}
	
	private boolean ranFromFight(String positionKey) {
		String newKey = getPositionKey();
		if (newKey.equals(positionKey))
			return false;
		return true;
	}
	
	private static void resolveEncounter(ArrayList<Critter> critters) {
		String currentPositionKey = critters.get(0).getPositionKey();
			while (critters.size() > 1) {
				Critter a = critters.get(0);
				Critter b = critters.get(1);
				boolean aFight = a.fight(b.toString());
				boolean bFight = b.fight(a.toString());
				//need to check that neither moved
				boolean aRan = a.ranFromFight(currentPositionKey);
				boolean bRan = b.ranFromFight(currentPositionKey);
				if (!(aRan && bRan)) {
					int aFightRoll = 0;
					int bFightRoll = 0;
					boolean aIsNegative = a.energy < 0;
					boolean bIsNegative = b.energy < 0;
//					if (a.energy < 0) {
//						System.out.println("a energy is negative" + a.energy);
//						aIsNegative = true;
//					}
//					if (b.energy < 0) {
//						System.out.println("b energy is negative" + b.energy);
//						bIsNegative = true;
//					}
					if (aIsNegative == true) {
						a.energy = 0;
						critters.remove(0);
					} else if (bIsNegative == true) {
						b.energy = 0;
						critters.remove(1);
					} else {
						if (aFight)
							aFightRoll = getRandomInt(a.energy);
						if (bFight)
							bFightRoll = getRandomInt(b.energy);
						if ((aFightRoll >= bFightRoll)) {
							a.winEncounter(b);
							b.energy = 0;
							critters.remove(1); 
						} else if (aFightRoll < bFightRoll) {
							b.winEncounter(a);
							a.energy = 0;
							critters.remove(0);
						}
					}
				} else if (aRan && bRan) {
					b.energy = 0;
					critters.remove(1);
					a.energy = 0;
					critters.remove(0);
				}else if (aRan) {
					a.energy = 0;
					critters.remove(0);
				} else if (bRan) {
					b.energy = 0;
					critters.remove(1);
				}
			}
			return;
		}
		
		public static void handleEncounters (Set critters) {
			Iterator worldIt = critters.iterator();
			while (worldIt.hasNext()) {
				Critter c = (Critter) worldIt.next();
				ArrayList<Critter> positionCritterArray = getPositionCritterArray(c, positionLog);
				if (hasEncounter(positionCritterArray) == true) {
					resolveEncounter(positionCritterArray);
				}
			}
		}	
		
		private static Map getPositionLog() {
			return positionLog;
		}
	
		public static void worldTimeStep() {
			Set critters = CritterWorld.getCritterList();		// Create an array from our current list of Critters
			Iterator worldIt = critters.iterator();
			positionLog.clear();
			positionLog = new HashMap<String, ArrayList<Critter>>();
			while(worldIt.hasNext()) {
				Critter c = (Critter) worldIt.next();
				c.doTimeStep();
				String positionKey = c.getPositionKey();
				ArrayList<Critter> positionList = positionLog.get(positionKey);
				if (positionList != null) {
					//add critter to list of critters
					positionList.add(c);
				} else {
					//add critter with hashcode of position
					positionList = new ArrayList<Critter>();
					positionList.add(c);
					positionLog.put(positionKey, new ArrayList<Critter>());
				}
			}
		removeWorldDead(critters);	
			//deal with encounter
		encounter_state = true;
		handleEncounters(critters);
		encounter_state = false;
		removeWorldDead(critters);
		CritterWorld.addNewbornsToPop();		// Add newborns at the end of the time step
		CritterWorld.clearMovedCritters();		// Clear list of critters that have moved
		createNewAlgae();						// Add new algae to the world
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
