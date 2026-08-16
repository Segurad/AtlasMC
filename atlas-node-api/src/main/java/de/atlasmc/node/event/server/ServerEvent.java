package de.atlasmc.node.event.server;

import de.atlasmc.event.Event;
import de.atlasmc.network.server.BaseServer;

public abstract class ServerEvent extends Event {

	private final BaseServer server;
	
	public ServerEvent(BaseServer server) {
		if (server == null)
			throw new IllegalArgumentException("Server can not be null!");
		this.server = server;
	}
	
	public BaseServer getServer() {
		return server;
	}
	
}
