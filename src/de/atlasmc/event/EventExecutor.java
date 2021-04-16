package de.atlasmc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventExecutor {
	
	private final boolean ignorecancelled;
	private final Class<?> eventClass;
	private final Method method;
	private final EventPriority priority;
	private final Listener listener;
	
	public EventExecutor(Class<?> eventClass, Method method, EventPriority priority, boolean ignoreCancelled, Listener listener) {
		this.eventClass = eventClass;
		this.ignorecancelled = ignoreCancelled;
		this.method = method;
		this.priority = priority;
		this.listener = listener;
	}
	
	public Listener getListener() {
		return listener;
	}

	public boolean getIgnoreCancelled() {
		return ignorecancelled;
	}
	
	public Class<?> getEventClass() {
		return eventClass;
	}
	
	public EventPriority getPriority() {
		return priority;
	}
	
	public void fireEvent(Event event) {
		try {
			method.invoke(eventClass, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
