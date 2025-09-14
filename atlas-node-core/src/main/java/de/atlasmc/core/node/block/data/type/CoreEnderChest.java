package de.atlasmc.core.node.block.data.type;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.type.EnderChest;

public class CoreEnderChest extends CoreWaterloggedDirectional4Faces implements EnderChest {
	
	public CoreEnderChest(BlockType type) {
		super(type);
	}

}
