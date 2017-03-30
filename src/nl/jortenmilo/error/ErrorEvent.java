package nl.jortenmilo.error;

import nl.jortenmilo.event.Event;

/**
 * This is the general ErrorEvent. All ErrorEvents instantiate this class since it contains the general information about a ErrorEvent.
 * @see Error
 */
public abstract class ErrorEvent extends Event {
	
	private Error error;
	
	/**
	 * Returns the Error that was used in this event.
	 * @return The error
	 * @see Error
	 */
	public Error getError() {
		return error;
	}

	protected void setError(Error error) {
		this.error = error;
	}
}
