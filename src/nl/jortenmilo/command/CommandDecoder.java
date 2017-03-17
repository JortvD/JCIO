package nl.jortenmilo.command;

import nl.jortenmilo.error.NonNullableParameterError;

/**
 * The CommandDecorder contains some methods to get the parameters from a String.
 */
public class CommandDecoder {
	
	/**
	 * This method parses a String into a list of Strings. It sees a space as the beginning of a new String.
	 * @param s The string you want to parse.
	 * @return The outcome of the parse
	 */
	public static String[] getParameters(String command) {
		if(command == null) {
			new NonNullableParameterError("String", "command").print();
			return null;
		}
		
		String[] params = null;
		int amount = 1;
		
		command = removeLastSpaces(command);
		
		byte[] bytes = command.getBytes();
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 32) {
				amount++;
			}
		}
		
		params = new String[amount];
		String param = "";
		int n = 0;
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 32) {
				params[n] = param;
				param = "";
				n++;
			} 
			else {
				param += command.substring(i, i+1);
			}
		}
		
		params[n] = param;
		
		return params;
	}

	private static String removeLastSpaces(String string) {
		if(string.endsWith(" ") && string.length() > 0) {
			string = string.substring(0, string.length()-1);
			
			removeLastSpaces(string);
		}
		
		return string;
	}
	
}
