package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.main.CloseManager;

public class CommandUsedError extends Error {
	
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
			Console.println(ConsoleUser.Error, "   at: " + e.getClassName() + "." + e.getMethodName() + " (Line: " + e.getLineNumber() + " in " + e.getFileName() + ")");
		}
		CloseManager.close();
	}
	
}
