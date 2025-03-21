package de.atlascore.block.data.type;

import de.atlascore.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.type.Dripleaf;

public class CoreDripleaf extends CoreWaterloggedDirectional4Faces implements Dripleaf {
	
	public CoreDripleaf(BlockType type) {
		super(type);
	}
	
	@Override
	public CoreDripleaf clone() {
		return (CoreDripleaf) super.clone();
	}

}
