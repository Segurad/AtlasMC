package de.atlasmc.event;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import de.atlasmc.plugin.PluginHandle;

/**
 * EventExecutor implementation that invokes methods
 */
public final class MethodEventExecutor extends EventExecutor {
	
	private final Consumer<Event> handler;
	
	public MethodEventExecutor(PluginHandle plugin, Class<? extends Event> eventClass, Method method, EventPriority priority, EventHandledAction action, boolean ignoreCancelled, Listener listener) {
		super(plugin, eventClass, ignoreCancelled, priority, action, listener);
		Objects.requireNonNull(method, "method");
		try {
			Lookup lookup = MethodHandles.privateLookupIn(listener.getClass(), MethodHandles.lookup());
			MethodHandle handle = lookup.unreflect(method);
			CallSite site = LambdaMetafactory.metafactory(
					lookup, 
					"accept", 
					MethodType.methodType(Consumer.class, listener.getClass()), 
					MethodType.methodType(void.class, Object.class), 
					handle, 
					MethodType.methodType(void.class, eventClass));
			MethodHandle factory = site.getTarget();
			handler = (Consumer<Event>) factory.invoke(listener);
		} catch (Throwable e) {
			throw new EventException("Failed to create handler " + method.getName() + " for listener" + listener.getClass().getName(), e);
		}
	}	
	
	/**
	 * Returns a list of {@link EventExecutor}s created by the methods marked with {@link EventHandler} of the Listener class
	 * @param listener
	 * @return list
	 */
	public static List<EventExecutor> getExecutors(PluginHandle plugin, Listener listener) {
		List<EventExecutor> executors = null;
		for (Method method : listener.getClass().getDeclaredMethods()) {
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
		handler.accept(event);
	}
	
}
