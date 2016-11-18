package nl.jortenmilo.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.event.MouseClickedEvent;
import nl.jortenmilo.event.MouseEventListener;
import nl.jortenmilo.event.MouseMovedEvent;
import nl.jortenmilo.event.MousePressedEvent;
import nl.jortenmilo.event.MouseReleasedEvent;
import nl.jortenmilo.event.MouseWheelMovedEvent;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private List<MouseEventListener> mels = new ArrayList<MouseEventListener>();
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MouseEventListener mel : mels) {
			MouseMovedEvent event = new MouseMovedEvent();
			event.setModifiers(e.getModifiers());
			event.setX(e.getX());
			event.setY(e.getY());
			event.setXOnScreen(e.getXOnScreen());
			event.setYOnScreen(e.getYOnScreen());
			mel.onMoved(event);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for(MouseEventListener mel : mels) {
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
			mel.onWheelMoved(event);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(MouseEventListener mel : mels) {
			MouseClickedEvent event = new MouseClickedEvent();
			event.setModifiers(e.getModifiers());
			event.setX(e.getX());
			event.setY(e.getY());
			event.setXOnScreen(e.getXOnScreen());
			event.setYOnScreen(e.getYOnScreen());
			event.setButton(e.getButton());
			mel.onClicked(event);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MouseEventListener mel : mels) {
			MousePressedEvent event = new MousePressedEvent();
			event.setModifiers(e.getModifiers());
			event.setX(e.getX());
			event.setY(e.getY());
			event.setXOnScreen(e.getXOnScreen());
			event.setYOnScreen(e.getYOnScreen());
			event.setButton(e.getButton());
			mel.onPressed(event);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MouseEventListener mel : mels) {
			MouseReleasedEvent event = new MouseReleasedEvent();
			event.setModifiers(e.getModifiers());
			event.setX(e.getX());
			event.setY(e.getY());
			event.setXOnScreen(e.getXOnScreen());
			event.setYOnScreen(e.getYOnScreen());
			event.setButton(e.getButton());
			mel.onReleased(event);
		}
	}
	
	public void addEventListener(MouseEventListener e) {
		mels.add(e);
	}

}
