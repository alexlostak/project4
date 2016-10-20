package assignment4;

public class Cheetah extends Critter {
	
	@Override
	public String toString() { return "~"; }
	
	
	
	public Cheetah() {
		
	}
	
	@Override
	public void doTimeStep() {
		run(6);
	}



	@Override
	public boolean fight(String not_used) { return true; }
	
}
