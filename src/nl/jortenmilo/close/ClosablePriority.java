package nl.jortenmilo.close;

/**
 * This enum contains all the possible priorities for a Closable.
 * The order of the priorities is:
 * - LAUNCHER
 * - HIGH
 * - MEDIUM
 * - LOW
 * The Closables with a higher priority will be called after the Closables with a lower priority.
 * @see Closable
 */
public enum ClosablePriority {
	
	LAUNCHER,
	HIGH,
	MEDIUM,
	LOW
	
}
