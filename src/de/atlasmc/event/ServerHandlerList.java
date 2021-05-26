package de.atlasmc.event;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ConcurrentVectorMultimap;
import de.atlasmc.util.map.ListMultimap;

public class ServerHandlerList extends HandlerList {
	
	private final ListMultimap<ServerGroup, EventExecutor> groupExecutors;
	private final ListMultimap<LocalServer, EventExecutor> serverExecutors;
	
	public ServerHandlerList() {
		super();
		this.groupExecutors = new ConcurrentVectorMultimap<>();
		this.serverExecutors = new ConcurrentVectorMultimap<>();
	}
	
	public synchronized void registerExecutor(ServerGroup group, EventExecutor executor) {
		if (group == null || executor == null) return;
		if (groupExecutors.containsKey(group)) {
			register(groupExecutors.get(group), executor);
		} else groupExecutors.put(group, executor);
	}
	
	public synchronized void registerExecutor(LocalServer server, EventExecutor executor) {
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
	
	public List<EventExecutor> getExecutors(@NotNull ServerGroup group) {
		if (group == null) return null;
		if (!groupExecutors.containsKey(group)) return List.of();
		return groupExecutors.get(group);
	}
	
	public List<EventExecutor> getExecutors(@NotNull LocalServer server) {
		if (server == null) return null;
		if (!serverExecutors.containsKey(server)) return List.of();
		return serverExecutors.get(server);
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalServer server = ((GenericEvent<LocalServer, ?>) event).getEventSource();
		if (!server.isServerThread() && !event.isAsynchronous()) 
			throw new RuntimeException("Tryed to call ServerEvent async: " + event.getName());
		final List<EventExecutor> groupexes = getExecutors(server.getGroup());
		final List<EventExecutor> serverexes = getExecutors(server);
		final List<EventExecutor> globalexes = getExecutors();
		int groupIndex = 0, serverIndex = 0, globalIndex = 0;
		for (EventPriority prio : EventPriority.values()) {
			serverIndex = fireEvents(serverexes, prio, event, cancelled, serverIndex);
			groupIndex = fireEvents(groupexes, prio, event, cancelled, groupIndex);
			globalIndex = fireEvents(globalexes, prio, event, cancelled, globalIndex);
		}
	}
	
	@Override
	public synchronized void unregisterHandledListener(Listener listener) {
		if (listener == null) return;
		super.unregisterHandledListener(listener);
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
				if (!ServerHandlerList.class.isInstance(h)) continue;
				ServerHandlerList sh = (ServerHandlerList) h;
				synchronized (sh.serverExecutors) {
					sh.serverExecutors.remove(server);
				}
			}
		}
	}

}
