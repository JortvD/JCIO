package nl.jortenmilo.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.NullableParameterError;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.SystemUtils;

public class EventHandler {
	
	private Method method;
	private Class<? extends Event> event;
	private Plugin plugin;
	private EventListener listener;
	
	public void execute(Event event) {
		if(event == null) {
			new NullableParameterError("Event", "event").print();
			return;
		}
		
		Console.debug("EVENT_EXECUTED [" + new SystemUtils().getTime() + "][" + listener.getClass().getName() + "][" + method.getName() + "]");
		
		try {
			method.invoke(listener, this.event.cast(event));
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			new nl.jortenmilo.error.UnknownError(e.toString(), e.getMessage()).print();
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
