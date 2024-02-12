package de.atlasmc.atlasnetwork;

import java.util.Collection;
import java.util.UUID;

public interface NodeManager {
	
	AtlasNode getNode(UUID uuid);

	Collection<? extends AtlasNode> getNodes();
	
}
