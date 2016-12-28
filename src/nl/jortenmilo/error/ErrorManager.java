package nl.jortenmilo.error;

import java.util.List;

import nl.jortenmilo.error.ErrorEvent.ErrorEventListener;
import nl.jortenmilo.plugin.Plugin;

public class ErrorManager {
	
	public void addListener(ErrorEventListener listener, Plugin plugin) {
		Error.addListener(listener, plugin);
	}

	public List<ErrorEventListener> getListeners() {
		return Error.getListeners();
	}
	
	public void removeListener(ErrorEventListener listener) {
		Error.removeListener(listener);
	}
	
	public void removeListeners(Plugin plugin) {
		Error.removeListeners(plugin);
	}
	
}
