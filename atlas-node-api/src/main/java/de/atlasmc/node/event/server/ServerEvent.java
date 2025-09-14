package de.atlasmc.node.event.server;

import de.atlasmc.event.Event;
import de.atlasmc.network.server.Server;

public abstract class ServerEvent extends Event {

	private final Server server;
	
	public ServerEvent(Server server) {
		if (server == null)
			throw new IllegalArgumentException("Server can not be null!");
		this.server = server;
	}
	
	public Server getServer() {
		return server;
	}
	
}
