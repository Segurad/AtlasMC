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
	private final Class<?> eventClass;
	private final Method method;
	private final EventPriority priority;
	private final Listener listener;
	
	public MethodEventExecutor(Class<?> eventClass, Method method, EventPriority priority, boolean ignoreCancelled, Listener listener) {
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
	public Class<?> getEventClass() {
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
	public boolean equals(Object obj) {
		return obj == this;
	}
	
	public static List<EventExecutor> getExecutors(Listener listener) {
		if (listener == null) return null;
		List<EventExecutor> executors = null;
		for (Method method : listener.getClass().getMethods()) {
			EventHandler handler = method.getAnnotation(EventHandler.class);
			if (handler == null) continue;
			if (method.getParameterCount() != 1) continue;
			Class<?>[] params = method.getParameterTypes();
			if (!Event.class.isAssignableFrom(params[0])) continue;
			if (executors == null)
				executors = new ArrayList<>();
			executors.add(new MethodEventExecutor(params[0], method, handler.priority(), handler.ignoreCancelled(), listener));
		}
		return executors == null ? List.of() : executors;
	}
	
}
