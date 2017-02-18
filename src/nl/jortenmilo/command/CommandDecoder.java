package nl.jortenmilo.command;

/**
 * The CommandDecorder contains some methods to get the parameters from a String.
 */
public class CommandDecoder {
	
	/**
	 * This method parses a String into a list of Strings. It sees a space as the beginning of a new String.
	 * @param s The string you want to parse.
	 * @return The outcome of the parse
	 */
	public static String[] getParameters(String s) {
		String[] params = null;
		int amount = 1;
		
		s = removeLastSpaces(s);
		
		byte[] bytes = s.getBytes();
		
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
				param += s.substring(i, i+1);
			}
		}
		
		params[n] = param;
		
		return params;
	}

	private static String removeLastSpaces(String s) {
		if(s.endsWith(" ") && s.length() > 0) {
			s = s.substring(0, s.length()-1);
			
			removeLastSpaces(s);
		}
		
		return s;
	}
	
}
