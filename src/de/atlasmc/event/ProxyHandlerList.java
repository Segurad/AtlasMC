package de.atlasmc.event;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ArrayListMultimap;
import de.atlasmc.util.map.ListMultimap;

public class ProxyHandlerList extends HandlerList {
	
	private final ListMultimap<LocalProxy, EventExecutor> proxyExecutors;
	
	public ProxyHandlerList() {
		super();
		proxyExecutors = new ArrayListMultimap<LocalProxy, EventExecutor>();
	}
	
	public void registerExecutor(LocalProxy proxy, EventExecutor executor) {
		synchronized (proxyExecutors) {
			if (proxyExecutors.containsKey(proxy)) {
				register(proxyExecutors.get(proxy), executor);
			} else proxyExecutors.put(proxy, executor);
		}
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
		synchronized (proxyExecutors) {
			if (!proxyExecutors.containsKey(proxy)) return List.of();
			return List.copyOf(proxyExecutors.get(proxy));
		}
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalProxy proxy = ((GenericEvent<LocalProxy, ?>) event).getEventSource();
		final Iterator<EventExecutor> proxyexes = getExecutors(proxy).iterator();
		final Iterator<EventExecutor> globalexes = getExecutors().iterator();
		for (EventPriority prio : EventPriority.values()) {
			fireEvents(proxyexes, prio, event, cancelled);
			fireEvents(globalexes, prio, event, cancelled);
		}
	}
	
	@Override
	public void unregisterHandledListener(Listener listener) {
		super.unregisterHandledListener(listener);
		synchronized (proxyExecutors) {
			for (LocalProxy proxy: proxyExecutors.keySet()) {
				Iterator<EventExecutor> it = proxyExecutors.get(proxy).iterator();
				while(it.hasNext()) {
					EventExecutor exe = it.next();
					if (exe.getListener() == listener) it.remove();
				}
			}
		}
	}

}
