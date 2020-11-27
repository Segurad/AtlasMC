package de.atlasmc.server;

import java.util.List;

public class AtlasNetwork {
	
	private List<ServerGroup> groups;
	private List<ServerPlayer> players;
	private List<AtlasNode> nodes;
	private AtlasNode master;
	
	public AtlasNode getMaster() {
		return master;
	}

}
