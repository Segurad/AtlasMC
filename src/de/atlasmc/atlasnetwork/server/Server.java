package de.atlasmc.atlasnetwork.server;

import de.atlasmc.atlasnetwork.AtlasNode;

public interface Server {
	
	public ServerGroup getGroup();
	public String getIdentifier();
	public int getPlayerCount();
	public int getMaxPlayers();
	public int getServerID();
	public AtlasNode getNode();
	
}
