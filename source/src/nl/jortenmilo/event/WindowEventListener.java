package nl.jortenmilo.event;

public interface WindowEventListener {
	
	public void onResized(WindowResizedEvent e);
	
	public void onMoved(WindowMovedEvent e);
	
	public void onHidden(WindowHiddenEvent e);
	
	public void onShown(WindowShownEvent e);
	
	public void onOpened(WindowOpenedEvent e);
	
	public void onClosed(WindowClosedEvent e);
	
}
