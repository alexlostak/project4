package assignment4;
import java.util.*;

public class CritterWorld {			// HOW DO I MAKE THIS WORK??????????????
	
	private static Set<Critter> myCritters = new HashSet<Critter>();
	private static int population = 0;
	public static Map<String, ArrayList<Critter>> positionLog = new HashMap<String, ArrayList<Critter>>();

	
//	public static void makeCritterWorld() {
//		myCritters = new HashSet<Critter>();
//		population = 0;
//	}
	//Adds critter to set
	public static void addCritter(Critter c){
		myCritters.add(c);				//add critter					
		population += 1;				//increase tag count
	}
	
	public static void removeCritter(Critter c){
		myCritters.remove(c);
		population -= 1;
	}
	
	public static Set getCritterList(){
		return myCritters;
	}

	public static Map getPositionLog() {
		return positionLog;
	}
	
}
