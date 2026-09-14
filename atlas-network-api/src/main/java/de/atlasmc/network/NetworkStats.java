package de.atlasmc.network;

import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface NetworkStats {
	
	int getOnlinePlayers();
	
	int getServers();
	
	int getMaxPlayers();
	
	int getNodes();
	
	boolean isMaintenance();

}
