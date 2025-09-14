package de.atlasmc.core.node.block.data.type;

import de.atlasmc.core.node.block.data.CoreWaterloggedDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.type.Dripleaf;

public class CoreDripleaf extends CoreWaterloggedDirectional4Faces implements Dripleaf {
	
	public CoreDripleaf(BlockType type) {
		super(type);
	}
	
	@Override
	public CoreDripleaf clone() {
		return (CoreDripleaf) super.clone();
	}

}
