package de.atlasmc.event;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;

import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.server.LocalServer;
import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ConcurrentLinkedListMultimap;

public class ServerHandlerList extends HandlerList {
	
	private final ConcurrentLinkedListMultimap<ServerGroup, EventExecutor> groupExecutors;
	private final ConcurrentLinkedListMultimap<LocalServer, EventExecutor> serverExecutors;
	
	public ServerHandlerList() {
		super();
		this.groupExecutors = new ConcurrentLinkedListMultimap<>();
		this.serverExecutors = new ConcurrentLinkedListMultimap<>();
	}
	
	public void registerExecutor(ServerGroup group, EventExecutor executor) {
		if (group == null || executor == null) 
			return;
		if (groupExecutors.containsKey(group)) {
			register(groupExecutors.get(group), executor);
		} else 
			groupExecutors.put(group, executor);
	}
	
	public void registerExecutor(LocalServer server, EventExecutor executor) {
		if (server == null || executor == null) 
			return;
		if (serverExecutors.containsKey(server)) {
			register(serverExecutors.get(server), executor);
		} else 
			serverExecutors.put(server, executor);
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
	 * Returns the LinkedListIterator or null if ServerGroup not present
	 * @param group
	 * @return LinkedListIterator or null
	 */
	public LinkedListIterator<EventExecutor> getExecutors(@NotNull ServerGroup group) {
		if (group == null) 
			return null;
		final ConcurrentLinkedList<EventExecutor> list = groupExecutors.get(group);
		if (list == null || list.isEmpty()) 
			return null;
		return list.iterator();
	}
	
	/**
	 * Returns the LinkedListIterator or null if LocalServer not present
	 * @param server
	 * @return LinkedListIterator or null
	 */
	public LinkedListIterator<EventExecutor> getExecutors(@NotNull LocalServer server) {
		if (server == null) 
			return null;
		final ConcurrentLinkedList<EventExecutor> list = serverExecutors.get(server);
		if (list == null || list.isEmpty()) 
			return null;
		return list.iterator();
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		final LocalServer server = ((GenericEvent<LocalServer, ?>) event).getEventSource();
		final Log log = server.getLogger();
		final LinkedListIterator<EventExecutor> groupexes = getExecutors(server.getGroup());
		final LinkedListIterator<EventExecutor> serverexes = getExecutors(server);
		final LinkedListIterator<EventExecutor> globalexes = getExecutors();
		if (groupexes != null || serverexes != null || globalexes != null) {
			for (EventPriority prio : EventPriority.getValues()) {
				if (prio == EventPriority.MONITOR)
					fireDefaultExecutor(event, log);
				fireEvents(serverexes, prio, event, cancelled, log);
				fireEvents(groupexes, prio, event, cancelled, log);
				fireEvents(globalexes, prio, event, cancelled, log);
			}
		} else {
			fireDefaultExecutor(event, log);
		}
	}
	
	@Override
	public synchronized void unregisterListener(Listener listener) {
		if (listener == null) 
			return;
		super.unregisterListener(listener);
		for (ServerGroup group : groupExecutors.keySet()) {
			Iterator<EventExecutor> it = groupExecutors.get(group).iterator();
			while(it.hasNext()) {
				EventExecutor exe = it.next();
				if (exe.getListener() == listener) 
					it.remove();
			}
		}
		for (LocalServer server : serverExecutors.keySet()) {
			internalUnregister(listener, serverExecutors.get(server));
		}
	}
	
	@Override
	public synchronized void unregisterListener(PluginHandle plugin) {
		super.unregisterListener(plugin);
		for (Collection<EventExecutor> executors : groupExecutors.values()) {
			internalUnregister(plugin, executors);
		}
		for (Collection<EventExecutor> executors : serverExecutors.values()) {
			internalUnregister(plugin, executors);
		}
	}
	
	public static void unregisterServer(LocalServer server) {
		if (server == null) 
			return;
		synchronized (HANDLERS) {
			for (WeakReference<HandlerList> ref : HANDLERS) {
				if (ref.refersTo(null))
					continue;
				HandlerList h = ref.get();
				if (!(h instanceof ServerHandlerList)) 
					continue;
				ServerHandlerList sh = (ServerHandlerList) h;
				sh.serverExecutors.remove(server);
			}
		}
	}

}
