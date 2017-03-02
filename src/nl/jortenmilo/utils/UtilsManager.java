package nl.jortenmilo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.IDUtils;
import nl.jortenmilo.utils.defaults.ObjectUtils;
import nl.jortenmilo.utils.defaults.StringUtils;
import nl.jortenmilo.utils.defaults.SystemUtils;

public class UtilsManager {
	
	private HashMap<Class<? extends Utils>, List<Utils>> utils = new HashMap<Class<? extends Utils>, List<Utils>>();
	
	private EventManager events;
	
	public UtilsManager(EventManager events) {
		this.events = events;
		
		addUtil(IDUtils.class);
		addUtil(ObjectUtils.class);
		addUtil(StringUtils.class);
		addUtil(SystemUtils.class);
	}
	
	public Utils createUtils(Class<? extends Utils> name, Plugin plugin) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return null;
		}
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		try {
			for(Class<? extends Utils> key : utils.keySet()) {
				if(key == name) {
					Utils util = name.newInstance();
					util.setPlugin(plugin);
					
					UtilsCreatedEvent event = new UtilsCreatedEvent();
					event.setUtilName(util.getName());
					
					for(EventHandler handler : events.getHandlers(event.getClass())) {
						handler.execute(event);
					}
					
					List<Utils> list = utils.get(name);
					list.add(util);
					utils.put(name, list);
					
					return util;
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
		}
		
		return null;
	}
	
	public Utils cloneUtils(Class<? extends Utils> name, Utils util) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return null;
		}
		if(util == null) {
			new NullableParameterError("Utils", "util").print();
			return null;
		}
		
		Utils clone = util.clone();
		clone.setPlugin(util.getPlugin());
		
		UtilsClonedEvent event = new UtilsClonedEvent();
		event.setUtilName(util.getName());
		event.setData(util.getData());
		
		for(EventHandler handler : events.getHandlers(event.getClass())) {
			handler.execute(event);
		}
		
		List<Utils> list = utils.get(name);
		list.add(clone);
		utils.put(name, list);
		
		return clone;
	}
	
	public void addUtil(Class<? extends Utils> name) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return;
		}
		
		utils.put(name, new ArrayList<Utils>());
	}
	
	public void removeUtil(Class<? extends Utils> name) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return;
		}
		
		utils.remove(name);
	}
	
	public List<Utils> getUtils(Class<? extends Utils> name) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return null;
		}
		
		return utils.get(name);
	}
	
	public List<Utils> getUtils(Plugin plugin) {
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		List<Utils> list = new ArrayList<Utils>();
		
		for(Class<? extends Utils> key : utils.keySet()) {
			for(Utils util : utils.get(key)) {
				list.add(util);
			}
		}
		
		return list;
	}
	
	public List<Utils> getUtils(Plugin plugin, Class<? extends Utils> name) {
		if(name == null) {
			new NullableParameterError("Class<? extends Utils>", "name").print();
			return null;
		}
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		List<Utils> list = new ArrayList<Utils>();
		
		for(Utils util : utils.get(name)) {
			list.add(util);
		}
		
		return list;
	}
}
