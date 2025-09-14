package de.atlasmc.core.node.block.data.type;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.type.CoralWallFan;

public class CoreCoralWallFan extends CoreWaterloggedDirectional4Faces implements CoralWallFan {
	
	public CoreCoralWallFan(BlockType type) {
		super(type);
		setWaterlogged(true);
	}

}
