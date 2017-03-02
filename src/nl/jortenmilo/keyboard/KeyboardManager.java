package nl.jortenmilo.keyboard;

import java.awt.AWTException;
import java.awt.Robot;

import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.event.EventManager;

public class KeyboardManager {
	
	private KeyboardInput input;
	
	public KeyboardManager(KeyboardInput input, EventManager events) {
		this.input = input;
		
		input.setEventManager(events);
	}
	
	public void simulateKey(int key) {
		try {
			Robot r = new Robot();
			r.keyPress(key);
			r.keyRelease(key);
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public int waitUntilTyped() {
		return input.waitUntilTyped();
	}
	
	public int waitUntilTyped(int[] keys) {
		if(keys == null) {
			new NullableParameterError("int[]", "keys").print();
			return 0;
		}
		
		return input.waitUntilTyped(keys);
	}
	
	public void waitUntilTyped(int key) {
		input.waitUntilTyped(key);
	}
	
	public boolean isPressed(int key) {
		return input.isPressed(key);
	}
	
}
