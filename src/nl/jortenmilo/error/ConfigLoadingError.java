package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.ConsoleUser;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This error is thrown when there was an error while loading a config.
 * @see Error
 */
public class ConfigLoadingError extends Error {
	
	private String value1; //File
	private String value2; //Line
	private String value3; //Col
	private String value4; //Error
	
	public ConfigLoadingError(String value1, String value2, String value3, String value4) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
		this.value4 = value4;
	}
	
	@Override
	public void print() {
		Console.debug("ERROR [" + new SystemUtils().getTime() + "][ConfigLoading][" + value1 + ", " + value2 + ", " + value3 + ", " + value4 + "]");
		
		Console.println(ConsoleUser.Error, "ConfigLoadingError: " + value4 + "!");
		Console.println(ConsoleUser.Error, " in the file: " + value1);
		Console.println(ConsoleUser.Error, " at the line: " + value2);
		Console.println(ConsoleUser.Error, " at the col: " + value3);
		
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
