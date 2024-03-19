package de.atlasmc.event;

import de.atlasmc.server.LocalServer;

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
