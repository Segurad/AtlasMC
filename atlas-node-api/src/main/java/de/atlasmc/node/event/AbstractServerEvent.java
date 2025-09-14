package de.atlasmc.node.event;

import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.node.server.LocalServer;

/**
 * Abstract base for Events which are located on a certain server
 */
public abstract class AbstractServerEvent extends GenericEvent<LocalServer, ServerHandlerList>{

	public AbstractServerEvent(boolean async, LocalServer eventSource) {
		super(async, eventSource);
	}
	
	public AbstractServerEvent(LocalServer eventSource) {
		super(eventSource);
	}
	
	@Override
	public SyncThreadHolder getSyncThreadHolder() {
		return getEventSource();
	}

}
