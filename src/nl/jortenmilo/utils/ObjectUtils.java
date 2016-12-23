package nl.jortenmilo.utils;

import java.awt.Color;

import nl.jortenmilo.error.ParsingError;

public class ObjectUtils {
	
	public Color StringToColor(String s) {
		try {
			int r = Integer.parseInt(s.substring(0, s.indexOf("_")));
			int g = Integer.parseInt(s.substring(s.indexOf("_")+1, s.lastIndexOf("_")));
			int b = Integer.parseInt(s.substring(s.lastIndexOf("_")+1, s.length()));
			return new Color(r, g, b);
		} catch(NumberFormatException e) {
			new ParsingError("String", s, "Color").print();
			return null;
		}
		
	}
	
	public int StringToInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch(NumberFormatException e) {
			new ParsingError("String", s, "Integer").print();
			return 0;
		}
		
	}
	
	public String getData() {
		return "";
	}

}
