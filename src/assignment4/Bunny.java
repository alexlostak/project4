package assignment4;

public class Bunny extends Critter {
	
	@Override
	public String toString() { return "^"; }
	
	
	
	public Bunny() {
		
	}
	
	public void doTimeStep() {
		
	}



	@Override
	public boolean fight(String opponent) { 
		if (opponent.equals("Algae")) { return true; }
		else { return false; }
	}

}
