package nl.jortenmilo.console;

import nl.jortenmilo.console.ConsoleEvent.ConsoleEventListener;

public class ConsoleManager {
	
	public void addListener(ConsoleEventListener listener) {
		Console.addEventListener(listener);
	}
	
}
