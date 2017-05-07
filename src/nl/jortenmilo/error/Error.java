package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;

/**
 * This is the abstract method for an error. All errors instantiate this class.
 */
public abstract class Error {
	
	private static EventManager events;
	
	/**
	 * With this method you print everything about the error.
	 */
	public abstract void print();
	
	protected void event() {
		ErrorThrownEvent event = new ErrorThrownEvent();
		event.setError(this);
		
		for(EventHandler handler : getEventManager().getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
	public void printHelp() {
		Console.println(ConsoleUser.Error, "If you don't know what to do, please contact us at: goo.gl/1ROGMh.");
	}
	
	public void printError(String error, String cause) {
		Console.println(ConsoleUser.Error, error + ": " + cause);
	}
	
	public void printStackTrace() {
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement[] es2 = new StackTraceElement[es.length-2];
		
		for(int i = 0; i < es2.length; i++) {
			es2[i] = es[i+2];
		}
		
		for(StackTraceElement e : es2) {
			Console.println(ConsoleUser.Error, " at: " + e.getClassName() + "." + e.getMethodName() + " (Line: " + e.getLineNumber() + " in " + e.getFileName() + ")");
		}
	}

	protected static EventManager getEventManager() {
		return events;
	}

	protected static void setEventManager(EventManager events) {
		Error.events = events;
	}
	
}
