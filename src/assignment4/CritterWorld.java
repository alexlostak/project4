package assignment4;
import java.util.*;

public class CritterWorld {			// HOW DO I MAKE THIS WORK??????????????
	
	private static Set<Critter> myCritters;
	private static int population;
	
	
	public static void makeCritterWorld() {
		myCritters = new HashSet<Critter>();
		population = 0;
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

	/*public ArrayList<Critter> findEncounters(){
		
		for(int x = 0; x < Params.world_width; x += 1){
			for(int y = 0; y < Params.world_height; y += 1){
				Iterator<Critter> myIt = myCritters.iterator();
				while(myIt.hasNext()){
					Critter thisCritter = myIt.next();
					int thisX = thisCritter.getX_coord();
					
					//if(c.x_coord == x && c.y_coord){
						
					//}
				}
			}
		}
		
		
		
		
		return null;
	}*/
	
}
