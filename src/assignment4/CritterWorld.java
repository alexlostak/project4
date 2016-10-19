package assignment4;
import java.util.*;

public class CritterWorld {			
	
	private static Set<Critter> myCritters;
	private static Set<Critter> newbornCritters;
	private static Set<Critter> movedCritters;
	private static int population;
	private static int newborns;

	
	public static void makeCritterWorld() {
		myCritters = new HashSet<Critter>();
		population = 0;
		newbornCritters = new HashSet<Critter>();
		newborns = 0;
		movedCritters = new HashSet<Critter>();
		//stageOneInit();
		
	}
	private static void stageOneInit() {
		//add 100 Algae
		//add 25 Craig
		try {
			for (int i = 0; i < 100; i++) {
				Critter.makeCritter("Algae");
			}
		} catch (InvalidCritterException e) {
			System.out.println(e.toString());
			return;
		}
		try {
			for (int i = 0; i < 25; i++) {
				Critter.makeCritter("Craig");
			}
		} catch (InvalidCritterException e) {
			System.out.println(e.toString());
			return;
		}
	}
	//Adds critter to set
	public static void addCritter(Critter c){
		myCritters.add(c);				//add critter					
		population += 1;				//increase tag count
	}
	
	public static void removeCritter(Critter c){
		myCritters.remove(c);
		population -= 1;
	}
	
	public static Set<Critter> getCritterList(){
		return myCritters;
	}
	
	public static void addNewborn(Critter c){
		newbornCritters.add(c);						//Add newborn to temporary list
		newborns += 1;
	}
	
	public static void addNewbornsToPop(){
		myCritters.addAll(newbornCritters);			//Time to add newborns to pop
		newbornCritters.clear(); //Clear newborn list
		population += newborns;						//Add newborns to population total
		newborns = 0;								//Clear number of newborns
		//System.out.println("Current population: " + population); //Population count check
	}
	
	public static void addMovedCritter(Critter c){
		movedCritters.add(c);
	}
	
	public static void clearMovedCritters(){
		movedCritters.clear();
	}
	
	public static boolean hasCritterMoved(Critter c){
		return movedCritters.contains(c);
	}
}
