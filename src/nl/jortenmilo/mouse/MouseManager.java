package nl.jortenmilo.mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import nl.jortenmilo.mouse.MouseEvent.MouseEventListener;
import nl.jortenmilo.plugin.Plugin;

public class MouseManager {
	
	private MouseInput input;
	
	public MouseManager(MouseInput input) {
		this.input = input;
	}
	
	public void addListener(MouseEventListener listener, Plugin plugin) {
		input.addListener(listener, plugin);
	}
	
	public List<MouseEventListener> getListeners() {
		return input.getListeners();
	}
	
	public void removeListener(MouseEventListener listener) {
		input.removeListener(listener);
	}
	
	public void removeListeners(Plugin plugin) {
		input.removeListeners(plugin);
	}
	
	public void simulateButton(int button) {
		try {
			Robot r = new Robot();
			r.mousePress(button);
			r.mouseRelease(button);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void simulateMove(int x, int y) {
		try {
			Robot r = new Robot();
			r.mouseMove(x, y);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void simulateWheelMove(int move) {
		try {
			Robot r = new Robot();
			r.mouseWheel(move);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
}
