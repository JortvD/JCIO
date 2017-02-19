package nl.jortenmilo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jortenmilo.event.EventHandler;
import nl.jortenmilo.event.EventManager;
import nl.jortenmilo.plugin.Plugin;

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
		utils.put(name, new ArrayList<Utils>());
	}
	
	public void removeUtil(Class<? extends Utils> name) {
		utils.remove(name);
	}
	
	public List<Utils> getUtils(Class<? extends Utils> name) {
		return utils.get(name);
	}
	
	public List<Utils> getUtils(Plugin plugin) {
		List<Utils> list = new ArrayList<Utils>();
		
		for(Class<? extends Utils> key : utils.keySet()) {
			for(Utils util : utils.get(key)) {
				list.add(util);
			}
		}
		
		return list;
	}
	
	public List<Utils> getUtils(Plugin plugin, Class<? extends Utils> name) {
		List<Utils> list = new ArrayList<Utils>();
		
		for(Utils util : utils.get(name)) {
			list.add(util);
		}
		
		return list;
	}
}
