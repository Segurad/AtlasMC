package de.atlasmc.core.node.block.data.type;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.type.WallSign;

public class CoreWallSign extends CoreWaterloggedDirectional4Faces implements WallSign {

	public CoreWallSign(BlockType type) {
		super(type);
	}

}
