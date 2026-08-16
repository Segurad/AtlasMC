package de.atlasmc.network.server;

import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

/**
 * Common Server
 */
public interface BaseServer {
	
	/**
	 * The groups name the server is in null if none
	 * @return name or null
	 */
	@Nullable
	String getGroup();
	
	int getPlayerCount();
	
	int getMaxPlayers();
	
	/**
	 * Whether or not the Server is in maintenance
	 * @return
	 */
	boolean isMaintenance();
	
	/**
	 * UUID of the Server
	 * @return
	 */
	@NotNull
	UUID getID();
	
	@NotNull
	String getServerName();
	
	@NotNull
	UUID getNodeID();

	@NotNull
	String getImplementationName();
	
	@NotNull
	Status getStatus();
	
	@NotNull
	GameState getState();
	
	public static enum GameState {
		
		/**
		 * Game is in lobby phase waiting for players
		 */
		LOBBY,
		/**
		 * Game is starting
		 */
		STARTING,
		/**
		 * Game is running
		 */
		IN_GAME,
		/**
		 * Game is ended
		 */
		ENDED,
		/**
		 * Game is in non valid state or {@link Server#getStatus()} is not {@link Status#ONLINE}
		 */
		UNAVAILABLE;
		
	}
	
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
