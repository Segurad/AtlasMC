package de.atlascore;

import de.atlasmc.AtlasNodeBuilder;
import de.atlasmc.LocalAtlasNode;
import de.atlasmc.LocalAtlasNodeFactory;

public class CoreLocalAtlasNodeFactory implements LocalAtlasNodeFactory {

	@Override
	public LocalAtlasNode createNode(AtlasNodeBuilder builder) {
		return new CoreLocalAtlasNode(builder);
	}

}
