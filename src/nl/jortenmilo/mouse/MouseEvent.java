package nl.jortenmilo.mouse;

import nl.jortenmilo.event.Event;

/**
 * This is the MouseEvent. It contains all the general information for all the events.
 * @see MouseManager
 */
public abstract class MouseEvent extends Event {
	
	private int x;
	private int y;
	private int xOnScreen;
	private int yOnScreen;
	private int modifiers;
	
	/**
	 * Returns the x-position of the mouse.
	 * @return The x-position
	 */
	public int getX() {
		return x;
	}
	
	protected void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y-position of the mouse.
	 * @return The y-position
	 */
	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns the x-position on the screen.
	 * @return The x-position
	 */
	public int getXOnScreen() {
		return xOnScreen;
	}

	protected void setXOnScreen(int xOnScreen) {
		this.xOnScreen = xOnScreen;
	}
	
	/**
	 * Returns the y-position on the screen.
	 * @return The y-position
	 */
	public int getYOnScreen() {
		return yOnScreen;
	}

	protected void setYOnScreen(int yOnScreen) {
		this.yOnScreen = yOnScreen;
	}
	
	/**
	 * Returns the current modifiers.
	 * @return The modifiers
	 */
	public int getModifiers() {
		return modifiers;
	}

	protected void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}
	
}
