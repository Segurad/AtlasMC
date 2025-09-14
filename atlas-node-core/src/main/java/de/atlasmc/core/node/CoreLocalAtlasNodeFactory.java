package de.atlasmc.core.node;

import de.atlasmc.node.AtlasNodeBuilder;
import de.atlasmc.node.LocalAtlasNode;
import de.atlasmc.node.LocalAtlasNodeFactory;

public class CoreLocalAtlasNodeFactory implements LocalAtlasNodeFactory {

	@Override
	public LocalAtlasNode createNode(AtlasNodeBuilder builder) {
		return new CoreLocalAtlasNode(builder);
	}

}
