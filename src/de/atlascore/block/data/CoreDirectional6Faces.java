package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;

public class CoreDirectional6Faces extends CoreAbstractDirectional {

	public CoreDirectional6Faces(Material material) {
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

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue();
	}

}
