package de.atlasmc.event;

import java.util.Iterator;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
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
		if (group == null || executor == null) return;
		if (groupExecutors.containsKey(group)) {
			register(groupExecutors.get(group), executor);
		} else groupExecutors.put(group, executor);
	}
	
	public void registerExecutor(LocalServer server, EventExecutor executor) {
		if (server == null || executor == null) return;
		if (serverExecutors.containsKey(server)) {
			register(serverExecutors.get(server), executor);
		} else serverExecutors.put(server, executor);
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
	 * @param proxy
	 * @return LinkedListIterator or null
	 */
	public LinkedListIterator<EventExecutor> getExecutors(@NotNull ServerGroup group) {
		if (group == null) return null;
		if (!groupExecutors.containsKey(group)) return null;
		return groupExecutors.get(group).iterator();
	}
	
	/**
	 * Returns the LinkedListIterator or null if LocalServer not present
	 * @param proxy
	 * @return LinkedListIterator or null
	 */
	public LinkedListIterator<EventExecutor> getExecutors(@NotNull LocalServer server) {
		if (server == null) return null;
		if (!serverExecutors.containsKey(server)) return null;
		return serverExecutors.get(server).iterator();
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalServer server = ((GenericEvent<LocalServer, ?>) event).getEventSource();
		if (!server.isServerThread() && !event.isAsynchronous()) 
			throw new RuntimeException("Tryed to call ServerEvent async: " + event.getName());
		final LinkedListIterator<EventExecutor> groupexes = getExecutors(server.getGroup());
		final LinkedListIterator<EventExecutor> serverexes = getExecutors(server);
		final LinkedListIterator<EventExecutor> globalexes = getExecutors();
		if ((groupexes != null && groupexes.hasNext()) || 
				(serverexes != null && serverexes.hasNext()) || 
				globalexes.hasNext()) {
			for (EventPriority prio : EventPriority.values()) {
				if (prio == EventPriority.MONITOR) getDefaultExecutor().fireEvent(event);
				fireEvents(serverexes, prio, event, cancelled);
				fireEvents(groupexes, prio, event, cancelled);
				fireEvents(globalexes, prio, event, cancelled);
			}
		} else {
			getDefaultExecutor().fireEvent(event);
		}
	}
	
	@Override
	public synchronized void unregisterListener(Listener listener) {
		if (listener == null) return;
		super.unregisterListener(listener);
		for (ServerGroup group : groupExecutors.keySet()) {
			Iterator<EventExecutor> it = groupExecutors.get(group).iterator();
			while(it.hasNext()) {
				EventExecutor exe = it.next();
				if (exe.getListener() == listener) it.remove();
			}
		}
		for (LocalServer server : serverExecutors.keySet()) {
			Iterator<EventExecutor> it = serverExecutors.get(server).iterator();
			while(it.hasNext()) {
				EventExecutor exe = it.next();
				if (exe.getListener() == listener) it.remove();
			}
		}
		
	}
	
	public static void unregisterServer(LocalServer server) {
		if (server == null) return;
		synchronized (HANDLERS) {
			for (HandlerList h : HANDLERS) {
				if (!(h instanceof ServerHandlerList)) continue;
				ServerHandlerList sh = (ServerHandlerList) h;
				sh.serverExecutors.remove(server);
			}
		}
	}

}
