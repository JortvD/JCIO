package nl.jortenmilo.error;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.console.Console.ConsoleUser;

public class ClassNotFoundError extends Error {
	
	private String value1;
	private String value2;
	
	public ClassNotFoundError(String value1, String value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	@Override
	public void print() {
		Console.println(ConsoleUser.Error, "ClassNotFound: The class '" + value1 + "' is not found!");
		Console.println(ConsoleUser.Error, "Caused by: " + value2);
		Console.println(ConsoleUser.Error, "If you don't know what to do, please contact us at: goo.gl/1ROGMh.");
	}

}
