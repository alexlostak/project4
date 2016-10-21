package assignment4;

public class Critter2 extends Critter {
	
	@Override
	public String toString() { return "2"; }
	
	
	
	public Critter2() {
		
	}
	
	@Override
	public void doTimeStep() {
		walk(6);
		walk(6);
	}



	@Override
	public boolean fight(String not_used) { 
		run(2);
		return true; 
	}
	
}
