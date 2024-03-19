package de.atlasmc.event.proxy;

import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ProxyHandlerList;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.proxy.LocalProxy;

public abstract class ProxyEvent extends GenericEvent<LocalProxy, ProxyHandlerList> {

	public ProxyEvent(LocalProxy eventSource) {
		this(false, eventSource);
	}
	
	public ProxyEvent(boolean async, LocalProxy eventSource) {
		super(async, eventSource);
	}
	
	@Override
	public SyncThreadHolder getSyncThreadHolder() {
		return getEventSource();
	}

}
