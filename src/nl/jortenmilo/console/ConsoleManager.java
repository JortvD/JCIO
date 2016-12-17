package nl.jortenmilo.console;

public class ConsoleManager {
	
	public void addListener(ConsoleEventListener listener) {
		Console.addEventListener(listener);
	}
	
}
