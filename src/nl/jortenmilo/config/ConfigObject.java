package nl.jortenmilo.config;

import java.util.ArrayList;
import java.util.List;

import nl.jortenmilo.error.MissingConfigObjectError;
import nl.jortenmilo.error.NonNullableParameterError;

public class ConfigObject {
	
	private String name = "";
	private String value = "";
	private List<ConfigObject> objects = new ArrayList<ConfigObject>();
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		if(name == null) {
			new NonNullableParameterError("String", "name").print();
			return;
		}
		
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		if(value == null) {
			new NonNullableParameterError("String", "value").print();
			return;
		}
		
		this.value = value;
	}
	
	public List<ConfigObject> getObjects() {
		return objects;
	}
	
	public void set(ConfigObject object, String path) {
		if(object == null) {
			new NonNullableParameterError("ConfigObject", "object").print();
			return;
		}
		if(path == null) {
			new NonNullableParameterError("String", "path").print();
			return;
		}
		
		if(!path.contains(".")) {
			objects.add(object);
		} 
		else {
			String key = path.substring(0, path.indexOf("."));
			
			for(ConfigObject o : objects) {
				if(o.getName().equals(key)) {
					o.set(object, path.substring(path.indexOf(".")+1, path.length()));
					return;
				}
			}
			
			new MissingConfigObjectError(key, path).print();
		}
	}

	public ConfigObject get(String path) {
		if(path == null) {
			new NonNullableParameterError("String", "path").print();
			return null;
		}
		
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
