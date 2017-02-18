package nl.jortenmilo.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nl.jortenmilo.plugin.Plugin;

public class EventHandler {
	
	private Method method;
	private Class<? extends Event> event;
	private Plugin plugin;
	private EventListener listener;
	
	public void execute(Event event) {
		try {
			method.invoke(listener, this.event.cast(event));
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace(); //TODO: Change this to UnknownException
		}
	}

	public Method getMethod() {
		return method;
	}

	protected void setMethod(Method method) {
		this.method = method;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	public Class<? extends Event> getEvent() {
		return event;
	}

	protected void setEvent(Class<? extends Event> event) {
		this.event = event;
	}

	public EventListener getListener() {
		return listener;
	}

	protected void setListener(EventListener listener) {
		this.listener = listener;
	}
	
}
