package de.atlasmc.network;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.util.concurrent.future.Future;

public interface NodeManager {
	
	AtlasNode getNode(UUID uuid);
	
	Future<AtlasNode> loadNode(UUID uuid);

	Collection<? extends AtlasNode> getNodes();
	
}
