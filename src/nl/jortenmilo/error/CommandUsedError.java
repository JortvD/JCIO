package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when a command is created while it already exists.
 * @see Error
 */
public class CommandUsedError extends Error {
	
	private String value;
	
	public CommandUsedError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][CommandUsed][" + value + "]");
		
		Console.println(ConsoleUser.Error, "CommandUsedError: The command '" + value + "' is already used!");
		
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
