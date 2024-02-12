package de.atlasmc.atlasnetwork.server;

import java.util.UUID;

import de.atlasmc.atlasnetwork.AtlasNode;

public interface Server {
	
	ServerGroup getGroup();
	
	int getPlayerCount();
	
	int getMaxPlayers();
	
	UUID getServerID();
	
	String getServerName();
	
	AtlasNode getNode();

	String getImplementationName();
	
}
