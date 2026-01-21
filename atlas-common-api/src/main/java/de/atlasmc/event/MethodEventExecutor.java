package de.atlasmc.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.atlasmc.plugin.PluginHandle;

/**
 * EventExecutor implementation that invokes methods
 */
public final class MethodEventExecutor extends EventExecutor {
	
	private final Method method;
	
	public MethodEventExecutor(PluginHandle plugin, Class<? extends Event> eventClass, Method method, EventPriority priority, EventHandledAction action, boolean ignoreCancelled, Listener listener) {
		super(plugin, eventClass, ignoreCancelled, priority, action, listener);
		this.method = Objects.requireNonNull(method, "method");
		method.setAccessible(true); // we set this accessible because event handler may be private for API clarity
	}	
	
	/**
	 * Returns a list of {@link EventExecutor}s created by the methods marked with {@link EventHandler} of the Listener class
	 * @param listener
	 * @return list
	 */
	public static List<EventExecutor> getExecutors(PluginHandle plugin, Listener listener) {
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
			@SuppressWarnings("unchecked")
			Class<? extends Event> eventClass = (Class<? extends Event>) params[0];
			if (!Event.class.isAssignableFrom(eventClass)) 
				continue;
			if (executors == null)
				executors = new ArrayList<>();
			executors.add(new MethodEventExecutor(plugin, eventClass, method, handler.priority(), handler.ignoreHandled(), handler.ignoreCancelled(), listener));
		}
		return executors == null ? List.of() : executors;
	}

	@Override
	public void fireEvent(Event event) throws Exception {
		if (!eventClass.isInstance(event))
			return;
		method.invoke(listener, event);
	}
	
}
