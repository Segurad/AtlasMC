package de.atlasmc.network;

import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface NetworkStats {
	
	/**
	 * Number of players currently within the network
	 * @return count
	 */
	int getPlayers();
	
	/**
	 * Number of servers within the network
	 * @return count
	 */
	int getServers();
	
	/**
	 * Maximum number of players supported by the network
	 * @return count
	 */
	int getMaxPlayers();
	
	/**
	 * Number of nodes within the network
	 * @return
	 */
	int getNodes();
	
	/**
	 * Whether or not the network is in maintenance
	 * @return true if maintenance
	 */
	boolean isMaintenance();

}
