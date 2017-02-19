package nl.jortenmilo.utils;

public class StringUtils extends Utils {
	
	@Override
	public void create() {
		
	}

	@Override
	public Utils clone() {
		StringUtils clone = new StringUtils();
		clone.create();
		
		return clone;
	}
	
	public String addChars(String string, char c, int amount) {
		for(int x = 0; x < amount; x++) {
			string += c + "";
		}
		
		return string;
	}

	@Override
	public String getData() {
		return "";
	}

	@Override
	public String getName() {
		return "StringUtils";
	}
	
}
