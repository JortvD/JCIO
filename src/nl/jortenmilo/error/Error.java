package nl.jortenmilo.error;

import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.error.ErrorEvent.ErrorEventListener;

public abstract class Error {
	
	private static List<ErrorEventListener> listeners = new ArrayList<ErrorEventListener>();
	
	public abstract void print();
	
	protected static void addListener(ErrorEventListener listener) {
		listeners.add(listener);
	}
	
	protected static List<ErrorEventListener> getListeners() {
		return listeners;
	}
	
}
