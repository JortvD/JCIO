package nl.jortenmilo.console;

public class ConsoleEvent {
	
	private int width = 0;
	private int height = 0;
	private int x = 0;
	private int y = 0;
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

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
	
	public interface ConsoleEventListener {
		public void onConsoleResized(ConsoleResizedEvent e);
		public void onConsoleMoved(ConsoleMovedEvent e);
		public void onConsoleHidden(ConsoleHiddenEvent e);
		public void onConsoleShown(ConsoleShownEvent e);
		public void onConsoleOpened(ConsoleOpenedEvent e);
		public void onConsoleClosed(ConsoleClosedEvent e);
		public void onConsoleCleared(ConsoleClearedEvent e);
	}
	
}
