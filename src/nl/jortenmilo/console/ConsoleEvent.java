package nl.jortenmilo.console;

import nl.jortenmilo.event.Event;

/**
 * This is the general class for all the ConsoleEvents. It contains some general information for every event.
 * @see Console
 */
public abstract class ConsoleEvent extends Event {
	
	private int width = 0;
	private int height = 0;
	private int x = 0;
	private int y = 0;
	
	/**
	 * Returns the width of the Console.
	 * @return The width
	 */
	public int getWidth() {
		return width;
	}
	
	protected void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Returns the height of the Console.
	 * @return The height
	 */
	public int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Returns the x-position of the Console.
	 * @return The x-position
	 */
	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns the y-positions of the Console.
	 * @return The y-position
	 */
	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}
	
}
