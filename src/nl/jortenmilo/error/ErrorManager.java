package nl.jortenmilo.error;

import java.util.List;

import nl.jortenmilo.error.ErrorEvent.ErrorEventListener;

public class ErrorManager {
	
	public void addListener(ErrorEventListener listener) {
		Error.addListener(listener);
	}

	public List<ErrorEventListener> getListeners() {
		return Error.getListeners();
	}
	
}
