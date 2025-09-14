package de.atlasmc.node;

import de.atlasmc.util.factory.Factory;

public interface LocalAtlasNodeFactory extends Factory {
	
	LocalAtlasNode createNode(AtlasNodeBuilder builder);

}
