package nl.jortenmilo.keyboard;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import nl.jortenmilo.keyboard.KeyboardEvent.KeyboardEventListener;
import nl.jortenmilo.plugin.Plugin;

public class KeyboardManager {
	
	private KeyboardInput input;
	
	public KeyboardManager(KeyboardInput input) {
		this.input = input;
	}
	
	public void addListener(KeyboardEventListener listener, Plugin plugin) {
		input.addListener(listener, plugin);
	}
	
	public List<KeyboardEventListener> getListeners() {
		return input.getListeners();
	}
	
	public void removeListener(KeyboardEventListener listener) {
		input.removeListener(listener);
	}
	
	public void removeListeners(Plugin plugin) {
		input.removeListeners(plugin);
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
