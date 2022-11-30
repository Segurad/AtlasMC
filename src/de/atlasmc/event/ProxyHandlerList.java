package de.atlasmc.event;

import java.util.Iterator;
import de.atlasmc.atlasnetwork.proxy.LocalProxy;
import de.atlasmc.log.Logger;
import de.atlasmc.util.ConcurrentLinkedList.LinkedListIterator;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.map.ConcurrentLinkedListMultimap;

public class ProxyHandlerList extends HandlerList {
	
	private final ConcurrentLinkedListMultimap<LocalProxy, EventExecutor> proxyExecutors;
	
	public ProxyHandlerList() {
		super();
		proxyExecutors = new ConcurrentLinkedListMultimap<LocalProxy, EventExecutor>();
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
	
	/**
	 * Returns the LinkedListIterator or null if LocalProxy not present
	 * @param proxy
	 * @return LinkedListIterator or null
	 */
	public LinkedListIterator<EventExecutor> getExecutors(@NotNull LocalProxy proxy) {
		if (!proxyExecutors.containsKey(proxy)) return null;
		return proxyExecutors.get(proxy).iterator();
	}
	
	@Override
	protected void callEvent(Event event, boolean cancelled) {
		@SuppressWarnings("unchecked")
		final LocalProxy proxy = ((GenericEvent<LocalProxy, ?>) event).getEventSource();
		final Logger log = proxy.getLogger();
		final LinkedListIterator<EventExecutor> proxyexes = getExecutors(proxy);
		final LinkedListIterator<EventExecutor> globalexes = getExecutors();
		if ((proxyexes != null && proxyexes.hasNext()) || globalexes.hasNext()) {
			for (EventPriority prio : EventPriority.getValues()) {
				if (prio == EventPriority.MONITOR) 
					fireDefaultExecutor(event, log);
				fireEvents(proxyexes, prio, event, cancelled, log);
				fireEvents(globalexes, prio, event, cancelled, log);
			}
		} else {
			fireDefaultExecutor(event, log);
		}
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
