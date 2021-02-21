package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;

public abstract class CoreAbstractDirectional6Faces extends CoreAbstractDirectional {

	public CoreAbstractDirectional6Faces(Material material) {
		super(material);
	}

	@Override
	public Set<BlockFace> getFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.DOWN);
	}

	@Override
	protected int getFaceValue(BlockFace face) {
		int val = face.ordinal();
		return val < 6 ? val : -1;
	}

}
