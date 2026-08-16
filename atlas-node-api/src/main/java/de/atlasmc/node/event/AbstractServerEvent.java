package de.atlasmc.node.event;

import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.node.server.InternalServer;

/**
 * Abstract base for Events which are located on a certain server
 */
public abstract class AbstractServerEvent extends GenericEvent<InternalServer, ServerHandlerList>{

	public AbstractServerEvent(boolean async, InternalServer eventSource) {
		super(async, eventSource);
	}
	
	public AbstractServerEvent(InternalServer eventSource) {
		super(eventSource);
	}
	
	@Override
	public SyncThreadHolder getSyncThreadHolder() {
		return getEventSource();
	}

}
