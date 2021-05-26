package de.atlasmc.event;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ConcurrentVectorMultimap;
import de.atlasmc.util.map.ListMultimap;

public class ProxyHandlerList extends HandlerList {
	
	private final ListMultimap<LocalProxy, EventExecutor> proxyExecutors;
	
	public ProxyHandlerList() {
		super();
		proxyExecutors = new ConcurrentVectorMultimap<LocalProxy, EventExecutor>();
	}
	
	public synchronized void registerExecutor(LocalProxy proxy, EventExecutor executor) {
		if (proxyExecutors.containsKey(proxy)) {
			register(proxyExecutors.get(proxy), executor);
		} else proxyExecutors.put(proxy, executor);
	}
	
	@Override
	public void registerExecutor(EventExecutor executor, Object... handleroptions) {
		if (handleroptions != null) {
			for (Object o : handleroptions) {
				if (LocalProxy.class.isInstance(o)) {
					registerExecutor((LocalProxy) o, executor);
				} else registerExecutor(executor);
			}
		} else registerExecutor(executor);
	}
	
	public List<EventExecutor> getExecutors(@NotNull LocalProxy proxy) {
		if (!proxyExecutors.containsKey(proxy)) return List.of();
		return proxyExecutors.get(proxy);
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalProxy proxy = ((GenericEvent<LocalProxy, ?>) event).getEventSource();
		final List<EventExecutor> proxyexes = getExecutors(proxy);
		final List<EventExecutor> globalexes = getExecutors();
		int globalIndex = 0, proxyIndex = 0;
		for (EventPriority prio : EventPriority.values()) {
			proxyIndex = fireEvents(proxyexes, prio, event, cancelled, proxyIndex);
			globalIndex = fireEvents(globalexes, prio, event, cancelled, globalIndex);
		}
	}
	
	@Override
	public synchronized void unregisterHandledListener(Listener listener) {
		super.unregisterHandledListener(listener);
		for (LocalProxy proxy : proxyExecutors.keySet()) {
			Iterator<EventExecutor> it = proxyExecutors.get(proxy).iterator();
			while(it.hasNext()) {
				EventExecutor exe = it.next();
				if (exe.getListener() == listener) it.remove();
			}
		}
	}

}
