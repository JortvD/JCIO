package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;
import nl.jortenmilo.main.CloseManager;

public class UnknownSettingError extends Error{
	
	private String value;
	
	public UnknownSettingError(String value) {
		this.value = value;
	}
	
	@Override
	public void print() {
		Console.println(ConsoleUser.Error, "UnknownSettingError: Unknown setting '" + value + "'!");
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
