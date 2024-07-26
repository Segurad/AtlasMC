package de.atlasmc.atlasnetwork.server;

import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface ServerGroup {
	
	ServerConfig getServerConfig();
	
	int getMaxServers();
	
	int getMaxNonFullServers();
	
	int getMinServers();
	
	int getMinNonFullServers();
	
	int getNewServerDelay();
	
	float getNewServerOnUserLoad();
	
	boolean isMaintenance();
	
	String getName();

}
