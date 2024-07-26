package de.atlasmc.master.server;

import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.ServerConfig;
import de.atlasmc.master.node.AtlasNode;

public interface ServerGroup extends de.atlasmc.atlasnetwork.server.ServerGroup {
	
	NamespacedKey getDeploymentMethod();
	
	int getMemoryThreshold();
	
	float getMemoryUtilisation();
	
	boolean isInternal();
	
	/**
	 * Returns whether or not this group is unsatisfied.
	 * @return true if unsatisfied
	 */
	boolean isUnsatisfied();
	
	void setTimeout(int timeout);
	
	int getTimeout();
	
	Server getServer(UUID uuid);
	
	Server registerServer(UUID uuid, AtlasNode node);
	
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

	void updatePlayerCount(Server server, int oldCount, int newCount);

	void updateMaxPlayerCount(Server server, int oldMax, int newMax);

}
