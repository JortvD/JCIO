package nl.jortenmilo.console;

import java.util.List;

import nl.jortenmilo.console.ConsoleEvent.ConsoleEventListener;
import nl.jortenmilo.plugin.Plugin;

public class ConsoleManager {
	
	public void addListener(ConsoleEventListener listener, Plugin plugin) {
		Console.addListener(listener, plugin);
	}
	
	public List<ConsoleEventListener> getListeners() {
		return Console.getListeners();
	}
	
	public void removeListener(ConsoleEventListener listener) {
		Console.removeListener(listener);
	}
	
	public void removeListeners(Plugin plugin) {
		Console.removeListeners(plugin);
	}
	
	
	
}
