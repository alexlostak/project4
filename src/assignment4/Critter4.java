package assignment4;

public class Critter4 extends Critter{

	@Override
	public String toString() { return "4"; }
	
	
	
	public Critter4() {
		
	}
	
	public void doTimeStep() {
		
	}



	@Override
	public boolean fight(String opponent) { 
		if (opponent.equals("Algae")) { return true; }
		else { return false; }
	}
	
}
