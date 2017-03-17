package nl.jortenmilo.keyboard;

import java.awt.AWTException;
import java.awt.Robot;

import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.event.EventManager;

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
	
	public void simulateKeyPress(int key) {
		r.keyPress(key);
		r.keyRelease(key);
	}
	
	public void simulateKeyHold(int key) {
		r.keyPress(key);
	}
	
	public void simulateKeyRelease(int key) {
		r.keyRelease(key);
	}
	
	public int waitUntilTyped() {
		return input.waitUntilTyped();
	}
	
	public int waitUntilTyped(int[] keys) {
		if(keys == null) {
			new NonNullableParameterError("int[]", "keys").print();
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
