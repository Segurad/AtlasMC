package de.atlasmc.master.node;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.atlasnetwork.NodeConfig;

public interface NodeManager {
	
	AtlasNode getNode(UUID uuid);
	
	NodeConfig getNodeConfig(String key);

	Collection<AtlasNode> getNodes();

}
