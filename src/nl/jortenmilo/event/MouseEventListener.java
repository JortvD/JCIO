package nl.jortenmilo.event;

public interface MouseEventListener extends EventListener{

	public void onMoved(MouseMovedEvent e);
	
	public void onClicked(MouseClickedEvent e);
	
	public void onPressed(MousePressedEvent e);
	
	public void onReleased(MouseReleasedEvent e);
	
	public void onDragged(MouseDraggedEvent e);
	
	public void onWheelMoved(MouseWheelMovedEvent e);
	
	
	
}
