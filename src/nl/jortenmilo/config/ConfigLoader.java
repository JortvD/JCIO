package nl.jortenmilo.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.error.ConfigLoadingError;

public class ConfigLoader {

	@SuppressWarnings("resource")
	public void load(ConfigFile config) {
		try {
			String path = "";
			String line = "";
			int l = 0;
			boolean list = false;
			BufferedReader br = new BufferedReader(new FileReader(config.getFile()));
			
			while((line = br.readLine()) != null) {
				if(line.equals("")) {
					continue;
				}
				
				if(!line.contains(":") && !list) {
					new ConfigLoadingError(config.getFile().getPath(), l + "", 0 + "", "Missing semi-colon to end the key").print();
					return;
				}
				
				int spaces = getSpaces(line);
				int dots = getDots(path);
				
				String key = line.substring(spaces, line.indexOf(":"));
				
				if(key.contains(".")) {
					new ConfigLoadingError(config.getFile().getPath(), l + "", key.indexOf(".") + "", "A key can't contain a dot").print();
					return;
				}
				
				if(spaces == dots) {
					path = createNewPath(path, key);
				}
				else if(spaces > dots) {
					if(spaces-dots == 1) {
						path = createLongerPath(path, key);
					} else {
						new ConfigLoadingError(config.getFile().getPath(), l + "", spaces + "", "Too much spaces").print();
						return;
					}
				}
				else if(spaces < dots) {
					path = createShorterPath(path, key, dots-spaces);
				}
				
				String value = line.substring(line.indexOf(":")+1, line.length());
				
				if(value.startsWith(" ")) {
					value = value.substring(1, value.length());
				}
				
				ConfigObject object = new ConfigObject();
				object.setName(key);
				object.setValue(value);
				config.set(object, path);
				
				l++;
			}
			
			br.close();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
	}

	public void save(ConfigFile config) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(config.getFile()));
			
			for(ConfigObject object : config.getObjects()) {
				bw.write(object.getName() + ": " + object.getValue());
				bw.newLine();
				object.writeText(bw, " ");
			}
			
			bw.close();
		} catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
	}
	
	private int getSpaces(String s) {
		int spaces = 0;
		byte[] bytes = s.getBytes();
		
		for(int i = 0; i < s.length(); i++) {
			if(bytes[i] == (byte)' ') {
				spaces++;
			} else {
				return spaces;
			}
		}
		
		return spaces;
	}
	
	private int getDots(String s) {
		int dots = 0;
		byte[] bytes = s.getBytes();
		
		for(int i = 0; i < s.length(); i++) {
			if(bytes[i] == (byte)'.') {
				dots++;
			}
		}
		
		return dots;
	}
	
	private String createShorterPath(String path, String key, int length) {
		String newPath = "";
		path = path.replace(".", " ");
		String[] keys = CommandDecoder.getParameters(path);
		
		if(keys.length-length == 1) {
			return key;
		}
		
		for(int i = 0; i < keys.length-length; i++) {
			newPath += keys[i] + ".";
		}
		
		newPath = newPath.substring(0, newPath.length()-1);
		newPath = newPath.substring(0, newPath.lastIndexOf(".")) + "." + key;
		
		return newPath;
	}
	
	private String createNewPath(String path, String n) {
		String newPath = "";
		path = path.replace(".", " ");
		String[] keys = CommandDecoder.getParameters(path);
		
		if(!path.contains(".")) {
			return n;
		}
		
		for(int i = 0; i < keys.length-1; i++) {
			newPath += keys[i] + ".";
		}
		
		newPath = newPath.substring(0, newPath.length()-1) + "." + n;
		
		return newPath;
	}
	
	private String createLongerPath(String path, String a) {
		return path + "." + a;
	}

}
