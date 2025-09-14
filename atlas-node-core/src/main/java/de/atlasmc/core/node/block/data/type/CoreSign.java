package de.atlasmc.core.node.block.data.type;

import de.atlasmc.core.node.block.data.CoreWaterloggedRotatable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.type.Sign;

public class CoreSign extends CoreWaterloggedRotatable implements Sign {

	public CoreSign(BlockType type) {
		super(type);
	}

}
