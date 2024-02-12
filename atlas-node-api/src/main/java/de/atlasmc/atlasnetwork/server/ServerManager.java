package de.atlasmc.atlasnetwork.server;

public interface ServerManager {
	
	ServerGroup getFallBack();
	
	ServerGroup getServerGroup(String name);

}
