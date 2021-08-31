package de.atlasmc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class contains informations of a {@link EventHandler} Method and the Listener
 */
public class EventExecutor {
	
	/**
	 * EventExecutor that does nothing
	 */
	public static final EventExecutor NULL_EXECUTOR = new EventExecutor() {
		@Override
		public void fireEvent(Event event) {}
	};
	
	private final boolean ignorecancelled;
	private final Class<?> eventClass;
	private final Method method;
	private final EventPriority priority;
	private final Listener listener;
	
	private EventExecutor() {
		this.eventClass = null;
		this.ignorecancelled = false;
		this.method = null;
		this.priority = EventPriority.MONITOR;
		this.listener = null;
	}
	
	public EventExecutor(Class<?> eventClass, Method method, EventPriority priority, boolean ignoreCancelled, Listener listener) {
		this.eventClass = eventClass;
		this.ignorecancelled = ignoreCancelled;
		this.method = method;
		this.priority = priority;
		this.listener = listener;
		method.setAccessible(true);
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
	
	/**
	 * Invokes the EventHandler Method of this EventExecutor
	 * @param event
	 */
	public void fireEvent(Event event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
