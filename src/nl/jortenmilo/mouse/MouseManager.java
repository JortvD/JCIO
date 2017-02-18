package nl.jortenmilo.mouse;

import java.awt.AWTException;
import java.awt.Robot;

import nl.jortenmilo.event.EventManager;

public class MouseManager {
	
	public MouseManager(MouseInput input, EventManager events) {
		input.setEventManager(events);
	}
	
	public void simulateButton(int button) {
		try {
			Robot r = new Robot();
			r.mousePress(button);
			r.mouseRelease(button);
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void simulateMove(int x, int y) {
		try {
			Robot r = new Robot();
			r.mouseMove(x, y);
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void simulateWheelMove(int move) {
		try {
			Robot r = new Robot();
			r.mouseWheel(move);
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
}
