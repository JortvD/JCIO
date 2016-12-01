package nl.jortenmilo.event;

public class MouseEvent extends Event {
	
	private int x;
	private int y;
	private int xOnScreen;
	private int yOnScreen;
	private int modifiers;
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXOnScreen() {
		return xOnScreen;
	}

	public void setXOnScreen(int xOnScreen) {
		this.xOnScreen = xOnScreen;
	}

	public int getYOnScreen() {
		return yOnScreen;
	}

	public void setYOnScreen(int yOnScreen) {
		this.yOnScreen = yOnScreen;
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}
	
}
