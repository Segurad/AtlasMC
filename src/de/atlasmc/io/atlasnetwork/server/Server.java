package de.atlasmc.io.atlasnetwork.server;

import java.util.List;

import de.atlasmc.io.atlasnetwork.AtlasNode;

public interface Server {
	
	public ServerGroup getGroup();
	public String getIdentifier();
	public List<? extends AtlasPlayer> getPlayers();
	public AtlasNode getNode();
	
}
