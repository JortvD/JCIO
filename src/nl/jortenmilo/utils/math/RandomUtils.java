package nl.jortenmilo.utils.math;

public class RandomUtils {
	
	public int randomInt(int max) {
		return (int)(Math.random() * max);
	}
	
	public int randomInt(int min, int max) {
		return (int)(Math.random() * (max - min)) + min;
	}
	
	public double randomDouble() {
		return Math.random();
	}
	
	public double randomDouble(double max) {
		return Math.random() * max;
	}
	
	public double randomDouble(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}
	
	
	
}
