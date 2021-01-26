package de.atlasmc.io.atlasnetwork;

import java.util.List;

import de.atlasmc.io.atlasnetwork.server.AtlasPlayer;
import de.atlasmc.io.atlasnetwork.server.ServerGroup;

public class AtlasNetwork {
	
	private List<ServerGroup> groups;
	private List<AtlasNode> nodes;
 	private AtlasNode master;
	private ServerGroup fallBack;
	
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
