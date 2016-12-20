package nl.jortenmilo.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		objects.add(object);
	}
	
	public List<ConfigObject> getObjects() {
		return objects;
	}
	
	public ConfigObject get(String path) {
		
		
		return null;
	}
	
}
