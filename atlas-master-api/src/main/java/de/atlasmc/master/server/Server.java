package de.atlasmc.master.server;

import de.atlasmc.master.node.AtlasNode;

public interface Server extends de.atlasmc.network.server.BaseServer {
	
	AtlasNode getNode();
	
	void setStatus(Status status);
	
	void setState(GameState state);
	
	void setPlayerCount(int count);
	
	void setMaxPlayerCount(int count);
	
	void setMaintenance(boolean value);
	
	ServerGroup getServerGroup();

}
