package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;

public class CommandUsedError extends Error {
	
	/* This error is thrown when:
	 * A command is created with an command that already exists.
	 */
	
	private String value;
	
	public CommandUsedError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
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
	}
	
}
