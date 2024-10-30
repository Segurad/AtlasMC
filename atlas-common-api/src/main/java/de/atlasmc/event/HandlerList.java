package de.atlasmc.event;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.Atlas;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * This class holds all {@link EventExecutor} for the Event it is used
 */
@ThreadSafe
public class HandlerList {
	
	protected static final List<WeakReference<HandlerList>> HANDLERS = new CopyOnWriteArrayList<>();
	private static int lowID;
	
	private final ConcurrentLinkedList<EventExecutor> globalExecutors;
	private final int handlerID;
	private EventExecutor defaultExecutor;
	
	public HandlerList() {
		this.defaultExecutor = EventExecutor.NULL_EXECUTOR;
		this.globalExecutors = new ConcurrentLinkedList<>();
		handlerID = registerHandlerList();
	}
	
	/**
	 * Sets the DefaultEventExecutor<br>
	 * <br>
	 * The DefaultEventExecutor is always the last EventExecutor called, ignoring the flags ({@link EventPriority} and ignoreCancelled)
	 * @param defaultExecutor
	 */
	public void setDefaultExecutor(EventExecutor defaultExecutor) {
		if (defaultExecutor == null) 
			defaultExecutor = EventExecutor.NULL_EXECUTOR;
		this.defaultExecutor = defaultExecutor;
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
	protected void fireDefaultExecutor(Event event, Log log) {
		try {
			defaultExecutor.fireEvent(event);
		} catch (InvocationTargetException ex) {
			log.error("Error while event handling with default handler for: " + event.getName(), ex.getCause());
		} catch (Exception ex) {
			log.error("Error while event handling with default handler for: " + event.getName(), ex);
		}
	}
	
	/**
	 * Registers a HandlerList and returns the ID
	 * @param handler
	 * @return the handler's ID
	 */
	private final int registerHandlerList() {
		synchronized (HANDLERS) {
			final int size = HANDLERS.size();
			int id = -1;
			if (lowID == size) {
				HANDLERS.add(lowID, new WeakReference<>(this));
				id = lowID;
				lowID++;
			} else {
				HANDLERS.set(lowID, new WeakReference<>(this));
				id = lowID;
				boolean result = false;
				for (int i = lowID; i < size; i++) {
					if (HANDLERS.get(i) != null) 
						continue;
					lowID = i;
					result = true;
					break;
				}
				if (!result) lowID = size;
			}
			return id;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		synchronized (HANDLERS) {
			int id = this.getHandlerID();
			HANDLERS.set(id, null);
			if (id < lowID) 
				lowID = id;
		}
	}
	
	public final int getHandlerID() {
		return handlerID;
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
	 * Inserts a executor to the list and groups it with other executors of the same priority
	 * @param exes
	 * @param executor
	 */
	protected synchronized void register(ConcurrentLinkedList<EventExecutor> exes, EventExecutor executor) {
		LinkedListIterator<EventExecutor> it = exes.iterator();
		final int ordinal = executor.getPriority().ordinal();
		while(it.hasNext()) {
			EventExecutor exe = it.next();
			if (exe.getPriority().ordinal() > ordinal) {
				if (it.hasPrevious()) {
					it.previous();
					it.add(executor);
					return;
				}
				exes.addFirst(executor);
				return;
			}
		}
		exes.add(executor);
	}
	
	/**
	 * Returns a iterator for the global executors or null if no executors present
	 * @return iterator or null
	 */
	@Nullable
	public LinkedListIterator<EventExecutor> getExecutors() {
		if (globalExecutors.isEmpty())
			return null;
		return globalExecutors.iterator();
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
			} else {
				if (!Atlas.isMainThread())
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
		final Log logger = Atlas.getLogger();
		if (globalExecutors.isEmpty()) {
			fireDefaultExecutor(event, logger);
			return;
		}
		boolean defaultHandler = false;
		for (EventExecutor exe : globalExecutors){
			if (exe.getPriority() == EventPriority.MONITOR && !defaultHandler) {
				defaultHandler = true;
				fireDefaultExecutor(event, logger);
			}
			try {
				exe.fireEvent(event);
			} catch (Exception ex) {
				exe.getPlugin().getPlugin().getLogger().error("Error while event handling for: " + event.getName(), ex);
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
	 */
	protected static void fireEvents(final LinkedListIterator<EventExecutor> executors, final EventPriority priority, final Event event, final boolean cancellable, Log log) {
		if (executors == null || !executors.hasNext()) 
			return;
		final int prio = priority.ordinal();
		for (EventExecutor exe = executors.peekNext(); exe != null; exe = executors.peekNext()) {
			if (exe.getPriority().ordinal() > prio) 
				break;
			executors.gotoPeeked();
			if (exe.getIgnoreCancelled() && (!cancellable && ((Cancellable) event).isCancelled()))
				continue;
			try {
				exe.fireEvent(event);
			} catch (Exception ex) {
				exe.getPlugin().getPlugin().getLogger().error("Error while event handling for: " + event.getName(), ex);
			}
		}
	}
	
	/**
	 * Unregisters the Listener from all HandlerLists
	 * @param listener
	 */
	public static void unregisterListenerGlobal(@NotNull Listener listener) {
		synchronized (HANDLERS) {
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				ref.get().unregisterListener(listener);
			}
		}
	}
	
	/**
	 * Unregisters the Listener of a Plugin from all HandlerLists
	 * @param plugin
	 */
	public static void unregisterListenerGloabal(@NotNull PluginHandle plugin) {
		synchronized (HANDLERS) {
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				ref.get().unregisterListener(plugin);
			}
		}
	}

	/**
	 * Unregisters the Listener from this HandlerList
	 * @param listener
	 */
	public synchronized void unregisterListener(Listener listener) {
		internalUnregister(listener, globalExecutors);
	}
	
	public synchronized void unregisterListener(PluginHandle plugin) {
		internalUnregister(plugin, globalExecutors);
	}
	
	protected void internalUnregister(Listener listener, Collection<EventExecutor> executors) {
		if (listener == null)
			return;
		if (executors.isEmpty())
			return;
		Iterator<EventExecutor> it = executors.iterator();
		while(it.hasNext()) {
			EventExecutor exe = it.next();
			if (exe.getListener() == listener) 
				it.remove();
		}
	}
	
	protected void internalUnregister(PluginHandle plugin, Collection<EventExecutor> executors) {
		if (plugin == null)
			return;
		if (executors.isEmpty())
			return;
		Iterator<EventExecutor> it = executors.iterator();
		while(it.hasNext()) {
			EventExecutor exe = it.next();
			if (exe.getPlugin() == plugin) 
				it.remove();
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
			return;
		List<EventExecutor> exes = MethodEventExecutor.getExecutors(plugin, listener);
		for (EventExecutor exe : exes) {
			HandlerList handlers = getHandlerListOf(exe.getEventClass());
			handlers.registerExecutor(exe, context);
		}
	}
	
	public static <E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, Object... context) {
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
		HandlerList h = null;
		try {
			m.setAccessible(true);
			h = (HandlerList) m.invoke(null);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new EventException("Unable to call static getHandlerList method!", e);
		}
		if (h == null)
			throw new EventException("Event class does not return a HandlerList: " + eventClass.getName());
		return h;
	}

}
