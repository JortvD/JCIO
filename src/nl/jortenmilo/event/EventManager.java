package nl.jortenmilo.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.SystemUtils;

public class EventManager {
	
	private Map<Class<? extends Event>, List<EventHandler>> events = new HashMap<Class<? extends Event>, List<EventHandler>>();
	
	public void registerListener(EventListener listener, Plugin plugin) {
		if(listener == null) {
			new NullableParameterError("EventListener", "listener").print();
			return;
		}
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
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
	
	public void unregisterListener(EventListener listener) {
		if(listener == null) {
			new NullableParameterError("EventListener", "listener").print();
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
	
	public void unregisterPlugin(Plugin plugin) {
		if(plugin == null) {
			new NullableParameterError("Plugin", "plugin").print();
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
	
	public List<EventHandler> getHandlers(Class<? extends Event> event) {
		if(event == null) {
			new NullableParameterError("Class<? extends Event>", "event").print();
			return null;
		}
		
		if(!events.containsKey(event)) {
			return new ArrayList<EventHandler>();
		}
		
		return events.get(event);
	}

}
