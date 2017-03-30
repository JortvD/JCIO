package nl.jortenmilo.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nl.jortenmilo.console.Console;
import nl.jortenmilo.error.NonNullableParameterError;
import nl.jortenmilo.plugin.Plugin;
import nl.jortenmilo.utils.defaults.SystemUtils;

/**
 * This class handles the events for every single event method.
 * @see Event
 */
public class EventHandler {
	
	private Method method;
	private Class<? extends Event> event;
	private Plugin plugin;
	private EventListener listener;
	
	/**
	 * This method let's you execute a event.
	 * @param event Needs the event to execute.
	 */
	public void execute(Event event) {
		if(event == null) {
			new NonNullableParameterError("Event", "event").print();
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
	
	/**
	 * Returns the method for this EventHandler.
	 * @return The method
	 */
	public Method getMethod() {
		return method;
	}

	protected void setMethod(Method method) {
		this.method = method;
	}
	
	/**
	 * Returns the plugin for this EventHandler.
	 * @return The plugin
	 * @see Plugin
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	protected void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns the class of this event.
	 * @return The class
	 */
	public Class<? extends Event> getEvent() {
		return event;
	}

	protected void setEvent(Class<? extends Event> event) {
		this.event = event;
	}
	
	/**
	 * Returns the listener this event is from.
	 * @return The listener
	 * @see EventListener
	 */
	public EventListener getListener() {
		return listener;
	}

	protected void setListener(EventListener listener) {
		this.listener = listener;
	}
	
}
