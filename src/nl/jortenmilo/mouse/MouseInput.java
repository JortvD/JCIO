package nl.jortenmilo.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private EventManager events;
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseMovedEvent event = new MouseMovedEvent();
		event.setModifiers(e.getModifiers());
		event.setX(e.getX());
		event.setY(e.getY());
		event.setXOnScreen(e.getXOnScreen());
		event.setYOnScreen(e.getYOnScreen());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseWheelMovedEvent event = new MouseWheelMovedEvent();
		event.setModifiers(e.getModifiers());
		event.setX(e.getX());
		event.setY(e.getY());
		event.setXOnScreen(e.getXOnScreen());
		event.setYOnScreen(e.getYOnScreen());
		event.setWheelRotation(e.getWheelRotation());
		event.setPresiceWheelRotation(e.getPreciseWheelRotation());
		event.setScrollAmount(e.getScrollAmount());
		event.setScrollType(e.getScrollType());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MouseClickedEvent event = new MouseClickedEvent();
		event.setModifiers(e.getModifiers());
		event.setX(e.getX());
		event.setY(e.getY());
		event.setXOnScreen(e.getXOnScreen());
		event.setYOnScreen(e.getYOnScreen());
		event.setButton(e.getButton());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		MousePressedEvent event = new MousePressedEvent();
		event.setModifiers(e.getModifiers());
		event.setX(e.getX());
		event.setY(e.getY());
		event.setXOnScreen(e.getXOnScreen());
		event.setYOnScreen(e.getYOnScreen());
		event.setButton(e.getButton());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MouseReleasedEvent event = new MouseReleasedEvent();
		event.setModifiers(e.getModifiers());
		event.setX(e.getX());
		event.setY(e.getY());
		event.setXOnScreen(e.getXOnScreen());
		event.setYOnScreen(e.getYOnScreen());
		event.setButton(e.getButton());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	protected void setEventManager(EventManager events) {
		this.events = events;
	}

}
