package assignment4;
import java.util.*;

public class CritterWorld {
	
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

	public ArrayList<Critter> findEncounters(){
		
		for(int x = 0; x < Params.world_width; x += 1){
			for(int y = 0; y < Params.world_height; y += 1){
				Iterator<Critter> myIt = myCritters.iterator();
				while(myIt.hasNext()){
					Critter c = myIt.next();
					//c.get			//GETTERS & SETTERS
					//if(c.x_coord == x && c.y_coord){
						
					//}
				}
			}
		}
		
		
		
		
		return null;
	}
	
}
