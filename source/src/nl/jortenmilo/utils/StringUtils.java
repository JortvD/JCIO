package nl.jortenmilo.utils;

public class StringUtils {
	
	public static String addChars(String string, char c, int amount) {
		for(int x = 0; x < amount; x++) {
			string += " ";
		}
		return string;
	}
	
}
