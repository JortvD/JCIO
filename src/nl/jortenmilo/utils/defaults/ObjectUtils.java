package nl.jortenmilo.utils.defaults;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.jortenmilo.error.ParsingError;
import nl.jortenmilo.utils.Utils;

public class ObjectUtils extends Utils {
	
	@Override
	public void create() {
		
	}

	@Override
	public Utils clone() {
		ObjectUtils clone = new ObjectUtils();
		clone.create();
		
		return clone;
	}
	
	/***** CODE > OBJECT *****/
	public String codeToString(String s) {
		return s.replaceAll("_", "");
	}
	
	public Color codeToColor(String s) {
		try {
			byte[] bytes = s.getBytes();
			int length = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 95) {
					length++;
				}
			}
			
			if(length == 1) {
				int r = Integer.parseInt(s);
				int g = Integer.parseInt(s);
				int b = Integer.parseInt(s);
				
				return new Color(r, g, b);
			}
			else if(length == 3) {
				int r = Integer.parseInt(s.substring(0, s.indexOf("_")));
				int g = Integer.parseInt(s.substring(s.indexOf("_") + 1, s.lastIndexOf("_")));
				int b = Integer.parseInt(s.substring(s.lastIndexOf("_") + 1, s.length()));
				
				return new Color(r, g, b);
			}
			else if(length == 4) {
				int r = Integer.parseInt(s.substring(0, s.indexOf("_")));
				int g = Integer.parseInt(s.substring(s.indexOf("_") + 1, s.indexOf("_", 1)));
				int b = Integer.parseInt(s.substring(s.indexOf("_", 1) + 1, s.lastIndexOf("_")));
				int a = Integer.parseInt(s.substring(s.lastIndexOf("_") + 1, s.length()));
				
				return new Color(r, g, b, a);
			}
			else {
				new ParsingError("String", s, "Color").print();
				return null;
			}
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Color").print();
			
			return null;
		}
	}
	
	public int codeToInteger(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Integer").print();
			
			return 0;
		}
	}
	
	public long codeToLong(String s) {
		try {
			return Long.parseLong(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Long").print();
			
			return 0;
		}
	}
	
	public double codeToDouble(String s) {
		try {
			return Double.parseDouble(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Double").print();
			
			return 0;
		}
	}
	
	public float codeToFloat(String s) {
		try {
			return Float.parseFloat(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Float").print();
			
			return 0;
		}
	}
	
	public boolean codeToBoolean(String s) {
		if(s.equalsIgnoreCase("true")) {
			return true;
		}
		else if(s.equalsIgnoreCase("false")) {
			return false;
		}
		else {
			new ParsingError("String", s, "Boolean").print();
			
			return false;
		}
	}
	
	public List<String> codeToList(String s) {
		List<String> list = new ArrayList<String>();
		
		s = s.substring(1, s.length()-1);
		
		byte[] bytes = s.getBytes();
		int splitter = 0;
		
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] == 44) {
				list.add(s.substring(splitter + 1, i-1));
				splitter = i;
			}
			else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
				splitter = i;
			}
		}
		
		list.add(s.substring(splitter + 1, s.length()));
		
		return list;
	}
	
	public List<String> codeToStringList(String s) {
		return codeToList(s);
	}
	
	public List<Integer> codeToIntegerList(String s) {
		try {
			List<Integer> list = new ArrayList<Integer>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Integer.parseInt(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Integer.parseInt(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Integer>").print();
			
			return null;
		}
	}
	
	public List<Long> codeToLongList(String s) {
		try {
			List<Long> list = new ArrayList<Long>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Long.parseLong(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Long.parseLong(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Long>").print();
			
			return null;
		}
	}
	
	public List<Float> codeToFloatList(String s) {
		try {
			List<Float> list = new ArrayList<Float>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Float.parseFloat(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Float.parseFloat(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Float>").print();
			
			return null;
		}
	}
	
	public List<Double> codeToDoubleList(String s) {
		try {
			List<Double> list = new ArrayList<Double>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Double.parseDouble(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Double.parseDouble(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Double>").print();
			
			return null;
		}
	}
	
	public List<Byte> codeToByteList(String s) {
		try {
			List<Byte> list = new ArrayList<Byte>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Byte.parseByte(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Byte.parseByte(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Byte>").print();
			
			return null;
		}
	}
	
	public List<Short> codeToShortList(String s) {
		try {
			List<Short> list = new ArrayList<Short>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(Short.parseShort(s.substring(splitter + 1, i-1)));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(Short.parseShort(s.substring(splitter + 1, s.length())));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Short>").print();
			
			return null;
		}
	}
	
	public List<Boolean> codeToBooleanList(String s) {
		try {
			List<Boolean> list = new ArrayList<Boolean>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					String b = s.substring(splitter + 1, i-1);
					list.add(codeToBoolean(b));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			String b = s.substring(splitter + 1, s.length());
			list.add(codeToBoolean(b));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Boolean>").print();
			
			return null;
		}
	}
	
	public List<Character> codeToCharacterList(String s) {
		try {
			List<Character> list = new ArrayList<Character>();
			
			s = s.substring(1, s.length()-1);
			
			byte[] bytes = s.getBytes();
			int splitter = 0;
			
			for(int i = 0; i < bytes.length; i++) {
				if(bytes[i] == 44) {
					list.add(s.charAt(splitter + 1));
					splitter = i;
				}
				else if(bytes[i] == (byte)' ' && i == (splitter - 1)) {
					splitter = i;
				}
			}
			
			list.add(s.charAt(splitter + 1));
			
			return list;
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "List<Character>").print();
			
			return null;
		}
	}
	
	public short codeToShort(String s) {
		try {
			return Short.parseShort(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Short").print();
			
			return 0;
		}
	}
	
	public byte codeToByte(String s) {
		try {
			return Byte.parseByte(s);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Byte").print();
			
			return 0;
		}
	}
	
	public char codeToChar(String s) {
		return s.charAt(0);
	}
	
	public File codeToFile(String s) {
		return new File(s);
	}
	
	public Font codeToFont(String s) {
		try {
			String name = s.substring(0, s.indexOf("_"));
			int style = Integer.parseInt(s.substring(s.indexOf("_") + 1, s.lastIndexOf("_")));
			int size = Integer.parseInt(s.substring(s.lastIndexOf("_") + 1, s.length()));
			
			return new Font(name, style, size);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Font").print();
			
			return null;
		}
	}
	
	public Date codeToDate(String s) {
		try {
			long time = Long.parseLong(s);
			
			return new Date(time);
		} 
		catch(NumberFormatException e) {
			new ParsingError("String", s, "Font").print();
			
			return null;
		}
	}
	
	/***** OBJECT > CODE *****/
	public String stringToCode(String s) {
		return s.replaceAll(" ", "_");
	}
	
	public String colorToCode(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int a = c.getAlpha();
		
		if(r == g && r == b && a == 1) {
			return r + "";
		}
		else if(a == 1) {
			return r + "_" + g + "_" + b;
		}
		else {
			return r + "_" + g + "_" + b + "_" + a;
		}
	}
	
	public String integerToCode(int i) {
		return i + "";
	}
	
	public String longToCode(long l) {
		return l + "";
	}
	
	public String longToDouble(double d) {
		return d + "";
	}
	
	public String longToFloat(float d) {
		return d + "";
	}
	
	public String booleanToCode(boolean b) {
		if(b == false) {
			return "false";
		}
		else {
			return "true";
		}
	}
	
	public String listToCode(List<String> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String stringListToCode(List<String> l) {
		return listToCode(l);
	}
	
	public String integerListToCode(List<Integer> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String longListToCode(List<Long> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String floatListToCode(List<Float> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String doubleListToCode(List<Double> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String byteListToCode(List<Byte> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String shortListToCode(List<Short> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String characterListToCode(List<Character> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				s += l.get(i);
			}
			else {
				s += l.get(i) + ", ";
			}
		}
		
		return s + "]";
	}
	
	public String booleanListToCode(List<Boolean> l) {
		String s = "[";
		
		for(int i = 0; i < l.size(); i++) {
			if(i == (l.size() - 1)) {
				boolean b = l.get(i);
				
				if(b == false) {
					s += "false";
				}
				else {
					s += "true";
				}
			}
			else {
				boolean b = l.get(i);
				
				if(b == false) {
					s += "false, ";
				}
				else {
					s += "true, ";
				}
			}
		}
		
		return s + "]";
	}
	
	public String fileToCode(File f) {
		return f.getAbsolutePath();
	}
	
	public String fontToCode(Font f) {
		return f.getName() + "_" + f.getStyle() + "_" + f.getSize();
	}
	
	public String dateToCode(Date d) {
		return d.getTime() + "";
	}
	
	@Override
	public String getData() {
		return "";
	}

	@Override
	public String getName() {
		return "ObjectUtils";
	}

}
