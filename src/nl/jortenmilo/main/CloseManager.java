package nl.jortenmilo.main;

public class CloseManager {
	
	private static Launcher l;
	
	public static void setLauncher(Launcher l) {
		CloseManager.l = l;
	}
	
	public static void close() {
		l.close();
	}
	
}
