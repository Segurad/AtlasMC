package de.atlasmc.event;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlasmc.Atlas;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.iterator.ArrayIterator;

/**
 * This class holds all {@link EventExecutor} for the Event it is used
 */
@ThreadSafe
public class HandlerList {
	
	protected static final EventExecutor[] EMPTY = new EventExecutor[0];
	protected static final List<WeakReference<HandlerList>> HANDLERS = new CopyOnWriteArrayList<>();
	private static final ReferenceQueue<HandlerList> REF_QUEUE = new ReferenceQueue<>();
	
	
	protected final Lock modLock = new ReentrantLock();
	protected volatile EventExecutor[] globalExecutors;
	protected volatile EventExecutor defaultExecutor;
	
	public HandlerList() {
		this.defaultExecutor = EventExecutor.NULL_EXECUTOR;
		this.globalExecutors = EMPTY;
		registerHandlerList();
	}
	
	/**
	 * Sets the DefaultEventExecutor<br>
	 * <br>
	 * The DefaultEventExecutor is always the last EventExecutor called, ignoring the flags ({@link EventPriority} and ignoreCancelled)
	 * @param defaultExecutor
	 */
	public void setDefaultExecutor(EventExecutor defaultExecutor) {
		this.defaultExecutor = defaultExecutor != null ? defaultExecutor : EventExecutor.NULL_EXECUTOR;
	}
	
	/**
	 * Returns the DefaultEventExecutor of this HandlerList by Default it is the {@link EventExecutor#NULL_EXECUTOR}
	 * @return a EventExecutor
	 */
	public EventExecutor getDefaultExecutor() {
		return defaultExecutor;
	}
	
	/**
	 * Fires the Event for the Default event executor
	 * @param event
	 * @param log the logger error should be send to
	 */
	protected void fireDefaultExecutor(EventExecutor executor, Event event, Log log) {
		try {
			executor.fireEvent(event);
		} catch (InvocationTargetException ex) {
			log.error("Error while event handling with default handler for: " + event.getName(), ex.getCause());
		} catch (Exception ex) {
			log.error("Error while event handling with default handler for: " + event.getName(), ex);
		}
	}
	
	/**
	 * Registers this HandlerList
	 */
	private final void registerHandlerList() {
		removeStaleEntries();
		HANDLERS.add(new WeakReference<>(this, REF_QUEUE));
	}
	
	protected static final void removeStaleEntries() {
		Reference<?> ref = null;
		while ((ref = REF_QUEUE.poll()) != null) {
			HANDLERS.remove(ref);
		}
	}
	
	public void registerExecutor(@NotNull EventExecutor executor) {
		if (executor == null) 
			return;
		register(globalExecutors, executor);
	}
	
	public void registerExecutor(EventExecutor executor, Object... context) {
		registerExecutor(executor);
	}
	
	/**
	 * Creates a ordered copy of the given array with the executor inserted
	 * @param exes
	 * @param executor
	 */
	protected EventExecutor[] register(final EventExecutor[] exes, final EventExecutor executor) {
		final int priority = executor.getPriority().ordinal();
		int insertIndex = -1;
		for (int i = 0; i < exes.length; i++) {
			EventExecutor exe = exes[i];
			if (exe == executor)
				return exes; // no need for modification already present
			if (exes[i].getPriority().ordinal() > priority) {
				insertIndex = i;
				break;
			}
		}
		EventExecutor[] newExes = new EventExecutor[exes.length + 1];
		System.arraycopy(exes, 0, newExes, 0, insertIndex);
		newExes[insertIndex] = executor;
		System.arraycopy(exes, insertIndex, newExes, insertIndex + 1, exes.length - insertIndex);
		return newExes;
	}
	
	protected <T> void register(final T context, final Map<T, EventExecutor[]> contextExecutors, final EventExecutor executor) {
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		if (executor == null)
			throw new IllegalArgumentException("Executor can not be null!");
		modLock.lock();
		final EventExecutor[] executors = contextExecutors.get(context);
		if (executors == null || executors.length == 0) {
			contextExecutors.put(context, new EventExecutor[] { executor });
		}
		final EventExecutor[] newExecutors = register(executors, executor);
		if (executors != newExecutors)
			contextExecutors.put(context, newExecutors);
		modLock.unlock();
	}
	
	protected static <T> Iterator<EventExecutor> getContextIterator(T context, Map<T, EventExecutor[]> contextExecutors) {
		if (context == null)
			throw new IllegalArgumentException("Context can not be null!");
		final EventExecutor[] list = contextExecutors.get(context);
		return list == null || list.length == 0 ? null : new ArrayIterator<>(list, false);
	}
	
	/**
	 * Returns a iterator for the global executors or null if no executors present
	 * @return iterator or null
	 */
	@Nullable
	public Iterator<EventExecutor> getExecutors() {
		return new ArrayIterator<>(globalExecutors, false);
	}
	
	/**
	 * Calls a event for all registered {@link EventExecutor}s of the given event
	 * If a event is marked as sync in needs to be called in a sync way.
	 * If the event is a instance of {@link GenericEvent}, {@link SyncThreadHolder#isSync()} will be used to check the sync state.
	 * Otherwise the sync state will be check by {@link LocalAtlasNode#isSync()}
	 * @param event the event to call
	 */
	public static void callEvent(@NotNull final Event event) {
		if (!event.isAsynchronous()) {
			if (event instanceof GenericEvent<?, ?> gEvent) {
				if (!gEvent.getSyncThreadHolder().isSync())
					throw new EventException("Tried to call sync event asynchronous!");
			} else if (!Atlas.isMainThread()) {
				throw new EventException("Tried to call sync event asynchronous!");
			}
		}
		final HandlerList handlers = event.getHandlers();
		handlers.callEvent(event, event instanceof Cancellable);
	}
	
	/**
	 * Calls all EventExecutors of this HandlerList.
	 * This Method will be called by the static Method {@link #callEvent(Event)} and should be used for children to fire Events
	 * @param event
	 * @param cancellable
	 */
	protected void callEvent(final Event event, boolean cancellable) {
		final Log log = Atlas.getLogger();
		final EventExecutor[] handlers = globalExecutors;
		final EventExecutor defaultHandler = defaultExecutor;
		if (handlers.length == 0) {
			fireDefaultExecutor(defaultHandler, event, log);
			return;
		}
		fireExecutors(event, handlers, defaultHandler, log);
	}
	
	protected void fireExecutors(final Event event, final EventExecutor[] executors, final EventExecutor defaultHandler, final Log log) {
		boolean defaultHandlerFired = false;
		for (EventExecutor exe : globalExecutors){
			if (exe.getPriority() == EventPriority.MONITOR && !defaultHandlerFired) {
				defaultHandlerFired = true;
				fireDefaultExecutor(defaultHandler, event, log);
			}
			try {
				exe.fireEvent(event);
			} catch (Exception ex) {
				exe.getPlugin().getLogger().error("Error while event handling for: " + event.getName(), ex);
			}
		}
	}
	
	/**
	 * Fires the Event for all Executors with a lower or equal priority
	 * @param executors
	 * @param priority
	 * @param event event
	 * @param log the logger all error should be send to
	 * @param cancellable whether or not the event extends {@link Cancellable}
	 * @return next index
	 */
	protected static int fireEvents(final EventExecutor[] executors, final int start, final int priority, final Event event, final boolean cancellable, Log log) {
		if (start >= executors.length) 
			return start;
		final int length = executors.length;
		for (int i = start; i < length; i++) {
			EventExecutor exe = executors[i];
			if (exe.getPriority().ordinal() > priority) 
				return i;
			if (exe.getIgnoreCancelled() && (!cancellable && ((Cancellable) event).isCancelled()))
				continue;
			try {
				exe.fireEvent(event);
			} catch (Exception ex) {
				exe.getPlugin().getLogger().error("Error while event handling for: " + event.getName(), ex);
			}
		}
		return length; // list fully iterated no executors left
	}
	
	/**
	 * Unregisters the Listener from all HandlerLists
	 * @param listener
	 */
	public static void unregisterListenerGlobal(@NotNull Listener listener) {
		synchronized (HANDLERS) {
			removeStaleEntries();
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				HandlerList list = ref.get();
				if (list == null)
					continue;
				list.unregisterListener(listener);
			}
		}
	}
	
	/**
	 * Unregisters the Listener of a Plugin from all HandlerLists
	 * @param plugin
	 */
	public static void unregisterListenerGlobal(@NotNull PluginHandle plugin) {
		synchronized (HANDLERS) {
			removeStaleEntries();
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				HandlerList list = ref.get();
				if (list == null)
					continue;
				list.unregisterListener(plugin);
			}
		}
	}

	/**
	 * Unregisters the Listener from this HandlerList
	 * @param listener
	 */
	public void unregisterListener(Listener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		modLock.lock();
		globalExecutors = internalUnregister(listener, globalExecutors);
		modLock.unlock();
	}
	
	public void unregisterListener(PluginHandle plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		modLock.lock();
		globalExecutors = internalUnregister(plugin, globalExecutors);
		modLock.unlock();
	}
	
	/**
	 * 
	 * @param listener
	 * @param executors
	 * @return
	 */
	protected static EventExecutor[] internalUnregister(final Listener listener, final EventExecutor[] executors) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		final int length = executors.length;
		if (length == 0)
			return executors;
		int newLength = length;
		for (EventExecutor exe : executors) {
			if (exe.getListener() == listener)
				newLength--;
		}
		if (newLength == length)
			return executors; // no modification required
		if (newLength == 0)
			return EMPTY;
		final EventExecutor[] newExes = new EventExecutor[newLength];
		for (int i = 0, j = 0; i < length; i++) {
			EventExecutor exe = executors[i];
			if (exe.getListener() == listener)
				continue;
			newExes[j++] = exe;
		}
		return executors;
	}
	
	protected static void internalUnregister(final Listener listener, final Map<?, EventExecutor[]> contextExecutors) {
		for (Entry<?, EventExecutor[]> entry : contextExecutors.entrySet()) {
			final Object key = entry.getKey();
			final EventExecutor[] executors = entry.getValue();
			final EventExecutor[] newExecutors = internalUnregister(listener, executors);
			if (executors == newExecutors)
				continue;
			if (newExecutors.length == 0) {
				contextExecutors.remove(key, entry.getValue());
			} else {
				entry.setValue(newExecutors);
			}
		}
	}
	
	/**
	 * 
	 * @param plugin
	 * @param executors
	 */
	protected static EventExecutor[] internalUnregister(PluginHandle plugin, EventExecutor[] executors) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		final int length = executors.length;
		if (length == 0)
			return executors;
		int newLength = length;
		for (EventExecutor exe : executors) {
			if (exe.getPlugin() == plugin)
				newLength--;
		}
		if (newLength == length)
			return executors; // no modification required
		if (newLength == 0)
			return EMPTY;
		final EventExecutor[] newExes = new EventExecutor[newLength];
		for (int i = 0, j = 0; i < length; i++) {
			EventExecutor exe = executors[i];
			if (exe.getPlugin() == plugin)
				continue;
			newExes[j++] = exe;
		}
		return executors;
	}
	
	protected static void internalUnregister(final PluginHandle plugin, final Map<?, EventExecutor[]> contextExecutors) {
		for (Entry<?, EventExecutor[]> entry : contextExecutors.entrySet()) {
			final Object key = entry.getKey();
			final EventExecutor[] executors = entry.getValue();
			final EventExecutor[] newExecutors = internalUnregister(plugin, executors);
			if (executors == newExecutors)
				continue;
			if (newExecutors.length == 0) {
				contextExecutors.remove(key, entry.getValue());
			} else {
				entry.setValue(newExecutors);
			}
		}
	}
	
	/**
	 * If the Listener contains a e.g. a {@link AbstractServerEvent}, you may add a {@link LocalServer} or {@link ServerGroup} for registration.
	 * If there are multiple LocalServers or/and ServerGroups it will registered for all.
	 * If not present or does not contain a specific Object the EventHandler will be registered as Global.
	 * @param plugin the plugin this listener belongs to
	 * @param listener
	 * @param context the context in which this listener should be registered
	 */
	public static void registerListener(PluginHandle plugin, Listener listener, Object... context) {
		if (listener == null) 
			throw new IllegalArgumentException("Listener can not be null!");
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(plugin, listener);
		for (EventExecutor exe : exes) {
			HandlerList handlers = getHandlerListOf(exe.getEventClass());
			handlers.registerExecutor(exe, context);
		}
	}
	
	/**
	 * Registers a functional listener for a given event
	 * @param <E> type of event
	 * @param plugin the plugin this listener belongs to
	 * @param eventClass the event type the listener handles
	 * @param listener the listener function
	 * @param context optional context for registration
	 */
	public static <E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, Object... context) {
		if (listener == null) 
			throw new IllegalArgumentException("Listener can not be null!");
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		HandlerList handlers = getHandlerListOf(eventClass);
		FunctionalListenerExecutor exe = new FunctionalListenerExecutor(plugin, eventClass, listener);
		handlers.registerExecutor(exe, context);
	}
	
	/**
	 * Returns the HandlerList of a event class
	 * @param eventClass
	 * @return handler list
	 */
	public static HandlerList getHandlerListOf(Class<? extends Event> eventClass) {
		Method m;
		try {
			m = eventClass.getMethod("getHandlerList");
		} catch (NoSuchMethodException e) {
			throw new EventException("Unable to find static getHandlerList method in: " + eventClass.getName());
		} catch (SecurityException e) {
			throw new EventException("Unable to access static getHandlerList method in: " + eventClass.getName());
		}
		HandlerList h;
		try {
			h = (HandlerList) m.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EventException("Unable to call static getHandlerList method!", e);
		}
		if (h == null)
			throw new EventException("Event class does not return a HandlerList: " + eventClass.getName());
		return h;
	}

}
