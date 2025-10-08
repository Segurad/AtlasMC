package de.atlasmc.node.event;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.event.Event;
import de.atlasmc.event.EventExecutor;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.log.Log;
import de.atlasmc.network.server.ServerGroup;
import de.atlasmc.node.server.LocalServer;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.util.annotation.NotNull;

public class ServerHandlerList extends HandlerList {
	
	private final Map<ServerGroup, EventExecutor[]> groupExecutors;
	private final Map<LocalServer, EventExecutor[]> serverExecutors;
	
	public ServerHandlerList() {
		super();
		this.groupExecutors = new ConcurrentHashMap<>();
		this.serverExecutors = new ConcurrentHashMap<>();
	}
	
	public void registerExecutor(ServerGroup group, EventExecutor executor) {
		register(group, groupExecutors, executor);
	}
	
	public void registerExecutor(LocalServer server, EventExecutor executor) {
		register(server, serverExecutors, executor);
	}
	
	@Override
	public void registerExecutor(EventExecutor executor, Object... handleroptions) {
		if (handleroptions != null) {
			for (Object o : handleroptions) {
				if (ServerGroup.class.isInstance(o)) {
					registerExecutor((ServerGroup) o, executor);
				} else if (LocalServer.class.isInstance(o)) {
					registerExecutor((LocalServer) o, executor);
				} else registerExecutor(executor);
			}
		} else registerExecutor(executor);
	}
	
	/**
	 * Returns the iterator or null if ServerGroup not present
	 * @param group
	 * @return iterator or null
	 */
	public Iterator<EventExecutor> getExecutors(@NotNull ServerGroup group) {
		return getContextIterator(group, groupExecutors);
	}
	
	/**
	 * Returns the iterator or null if LocalServer not present
	 * @param server
	 * @return iterator or null
	 */
	public Iterator<EventExecutor> getExecutors(@NotNull LocalServer server) {
		return getContextIterator(server, serverExecutors);
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		final LocalServer server = ((GenericEvent<LocalServer, ?>) event).getEventSource();
		final Log log = server.getLogger();
		final EventExecutor[] groupexes = this.groupExecutors.get(server.getGroup());
		final EventExecutor[] serverexes = this.serverExecutors.get(server);
		final EventExecutor[] globalexes = this.globalExecutors;
		final EventExecutor defaultHandler = this.defaultExecutor;
		if (groupexes != null || serverexes != null || globalexes != null) {
			int serverIndex = 0;
			int groupIndex = 0;
			int globalIndex = 0;
			final int monitor = EventPriority.MONITOR.ordinal();
			for (int i = 0; i <= monitor; i++) {
				if (i == monitor)
					fireDefaultExecutor(defaultHandler, event, log);
				serverIndex = fireEvents(serverexes, serverIndex, i, event, cancelled, log);
				groupIndex = fireEvents(groupexes, groupIndex, i, event, cancelled, log);
				globalIndex = fireEvents(globalexes, globalIndex, i, event, cancelled, log);
			}
		} else {
			fireDefaultExecutor(defaultHandler, event, log);
		}
	}
	
	@Override
	public void unregisterListener(Listener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Listener can not be null!");
		modLock.lock();
		globalExecutors = super.internalUnregister(listener, globalExecutors);
		super.internalUnregister(listener, serverExecutors);
		super.internalUnregister(listener, groupExecutors);
		modLock.unlock();
	}
	
	@Override
	public synchronized void unregisterListener(PluginHandle plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		modLock.lock();
		globalExecutors = super.internalUnregister(plugin, globalExecutors);
		super.internalUnregister(plugin, serverExecutors);
		super.internalUnregister(plugin, groupExecutors);
		modLock.unlock();
	}
	
	public static void unregisterServer(LocalServer server) {
		if (server == null) 
			throw new IllegalArgumentException("Server can not be null!");
		synchronized (HANDLERS) {
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				HandlerList h = ref.get();
				if (!(h instanceof ServerHandlerList list)) 
					continue;
				list.serverExecutors.remove(server);
			}
		}
	}

}
