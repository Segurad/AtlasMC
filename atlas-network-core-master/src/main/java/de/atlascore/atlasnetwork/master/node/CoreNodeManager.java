package de.atlascore.atlasnetwork.master.node;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlascore.atlasnetwork.CoreAtlasNode;
import de.atlasmc.atlasnetwork.NodeManager;

public class CoreNodeManager implements NodeManager {
	
	private final Map<UUID, CoreAtlasNodeMaster> nodes;
	
	public CoreNodeManager() {
		nodes = new ConcurrentHashMap<UUID, CoreAtlasNodeMaster>();
	}
	
	@Override
	public Collection<CoreAtlasNodeMaster> getNodes() {
		return nodes.values();
	}
	
	@Override
	public CoreAtlasNode getNode(UUID uuid) {
		return nodes.get(uuid);
	}

}
