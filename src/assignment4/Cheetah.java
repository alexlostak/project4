package assignment4;

public class Cheetah extends Critter {
	
	@Override
	public String toString() { return "~"; }
	
	
	
	public Cheetah() {
		
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
