package de.atlasmc.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.ConcurrentLinkedCollection;
import de.atlasmc.util.ConcurrentLinkedCollection.LinkedCollectionIterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.ThreadSafe;

/**
 * This class holds all {@link EventExecutor} for specific Events
 */
@ThreadSafe
public class HandlerList {
	
	protected static final List<HandlerList> HANDLERS = new ArrayList<>();
	private static int lowID;
	private final ConcurrentLinkedCollection<EventExecutor> globalExecutors;
	private final int handlerID;
	private EventExecutor defaultExecutor;
	
	public HandlerList() {
		this.defaultExecutor = EventExecutor.NULL_EXECUTOR;
		this.globalExecutors = new ConcurrentLinkedCollection<>();
		handlerID = registerHandlerList();
	}
	
	/**
	 * Sets the DefaultEventExecutor<br>
	 * <br>
	 * The DefaultEventExecutor is always the last EventExecutor called, ignoring the flags ({@link EventPriority} and ignoreCancelled)
	 * @param defaultExecutor
	 */
	public void setDefaultExecutor(EventExecutor defaultExecutor) {
		if (defaultExecutor == null) defaultExecutor = EventExecutor.NULL_EXECUTOR;
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
	 * Registers a HandlerList and returns the ID
	 * @param handler
	 * @return the handler's ID
	 */
	private final int registerHandlerList() {
		synchronized (HANDLERS) {
			final int size = HANDLERS.size();
			int id = -1;
			if (lowID == size) {
				HANDLERS.add(lowID, this);
				id = lowID;
				lowID++;
			} else {
				HANDLERS.set(lowID, this);
				id = lowID;
				boolean result = false;
				for (int i = lowID; i < size; i++) {
					if (HANDLERS.get(i) != null) continue;
					lowID = i;
					result = true;
					break;
				}
				if (!result) lowID = size;
			}
			return id;
		}
	}
	
	public void unregister() {
		synchronized (HANDLERS) {
			int id = this.getHandlerID();
			HANDLERS.set(id, null);
			if (id < lowID) lowID = id;
		}
	}
	
	public final int getHandlerID() {
		return handlerID;
	}
	
	public void registerExecutor(@NotNull EventExecutor executor) {
		if (executor == null) return;
		register(globalExecutors, executor);
	}
	
	public void registerExecutor(EventExecutor executor, Object... handleroptions) {
		registerExecutor(executor);
	}
	
	protected synchronized void register(ConcurrentLinkedCollection<EventExecutor> exes, EventExecutor executor) {
		LinkedCollectionIterator<EventExecutor> it = exes.iterator();
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
			}
		}
		exes.add(executor);
	}
	
	/**
	 * 
	 * @return a immutable copy of all registered GlobalExecutors
	 */
	public LinkedCollectionIterator<EventExecutor> getExecutors() {
		return globalExecutors.iterator();
	}
	
	public static void callEvent(@NotNull final Event event) {
		final HandlerList hl = event.getHandlers();
		final boolean cancelled = Cancellable.class.isInstance(event);
		hl.callEvent(event, cancelled);
	}
	
	/**
	 * Calls all EventExecutors of this HandlerList <br>
	 * This Method will be called by the static Method {@link #callEvent(Event)} and should be used for children to fire Events
	 * @param event
	 * @param cancellable
	 */
	protected void callEvent(final Event event, boolean cancellable) {
		for (EventExecutor exe : globalExecutors){
			if (exe.getIgnoreCancelled() && cancellable) continue;
			exe.fireEvent(event);
		}
		getDefaultExecutor().fireEvent(event);
	}
	
	/**
	 * Fires the Event for all Executors with a lower or equal priority
	 * @param executors
	 * @param priority
	 * @param event event
	 * @param cancellable weather or not the event extends {@link Cancellable}
	 */
	protected static void fireEvents(final LinkedCollectionIterator<EventExecutor> executors, final EventPriority priority, final Event event, final boolean cancellable) {
		if (executors == null || !executors.hasNext()) return;
		final int prio = priority.ordinal();
		while (executors.hasNext()) {
			EventExecutor exe = executors.next();
			if (exe.getPriority().ordinal() > prio) return;
			if (exe.getIgnoreCancelled() && (cancellable ? false : ((Cancellable) event).isCancelled())) continue;
			exe.fireEvent(event);
		}
	}
	
	/**
	 * Unregisters the Listener from all HandlerLists
	 * @param listener
	 */
	public static void unregisterListenerGlobal(@NotNull Listener listener) {
		synchronized (HANDLERS) {
			for (HandlerList h : HANDLERS) {
				h.unregisterListener(listener);
			}
		}
	}

	/**
	 * Unregisters the Listener from this HandlerList
	 * @param listener
	 */
	public synchronized void unregisterListener(Listener listener) {
		Iterator<EventExecutor> it = globalExecutors.iterator();
		while(it.hasNext()) {
			EventExecutor exe = it.next();
			if (exe.getListener() == listener) it.remove();
		}
	}
	
	/**
	 * 
	 * @param listener
	 * @param handleroptions extra arguments for EventExecutor registration <br>
	 * If the Listener contains a e.g. a {@link AbstractServerEvent}, you may add a {@link LocalServer} or {@link ServerGroup} for registration.
	 * If there are multiple LocalServers or/and ServerGroups it will registered for all.
	 * If not present or does not contain a specific Object the EventHandler will be registered as Global.
	 */
	public static void registerListener(Listener listener, Object... handleroptions) {
		if (listener == null) return;
		List<EventExecutor> exes = getExecutors(listener);
		for (EventExecutor exe : exes) {
			try {
				Method m = exe.getEventClass().getMethod("getHandlerList", null);
				HandlerList h = (HandlerList) m.invoke(null, null);
				h.registerExecutor(exe, handleroptions);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException 
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<EventExecutor> getExecutors(Listener listener) {
		if (listener == null) return null;
		List<EventExecutor> executors = new ArrayList<>();
		for (Method method : listener.getClass().getMethods()) {
			EventHandler handler = method.getAnnotation(EventHandler.class);
			if (handler == null) continue;
			if (method.getParameterCount() != 1) continue;
			Class<?>[] params = method.getParameterTypes();
			if (!params[0].isAssignableFrom(Event.class)) continue;
			method.setAccessible(true);
			executors.add(new EventExecutor(params[0], method, handler.priority(), handler.ignoreCancelled(), listener));
		}
		return executors;
	}

}
