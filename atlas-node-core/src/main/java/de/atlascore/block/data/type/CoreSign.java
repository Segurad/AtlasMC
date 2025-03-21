package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterloggedRotatable;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.type.Sign;

public class CoreSign extends CoreWaterloggedRotatable implements Sign {

	public CoreSign(BlockType type) {
		super(type);
	}

}
