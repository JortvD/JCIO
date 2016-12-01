package nl.jortenmilo.mouse;

import java.awt.AWTException;
import java.awt.Robot;

public class MouseManager {
	
	private MouseInput mouse;
	
	public MouseManager(MouseInput mouse) {
		this.mouse = mouse;
	}
	
	public void addListener(MouseEventListener listener) {
		mouse.addEventListener(listener);
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
