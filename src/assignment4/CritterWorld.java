package assignment4;
import java.util.*;

public class CritterWorld {			// HOW DO I MAKE THIS WORK??????????????
	
	private static Set<Critter> myCritters;
	private static int population;
	public static Map<String, ArrayList<Critter>> positionLog = new HashMap<String, ArrayList<Critter>>();

	
	public static void makeCritterWorld() {
		myCritters = new HashSet<Critter>();
		population = 0;
		stageOneInit();
		
	}
	private static void stageOneInit() {
		//add 100 Algae
		//add 25 Craig
		try {
			for (int i = 0; i < 100; i++) {
				Critter.makeCritter("assignment4.Algae");
			}
		} catch (InvalidCritterException e) {
			System.out.println(e.toString());
			return;
		}
		try {
			for (int i = 0; i < 25; i++) {
				Critter.makeCritter("assignment4.Craig");
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
	
	public static Set getCritterList(){
		return myCritters;
	}

	public static Map getPositionLog() {
		return positionLog;
	}
	
}
