package de.atlasmc.server;

import java.util.List;

public interface Server {
	
	public ServerGroup getGroup();
	public String getIdentifier();
	public List<? extends ServerPlayer> getPlayers();
	public AtlasNode getNode();
	
}
