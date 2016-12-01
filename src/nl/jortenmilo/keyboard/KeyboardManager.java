package nl.jortenmilo.keyboard;

import java.awt.AWTException;
import java.awt.Robot;

public class KeyboardManager {
	
	private KeyboardInput input;
	
	public KeyboardManager(KeyboardInput input) {
		this.input = input;
	}
	
	public void addListener(KeyboardEventListener listener) {
		input.getListeners().add(listener);
	}
	
	public void simulateKey(int key) {
		try {
			Robot r = new Robot();
			r.keyPress(key);
			r.keyRelease(key);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public int waitUntilTyped() {
		return input.waitUntilTyped();
	}
	
	public int waitUntilTyped(int[] keys) {
		return input.waitUntilTyped(keys);
	}
	
	public void waitUntilTyped(int key) {
		input.waitUntilTyped(key);
	}
	
	public boolean isPressed(int key) {
		return input.isPressed(key);
	}
	
}
