package de.atlasmc.atlasnetwork.server;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.server.NodeServer;

public interface Server {
	
	ServerGroup getGroup();
	
	int getPlayerCount();
	
	int getMaxPlayers();
	
	UUID getServerID();
	
	String getServerName();
	
	AtlasNode getNode();

	String getImplementationName();
	
	Status getStatus();
	
	public static enum Status {
		/**
		 * Server is offline
		 */
		OFFLINE,
		/**
		 * Server is in preparation sequence
		 */
		PREPARATION,
		/**
		 * Server is prepared and awaits start ({@link NodeServer#start()})
		 */
		AWAIT_START,
		/**
		 * Server is starting
		 */
		STARTUP,
		/**
		 * Server is running
		 */
		ONLINE,
		/**
		 * Server is shutting down
		 */
		SHUTDOWN
	}
	
}
