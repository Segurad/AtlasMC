package de.atlasmc.event.socket;

import de.atlasmc.event.GenericEvent;
import de.atlasmc.event.ProxyHandlerList;
import de.atlasmc.event.SyncThreadHolder;
import de.atlasmc.socket.NodeSocket;

public abstract class SocketEvent extends GenericEvent<NodeSocket, ProxyHandlerList> {

	public SocketEvent(NodeSocket eventSource) {
		this(false, eventSource);
	}
	
	public SocketEvent(boolean async, NodeSocket eventSource) {
		super(async, eventSource);
	}
	
	@Override
	public SyncThreadHolder getSyncThreadHolder() {
		return getEventSource();
	}

}
