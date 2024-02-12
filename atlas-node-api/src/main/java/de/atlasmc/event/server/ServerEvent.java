package de.atlasmc.event.server;

import de.atlasmc.atlasnetwork.server.Server;
import de.atlasmc.event.Event;

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
