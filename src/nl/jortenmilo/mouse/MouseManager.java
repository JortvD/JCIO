package nl.jortenmilo.mouse;

import java.awt.AWTException;
import java.awt.Robot;

import nl.jortenmilo.event.EventManager;

/**
 * This is the MouseManager. It contains all the general methods to simulate mouse presses.
 */
public class MouseManager {
	
	public MouseManager(MouseInput input, EventManager events) {
		input.setEventManager(events);
	}
	
	/**
	 * Simulates a button press.
	 * @param button The button to press
	 */
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
	
	/**
	 * Simulates the mouse moving.
	 * @param x The x-movement
	 * @param y The y-movement
	 */
	public void simulateMove(int x, int y) {
		try {
			Robot r = new Robot();
			r.mouseMove(x, y);
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Simulates the mousewheel moving.
	 * @param move The amount of mousewheel movement
	 */
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
