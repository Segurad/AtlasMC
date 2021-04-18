package de.atlasmc.atlasnetwork;

import java.util.List;

import de.atlasmc.atlasnetwork.server.ServerGroup;

public class AtlasNetwork {
	
	private List<ServerGroup> groups;
	private List<AtlasNode> nodes;
 	private AtlasNode master;
	private ServerGroup fallBack;
	private int maxplayers, players;
	
	public AtlasNode getMaster() {
		return master;
	}
	
	public ServerGroup getFallBack() {
		return fallBack;
	}
	
	public void setFallBack(ServerGroup group) {
		this.fallBack = group;
	}

}
