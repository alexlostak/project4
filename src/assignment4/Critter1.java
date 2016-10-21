package assignment4;

public class Critter1 extends Critter {
	
	@Override
	public String toString() { return "1"; }
	
	
	
	public Critter1() {
		
	}
	
	public void doTimeStep() {
		
	}



	@Override
	public boolean fight(String opponent) { 
		if (opponent.equals("Algae")) { return true; }
		else { return false; }
	}

}
