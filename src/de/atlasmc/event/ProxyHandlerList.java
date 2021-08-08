package de.atlasmc.event;

import java.util.Iterator;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.util.ConcurrentLinkedCollection.LinkedCollectionIterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ConcurrentLinkedCollectionMultimap;

public class ProxyHandlerList extends HandlerList {
	
	private final ConcurrentLinkedCollectionMultimap<LocalProxy, EventExecutor> proxyExecutors;
	
	public ProxyHandlerList() {
		super();
		proxyExecutors = new ConcurrentLinkedCollectionMultimap<LocalProxy, EventExecutor>();
	}
	
	public void registerExecutor(LocalProxy proxy, EventExecutor executor) {
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
	
	public LinkedCollectionIterator<EventExecutor> getExecutors(@NotNull LocalProxy proxy) {
		if (!proxyExecutors.containsKey(proxy)) return null;
		return proxyExecutors.get(proxy).iterator();
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		LocalProxy proxy = ((GenericEvent<LocalProxy, ?>) event).getEventSource();
		final LinkedCollectionIterator<EventExecutor> proxyexes = getExecutors(proxy);
		final LinkedCollectionIterator<EventExecutor> globalexes = getExecutors();
		for (EventPriority prio : EventPriority.values()) {
			fireEvents(proxyexes, prio, event, cancelled);
			fireEvents(globalexes, prio, event, cancelled);
		}
		getDefaultExecutor().fireEvent(event);
	}
	
	@Override
	public synchronized void unregisterListener(Listener listener) {
		super.unregisterListener(listener);
		for (LocalProxy proxy : proxyExecutors.keySet()) {
			Iterator<EventExecutor> it = proxyExecutors.get(proxy).iterator();
			while(it.hasNext()) {
				EventExecutor exe = it.next();
				if (exe.getListener() == listener) it.remove();
			}
		}
	}

}
