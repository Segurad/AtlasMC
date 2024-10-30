package de.atlascore.master.node;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.atlasnetwork.NodeConfig;
import de.atlasmc.master.node.AtlasNode;
import de.atlasmc.master.node.NodeManager;

public class CoreNodeManager implements NodeManager {
	
	private Map<UUID, AtlasNode> nodes;
	private Map<String, NodeConfig> nodeConfigs;

	public CoreNodeManager() {
		nodes = new ConcurrentHashMap<>();
		nodeConfigs = new ConcurrentHashMap<>();
	}
	
	@Override
	public AtlasNode getNode(UUID uuid) {
		return nodes.get(uuid);
	}

	@Override
	public NodeConfig getNodeConfig(String key) {
		return nodeConfigs.get(key);
	}

	@Override
	public Collection<AtlasNode> getNodes() {
		return nodes.values();
	}

	@Override
	public void addNodeConfig(NodeConfig config) {
		nodeConfigs.put(config.getName(), config);
	}

}
