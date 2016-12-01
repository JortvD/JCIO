package nl.jortenmilo.event;

import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.input.KeyboardInput;
import nl.jortenmilo.input.MouseInput;

public class EventManager {
	
	private List<EventListener> els = new ArrayList<EventListener>();
	private KeyboardInput ki;
	private MouseInput mi;
	
	public void addEventListener(EventListener e) {
		els.add(e);
		
		if(e instanceof KeyboardEventListener) {
			ki.addEventListener((KeyboardEventListener)e);
		}
		else if(e instanceof MouseEventListener) {
			mi.addEventListener((MouseEventListener)e);
		}
		else if(e instanceof MouseEventListener) {
			Console.addEventListener((WindowEventListener)e);
		}
	}
	
	public List<EventListener> getEventListeners() {
		return els;
	}
	
	public KeyboardInput getKeyboardInput() {
		return ki;
	}

	public void setKeyboardInput(KeyboardInput ki) {
		this.ki = ki;
	}

	public MouseInput getMouseInput() {
		return mi;
	}

	public void setMouseInput(MouseInput mi) {
		this.mi = mi;
	}
	
}
