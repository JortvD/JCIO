package nl.jortenmilo.utils;

public class StringUtils {
	
	public String addChars(String string, char c, int amount) {
		for(int x = 0; x < amount; x++) {
			string += c + "";
		}
		
		return string;
	}

	public String getData() {
		return "";
	}
	
}
