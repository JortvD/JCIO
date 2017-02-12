package nl.jortenmilo.error;

import nl.jortenmilo.event.Event;

public abstract class ErrorEvent extends Event {
	
	private Error error;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
