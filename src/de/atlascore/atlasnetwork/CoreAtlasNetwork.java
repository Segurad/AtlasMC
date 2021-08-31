package de.atlascore.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.atlasnetwork.AtlasNode;
import de.atlasmc.atlasnetwork.server.ServerGroup;

public class CoreAtlasNetwork implements AtlasNetwork {
	
	private List<ServerGroup> groups;
	private List<AtlasNode> nodes;
 	private AtlasNode master;
	private ServerGroup fallBack;
	private int maxplayers, players;
	
	public CoreAtlasNetwork() {
		
	}
	
	public AtlasNode getMaster() {
		return master;
	}
	
	public ServerGroup getFallBack() {
		return fallBack;
	}
	
	public void setFallBack(ServerGroup group) {
		this.fallBack = group;
	}

	public int getOnlinePlayerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
