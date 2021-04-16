package de.atlasmc.event;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;

public class ServerHandlerList extends HandlerList {
	
	private final ListMultimap<ServerGroup, EventExecutor> groupExecutors;
	private final ListMultimap<LocalServer, EventExecutor> serverExecutors;
	
	public ServerHandlerList() {
		super();
		this.groupExecutors = new ArrayListMultimap<ServerGroup, EventExecutor>();
		this.serverExecutors = new ArrayListMultimap<LocalServer, EventExecutor>();
	}
	
	public void registerExecutor(ServerGroup group, EventExecutor executor) {
		synchronized (groupExecutors) {
			if (groupExecutors.containsKey(group)) {
				register(groupExecutors.get(group), executor);
			} else groupExecutors.put(group, executor);
		}
	}
	
	public void registerExecutor(LocalServer server, EventExecutor executor) {
		synchronized (serverExecutors) {
			if (serverExecutors.containsKey(server)) {
				register(serverExecutors.get(server), executor);
			} else serverExecutors.put(server, executor);
		}
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
		synchronized (groupExecutors) {
			if (!groupExecutors.containsKey(group)) return List.of();
			return List.copyOf(groupExecutors.get(group));
		}
	}
	
	public List<EventExecutor> getExecutors(@NotNull LocalServer server) {
		synchronized (serverExecutors) {
			if (!serverExecutors.containsKey(server)) return List.of();
			return List.copyOf(serverExecutors.get(server));
		}
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalServer server = ((GenericEvent<LocalServer, ?>) event).getEventSource();
		final Iterator<EventExecutor> groupexes = getExecutors(server.getGroup()).iterator();
		final Iterator<EventExecutor> serverexes = getExecutors(server).iterator();
		final Iterator<EventExecutor> globalexes = getExecutors().iterator();
		for (EventPriority prio : EventPriority.values()) {
			fireEvents(serverexes, prio, event, cancelled);
			fireEvents(groupexes, prio, event, cancelled);
			fireEvents(globalexes, prio, event, cancelled);
		}
	}
	
	@Override
	public void unregisterHandledListener(Listener listener) {
		super.unregisterHandledListener(listener);
		synchronized (groupExecutors) {
			for (ServerGroup group : groupExecutors.keySet()) {
				Iterator<EventExecutor> it = groupExecutors.get(group).iterator();
				while(it.hasNext()) {
					EventExecutor exe = it.next();
					if (exe.getListener() == listener) it.remove();
				}
			}
		}
		synchronized (serverExecutors) {
			for (LocalServer server : serverExecutors.keySet()) {
				Iterator<EventExecutor> it = serverExecutors.get(server).iterator();
				while(it.hasNext()) {
					EventExecutor exe = it.next();
					if (exe.getListener() == listener) it.remove();
				}
			}
		}
	}
	
	public static void unregisterServer(LocalServer server) {
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
