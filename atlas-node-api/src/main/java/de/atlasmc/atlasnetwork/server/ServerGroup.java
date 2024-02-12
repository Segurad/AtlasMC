package de.atlasmc.atlasnetwork.server;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.concurrent.future.Future;

@ThreadSafe
public interface ServerGroup {
	
	// config
	
	ServerConfig getServerConfig();
	
	int getMaxServers();
	
	int getMaxNonFullServers();
	
	int getMinServers();
	
	int getMinNonFullServers();
	
	int getNewServerDelay();
	
	float getNewServerOnUserLoad();
	
	boolean isMaintenance();
	
	String getName();
	
	// runtime
	
	LocalServer getLocalServer(UUID uuid);
	
	Future<Server> getServer(UUID uuid);
	
	Collection<LocalServer> getLocalServers();
	
	/**
	 * Returns the number of server marked as full
	 * @return full server count
	 */
	int getFullServerCount();
	
	int getServerCount();
	
	/**
	 * Returns the maximum number of supported slots of all servers
	 * @return slots
	 */
	int getCurrentPlayerCapacity();
	
	/**
	 * Returns the number of player in this group
	 * @return player count
	 */
	int getPlayerCount();

}
