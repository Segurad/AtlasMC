package de.atlasmc.atlasnetwork;

import de.atlasmc.atlasnetwork.server.ServerGroup;

public interface AtlasNetwork {
	
	public AtlasNode getMaster();
	
	public ServerGroup getFallBack();
	
	public void setFallBack(ServerGroup group);

	public int getOnlinePlayerCount();

}
