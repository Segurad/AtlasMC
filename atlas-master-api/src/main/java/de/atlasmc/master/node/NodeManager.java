package de.atlasmc.master.node;

import java.util.Collection;
import java.util.UUID;

public interface NodeManager {
	
	AtlasNode getNode(UUID uuid);

	Collection<AtlasNode> getNodes();

}
