package de.atlasmc;

import de.atlasmc.util.Factory;

public interface LocalAtlasNodeFactory extends Factory {
	
	LocalAtlasNode createNode(AtlasNodeBuilder builder);

}
