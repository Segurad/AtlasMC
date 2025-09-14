package de.atlascore.network.master.node;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.core.network.CoreAtlasNode;
import de.atlasmc.network.NodeManager;
import de.atlasmc.tick.Tickable;

public class CoreNodeManager implements NodeManager, Tickable {
	
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

	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
