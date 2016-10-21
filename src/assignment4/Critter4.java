package assignment4;
/*
 * This Critter is the Peaceful Wanderer. A man among beasts his only duty is to wander.
 * He continues straight in one direction, never fighting, only eating the Algae he comes across.
 * Shrubberies Acquired will show how many Algae the Peaceful Wanderer has eaten. 
 * */
public class Critter4 extends Critter{
	private int shrubberiesAcquired = 0;
	@Override
	public String toString() { return "4"; }
	
	
	
	public Critter4() {
		
	}
	
	public void doTimeStep() {
		walk(1);
	}

	@Override
	public boolean fight(String opponent) { 
		if (opponent.equals("@")) { 
			shrubberiesAcquired++;
			return true; 
			}
		else { return false; }
	}
	
	public static void runStats(java.util.List<Critter> wanderers) {
		int totalShrubberiesAcquired = 0;
		for (Critter w : wanderers) {
			Critter4 realW = (Critter4) w;
			totalShrubberiesAcquired += realW.shrubberiesAcquired;
		}
		System.out.println(wanderers.size() + " Peaceful Wanderers are wandering and have acquired " + totalShrubberiesAcquired + " shrubberies");
	}
}
