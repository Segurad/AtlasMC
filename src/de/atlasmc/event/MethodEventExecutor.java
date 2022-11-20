package de.atlasmc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * EventExecutor implementation that invokes methods
 */
public class MethodEventExecutor implements EventExecutor {
	
	private final boolean ignorecancelled;
	private final Class<? extends Event> eventClass;
	private final Method method;
	private final EventPriority priority;
	private final Listener listener;
	
	public MethodEventExecutor(Class<? extends Event> eventClass, Method method, EventPriority priority, boolean ignoreCancelled, Listener listener) {
		this.eventClass = eventClass;
		this.ignorecancelled = ignoreCancelled;
		this.method = method;
		this.priority = priority;
		this.listener = listener;
		method.setAccessible(true);
	}
	
	@Override
	public Listener getListener() {
		return listener;
	}

	@Override
	public boolean getIgnoreCancelled() {
		return ignorecancelled;
	}
	
	@Override
	public Class<? extends Event> getEventClass() {
		return eventClass;
	}
	
	@Override
	public EventPriority getPriority() {
		return priority;
	}
	
	@Override
	public void fireEvent(Event event) {
		if (!eventClass.isInstance(event))
			return;
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Only checks if the obj is this
	 */
	@Override
	public final boolean equals(Object obj) {
		return obj == this;
	}
	
	/**
	 * Returns a list of {@link EventExecutor}s created by the methods marked with {@link EventHandler} of the Listener class
	 * @param listener
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<EventExecutor> getExecutors(Listener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		List<EventExecutor> executors = null;
		for (Method method : listener.getClass().getMethods()) {
			EventHandler handler = method.getAnnotation(EventHandler.class);
			if (handler == null) 
				continue;
			if (method.getParameterCount() != 1) 
				continue;
			Class<?>[] params = method.getParameterTypes();
			if (!Event.class.isAssignableFrom(params[0])) 
				continue;
			if (executors == null)
				executors = new ArrayList<>();
			executors.add(new MethodEventExecutor((Class<? extends Event>) params[0], method, handler.priority(), handler.ignoreCancelled(), listener));
		}
		return executors == null ? List.of() : executors;
	}
	
}
