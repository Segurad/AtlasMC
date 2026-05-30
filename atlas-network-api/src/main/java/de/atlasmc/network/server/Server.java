package de.atlasmc.network.server;

import java.util.UUID;

import de.atlasmc.network.AtlasNode;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface Server {
	
	@Nullable
	ServerGroup getGroup();
	
	int getPlayerCount();
	
	int getMaxPlayers();
	
	@NotNull
	UUID getServerID();
	
	@NotNull
	String getServerName();
	
	@NotNull
	AtlasNode getNode();

	@NotNull
	String getImplementationName();
	
	@NotNull
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
