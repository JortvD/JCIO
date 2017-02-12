package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;

public class MissingObjectError extends Error {
	
	/* This error is thrown when:
	 * A object is missing from a class.
	 */
	
	private String value1;
	private String value2;
	
	public MissingObjectError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.println(ConsoleUser.Error, "MissingObjectError: The class '" + value1 + "' is missing the object '" + value2 + "'!");
		
		StackTraceElement[] es = Thread.currentThread().getStackTrace();
		StackTraceElement[] es2 = new StackTraceElement[es.length-2];
		
		for(int i = 0; i < es2.length; i++) {
			es2[i] = es[i+2];
		}
		
		for(StackTraceElement e : es2) {
			Console.println(ConsoleUser.Error, " at: " + e.getClassName() + "." + e.getMethodName() + " (Line: " + e.getLineNumber() + " in " + e.getFileName() + ")");
		}
		
		Console.println(ConsoleUser.Error, "If you don't know what to do, please contact us at: goo.gl/1ROGMh.");
		
		ErrorThrownEvent event = new ErrorThrownEvent();
		event.setError(this);
		
		for(EventHandler handler : getEventManager().getHandlers(event.getClass())) {
			handler.execute(event);
		}
	}
	
}
