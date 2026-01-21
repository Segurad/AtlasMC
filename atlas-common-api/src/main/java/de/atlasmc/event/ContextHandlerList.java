package de.atlasmc.event;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public class ContextHandlerList<T> extends HandlerList {

	private final Class<T> clazz;
	private final Map<T, EventExecutor[]> contextExecutors;
	
	public ContextHandlerList(Class<T> clazz) {
		super();
		this.clazz = Objects.requireNonNull(clazz);
		contextExecutors = new ConcurrentHashMap<>();
	}
	
	public void registerExecutor(T context, EventExecutor executor) {
		register(context, contextExecutors, executor);
	}
	
	@Override
	public void registerExecutor(EventExecutor executor, Object... handleroptions) {
		if (handleroptions != null) {
			for (Object o : handleroptions) {
				if (clazz.isInstance(o)) {
					@SuppressWarnings("unchecked")
					T context = (T) o;
					registerExecutor(context, executor);
				} else {
					registerExecutor(executor);
				}
			}
		} else registerExecutor(executor);
	}
	
	/**
	 * Returns the iterator or null if the context is not present
	 * @param context the context to check
	 * @return iterator or null
	 */
	@Nullable
	public Iterator<EventExecutor> getExecutors(@NotNull T context) {
		return getContextIterator(context, contextExecutors);
	}
	
	@Override
	protected void callEvent(final Event event, final boolean cancelled) {
		@SuppressWarnings("unchecked")
		final T socket = ((GenericEvent<T, ?>) event).getEventSource();
		final EventExecutor[] contextExes = this.contextExecutors.get(socket);
		final EventExecutor[] globalexes = this.globalExecutors;
		final EventExecutor defaultExecutor = this.defaultExecutor;
		if (globalExecutors.length == 0 && (contextExes == null || contextExes.length == 0)) {
			fireDefaultExecutor(defaultExecutor, event);
		} else if (globalExecutors.length == 0) {
			fireExecutors(event, contextExes, defaultExecutor);
		} else if (contextExes == null || contextExes.length == 0) {
			fireExecutors(event, globalexes, defaultExecutor);
		} else {
			int contextIndex = 0;
			int globalIndex = 0;
			final int monitor = EventPriority.MONITOR.ordinal();
			for (int i = 0; i <= monitor; i++) {
				if (i == monitor) 
					fireDefaultExecutor(defaultExecutor, event);
				contextIndex = fireEvents(contextExes, contextIndex, i, event, cancelled);
				globalIndex = fireEvents(globalexes, globalIndex, i, event, cancelled);
			}
		}
	}
	
	@Override
	public synchronized void unregisterListener(Listener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		modLock.lock();
		globalExecutors = super.internalUnregister(listener, globalExecutors);
		super.internalUnregister(listener, contextExecutors);
		modLock.unlock();
	}
	
	@Override
	public synchronized void unregisterListener(PluginHandle plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		modLock.lock();
		globalExecutors = super.internalUnregister(plugin, globalExecutors);
		super.internalUnregister(plugin, contextExecutors);
		modLock.unlock();
	}
	
}
