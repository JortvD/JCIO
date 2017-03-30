package nl.jortenmilo.keyboard;

import java.awt.AWTException;
import java.awt.Robot;

import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.event.EventManager;

/**
 * This is the KeyboardManager. It contains all the general methods for working with the keyboard.
 */
public class KeyboardManager {
	
	private KeyboardInput input;
	private Robot r;
	
	public KeyboardManager(KeyboardInput input, EventManager events) {
		this.input = input;
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		input.setEventManager(events);
	}
	
	/**
	 * Simulates a key press.
	 * @param key The key to press
	 */
	public void simulateKeyPress(int key) {
		r.keyPress(key);
		r.keyRelease(key);
	}
	
	/**
	 * Simulates a holding a key.
	 * @param key The key to hold
	 */
	public void simulateKeyHold(int key) {
		r.keyPress(key);
	}
	
	/**
	 * Simulates the release of a key.
	 * @param key The key to release.
	 */
	public void simulateKeyRelease(int key) {
		r.keyRelease(key);
	}
	
	/**
	 * Wait until a key is pressed.
	 * @return Returns the key code of the key that was pressed.
	 */
	public int waitUntilTyped() {
		return input.waitUntilTyped();
	}
	
	/**
	 * Wait until on of the specified keys was pressed.
	 * @param keys A list of keys to wait for to be pressed.
	 * @return The key code of the key that was pressed.
	 */
	public int waitUntilTyped(int[] keys) {
		if(keys == null) {
			new NonNullableParameterError("int[]", "keys").print();
			return 0;
		}
		
		return input.waitUntilTyped(keys);
	}
	
	/**
	 * Wait for the specified key to be pressed.
	 * @param key The key
	 */
	public void waitUntilTyped(int key) {
		input.waitUntilTyped(key);
	}
	
	/**
	 * Check if the specified key was pressed.
	 * @param key The key
	 * @return If the key was pressed
	 */
	public boolean isPressed(int key) {
		return input.isPressed(key);
	}
	
}
