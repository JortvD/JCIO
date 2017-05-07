package nl.jortenmilo.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import nl.jortenmilo.command.CommandDecoder;
import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.ConfigLoadingError;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This is the config loader class. From here classes are loaded and saved. You shouldn't instantiate this class and use the ConfigManager instead.
 */
public class ConfigLoader {
	
	/**
	 * Loads a config using the file from the ConfigFile. It may throw a ConfigLoadingError when there is a something wrong in the config.
	 * @param config The ConfigFile you want to load
	 */
	@SuppressWarnings("resource")
	public void load(ConfigFile config) {
		if(config == null) {
			new NonNullableParameterError("ConfigFile", "config").print();
			return;
		}
		
		try {
			String path = "";
			String line = "";
			int l = 0;
			boolean list = false;
			BufferedReader br = new BufferedReader(new FileReader(config.getFile()));
			
			while((line = br.readLine()) != null) {
				if(line.equals("") || line.replaceAll(" ", "").equals("")) {
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
					} 
					else {
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
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		Console.debug("CONFIG_LOADED [" + new SystemUtils().getTime() + "][" + config.hashCode() + "][" + config.getFile().getPath() + "]");
	}
	
	/**
	 * This will save the ConfigFile to it's file.
	 * @param config The config that needs to be saved
	 */
	public void save(ConfigFile config) {
		if(config == null) {
			new NonNullableParameterError("ConfigFile", "config").print();
			return;
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(config.getFile()));
			
			for(ConfigObject object : config.getObjects()) {
				bw.write(object.getName() + ": " + object.getValue());
				bw.newLine();
				writeText(object, bw, " ");
			}
			
			bw.close();
		} 
		catch(Error | Exception e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		Console.debug("CONFIG_SAVED [" + new SystemUtils().getTime() + "][" + config.hashCode() + "][" + config.getFile().getPath() + "]");
	}
	
	private int getSpaces(String s) {
		int spaces = 0;
		byte[] bytes = s.getBytes();
		
		for(int i = 0; i < s.length(); i++) {
			if(bytes[i] == (byte)' ') {
				spaces++;
			} 
			else {
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
	
	private void writeText(ConfigObject o, BufferedWriter bw, String prefix) throws IOException {
		for(ConfigObject object : o.getObjects()) {
			bw.write(prefix + object.getName() + ": " + object.getValue());
			bw.newLine();
			
			writeText(object, bw, prefix + " ");
		}
	}

}
