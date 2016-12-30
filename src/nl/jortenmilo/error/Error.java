package nl.jortenmilo.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.ErrorEvent.ErrorEventListener;
import nl.jortenmilo.plugin.Plugin;

public abstract class Error {
	
	private static List<ErrorEventListener> listeners = new ArrayList<ErrorEventListener>();
	private static HashMap<Plugin, List<ErrorEventListener>> plisteners = new HashMap<Plugin, List<ErrorEventListener>>();
	
	public abstract void print();
	
	protected static void addListener(ErrorEventListener listener, Plugin plugin) {
		if(plugin == null) {
			//Throw an error when the plugin is null.
			new InvalidParameterError(plugin + "").print();
			return;
		}
		
		listeners.add(listener);
		
		if(plisteners.get(plugin)==null) plisteners.put(plugin, new ArrayList<ErrorEventListener>());
		
		List<ErrorEventListener> l = plisteners.get(plugin);
		l.add(listener);
		plisteners.put(plugin, l);
	}
	
	protected static List<ErrorEventListener> getListeners() {
		return listeners;
	}
	
	protected static void removeListener(ErrorEventListener listener) {
		listeners.remove(listener);
		
		Plugin plugin = getPlugin(listener);
		
		if(plugin == null) return;
		if(plisteners.get(plugin)==null) plisteners.put(plugin, new ArrayList<ErrorEventListener>());
		
		List<ErrorEventListener> l = plisteners.get(plugin);
		l.remove(listener);
		plisteners.put(plugin, l);
	}
	
	protected static void removeListeners(Plugin plugin) {
		if(!plisteners.containsKey(plugin)) {
			return;
		}
		
		for(ErrorEventListener listener : plisteners.get(plugin)) {
			listeners.remove(listener);
		}
		
		plisteners.remove(plugin);
	}
	
	private static Plugin getPlugin(ErrorEventListener listener) {
		for(Plugin plugin : plisteners.keySet()) {
			for(ErrorEventListener c : plisteners.get(plugin)) {
				if(c == listener) return plugin;
			}
		}
		return null;
	}
	
}
