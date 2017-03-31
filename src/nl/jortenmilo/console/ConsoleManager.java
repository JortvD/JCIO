package nl.jortenmilo.console;

import nl.jortenmilo.close.CloseManager;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.keyboard.KeyboardInput;
import nl.jortenmilo.mouse.MouseInput;
import nl.jortenmilo.settings.SettingsManager;

/**
 * This is the ConsoleManager. It currently does not contain any methods.
 * @see Console
 */
public class ConsoleManager {
	
	public ConsoleManager(SettingsManager settings) {
		Console.setSettingsManager(settings);
		Console.init();
	}
	
	/**
	 * Returns the KeyboardInput class that is used by the console.
	 * @return The KeyboardInput class
	 */
	public KeyboardInput getKeyboardInput() {
		return Console.getKeyboardInput();
	}
	
	/**
	 * Returns the MouseInput class that is used by the console.
	 * @return The MouseInput class
	 */
	public MouseInput getMouseInput() {
		return Console.getMouseInput();
	}
	
	/**
	 * Sets the EventManager for the console. Please don't use it.
	 * @param events The EventManager
	 */
	public void setEventManager(EventManager events) {
		Console.setEventManager(events);
	}
	
	/**
	 * Sets the CloseManager for the console. Please don't use it.
	 * @param close The CloseManager
	 */
	public void setCloseManager(CloseManager close) {
		Console.setCloseManager(close);
	}
	
}
