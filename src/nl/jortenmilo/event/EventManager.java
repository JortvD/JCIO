package nl.jortenmilo.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This is the EventManager. It contains all the general methods to work with events.
 * @see Event
 */
public class EventManager {
	
	private Map<Class<? extends Event>, List<EventHandler>> events = new HashMap<Class<? extends Event>, List<EventHandler>>();
	
	/**
	 * This method is used to register a listener and all it's events.
	 * @param listener The listener you want to register
	 * @param plugin The plugin the listener is from
	 */
	public void registerListener(EventListener listener, Plugin plugin) {
		if(listener == null) {
			new NonNullableParameterError("EventListener", "listener").print();
			return;
		}
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		Console.debug("LISTENER_REGISTERED [" + new SystemUtils().getTime() + "][" + listener.getClass().getName() + "][" + plugin.getLoadedPlugin().getName() + "]");
		
		for(Method method : listener.getClass().getMethods()) {
			if(method.getParameterCount() != 1) {
				continue;
			}
			else if(!Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
				continue;
			}
			
			Class<? extends Event> event = method.getParameters()[0].getType().asSubclass(Event.class);
			
			for(Annotation annotation : method.getDeclaredAnnotations()) {
				if(annotation instanceof EventMethod) {
					EventHandler handler = new EventHandler();
					handler.setEvent(event);
					handler.setListener(listener);
					handler.setPlugin(plugin);
					handler.setMethod(method);
					
					Console.debug("EVENT_REGISTERED [" + new SystemUtils().getTime() + "][" + listener.getClass().getName() + "][" + event.getName() + "][" + method.getName() + "]");
					
					if(events.get(event) == null) {
						events.put(event, new ArrayList<EventHandler>());
					}
					
					events.get(event).add(handler);
				}
			}
			
		}
	}
	
	/**
	 * Used to unregister a listener and all it's events.
	 * @param listener The listener you want to unregister
	 */
	public void unregisterListener(EventListener listener) {
		if(listener == null) {
			new NonNullableParameterError("EventListener", "listener").print();
			return;
		}
		
		for(Method method : listener.getClass().getMethods()) {
			if(method.getParameterCount() != 1) {
				continue;
			}
			else if(method.getDeclaredAnnotations().length > 0) {
				continue;
			}
			else if(!Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
				continue;
			}
			
			for(Class<? extends Event> key : events.keySet()) {
				List<EventHandler> handlers = events.get(key);
				
				for(int i = 0; i < handlers.size(); i++) {
					EventHandler handler = handlers.get(i);
					
					Console.debug("EVENT_UNREGISTERED [" + new SystemUtils().getTime() + "][" + listener.getClass().getName() + "][" + method.getName() + "]");
					
					if(handler.getMethod() == method) {
						handlers.remove(i);
					}
				}
			}
		}
	}
	
	/**
	 * Unregisters all the listener from the specified plugin.
	 * @param plugin The plugin
	 */
	public void unregisterPlugin(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return;
		}
		
		for(Class<? extends Event> key : events.keySet()) {
			List<EventHandler> handlers = events.get(key);
			
			for(int i = 0; i < handlers.size(); i++) {
				EventHandler handler = handlers.get(i);
				
				if(handler.getPlugin() == plugin) {
					Console.debug("EVENT_UNREGISTERED [" + new SystemUtils().getTime() + "][" + handler.getListener().getClass().getName() + "][" + handler.getMethod().getName() + "]");
					
					handlers.remove(i);
				}
			}
		}
	}
	
	/**
	 * Returns a list of all the handlers with the specified event.
	 * @param event The event
	 * @return The list of handlers
	 */
	public List<EventHandler> getHandlers(Class<? extends Event> event) {
		if(event == null) {
			new NonNullableParameterError("Class<? extends Event>", "event").print();
			return null;
		}
		
		if(!events.containsKey(event)) {
			return new ArrayList<EventHandler>();
		}
		
		return events.get(event);
	}
	
	/**
	 * Returns a list of all the handlers in the specified listener.
	 * @param listener The listener
	 * @return The list of handlers
	 */
	public List<EventHandler> getHandlers(EventListener listener) {
		if(listener == null) {
			new NonNullableParameterError("EventListener", "listener").print();
			return null;
		}
		
		List<EventHandler> handlers = new ArrayList<EventHandler>();
		
		for(Class<? extends Event> event : events.keySet()) {
			for(EventHandler handler : events.get(event)) {
				if(handler.getListener() == listener) {
					handlers.add(handler);
				}
			}
		}
		
		return handlers;
	}
	
	/**
	 * Returns a list of all the handlers in the specified plugin.
	 * @param plugin The plugin
	 * @return The list of handlers
	 */
	public List<EventHandler> getHandlers(Plugin plugin) {
		if(plugin == null) {
			new NonNullableParameterError("Plugin", "plugin").print();
			return null;
		}
		
		List<EventHandler> handlers = new ArrayList<EventHandler>();
		
		for(Class<? extends Event> event : events.keySet()) {
			for(EventHandler handler : events.get(event)) {
				if(handler.getPlugin() == plugin) {
					handlers.add(handler);
				}
			}
		}
		
		return handlers;
	}

}
