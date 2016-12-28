package nl.jortenmilo.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.InvalidParameterError;
import nl.jortenmilo.mouse.MouseEvent.MouseEventListener;
import nl.jortenmilo.plugin.Plugin;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private List<MouseEventListener> listeners = new ArrayList<MouseEventListener>();
	private HashMap<Plugin, List<MouseEventListener>> plisteners = new HashMap<Plugin, List<MouseEventListener>>();
	
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
		
		for(MouseEventListener mel : listeners) {
			try {
				mel.onMoved(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
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
		
		for(MouseEventListener mel : listeners) {
			try {
				mel.onWheelMoved(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
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
		
		for(MouseEventListener mel : listeners) {
			try {
				mel.onClicked(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
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
		
		for(MouseEventListener mel : listeners) {
			try {
				mel.onPressed(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
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
		
		for(MouseEventListener mel : listeners) {
			try {
				mel.onReleased(event);
			} catch(Error | Exception e2) {
				new nl.jortenmilo.error.UnknownError(e2.getMessage()).print();
			}
		}
	}
	
	protected void addListener(MouseEventListener listener, Plugin plugin) {
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
			return;
		}
		
		listeners.add(listener);
		
		List<MouseEventListener> l = plisteners.get(plugin);
		l.add(listener);
		plisteners.put(plugin, l);
	}
	
	protected List<MouseEventListener> getListeners() {
		return listeners;
	}
	
	protected void removeListener(MouseEventListener listener) {
		listeners.remove(listener);
		
		Plugin plugin = getPlugin(listener);
		List<MouseEventListener> l = plisteners.get(plugin);
		l.remove(listener);
		plisteners.put(plugin, l);
	}
	
	protected void removeListeners(Plugin plugin) {
		for(MouseEventListener listener : plisteners.get(plugin)) {
			listeners.remove(listener);
		}
		plisteners.remove(plugin);
	}
	
	private Plugin getPlugin(MouseEventListener listener) {
		for(Plugin plugin : plisteners.keySet()) {
			for(MouseEventListener c : plisteners.get(plugin)) {
				if(c==listener) return plugin;
			}
		}
		return null;
	}

}
