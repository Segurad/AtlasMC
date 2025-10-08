package de.atlasmc.node.event;

import de.atlasmc.event.ContextHandlerList;
import de.atlasmc.node.io.socket.NodeSocket;

public class SocketHandlerList extends ContextHandlerList<NodeSocket> {
	
	public SocketHandlerList() {
		super(NodeSocket.class, NodeSocket::getLogger);
	}

}
