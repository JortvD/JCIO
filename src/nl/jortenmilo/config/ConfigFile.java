package nl.jortenmilo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.error.MissingConfigObjectError;

public class ConfigFile {
	
	private File file;
	private List<ConfigObject> objects = new ArrayList<ConfigObject>();

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void set(ConfigObject object, String path) {
		if(!path.contains(".")) {
			objects.add(object);
		} 
		else {
			String key = path.substring(0, path.indexOf("."));
			
			for(ConfigObject o : objects) {
				if(o.getName().equals(key)) {
					o.set(object, path.substring(path.indexOf(".") + 1, path.length()));
					return;
				}
			}
			
			new MissingConfigObjectError(key, path).print();
		}
	}
	
	public List<ConfigObject> getObjects() {
		return objects;
	}
	
	public ConfigObject get(String path) {
		String key = path.substring(0, path.indexOf("."));
		for(ConfigObject o : objects) {
			if(o.getName().equals(key)) {
				if(!path.contains(".")) {
					return o;
				} 
				else {
					return o.get(path.substring(path.indexOf(".") + 1, path.length()));
				}
			}
		}
		
		new MissingConfigObjectError(key, path).print();
		
		return null;
	}
	
}
