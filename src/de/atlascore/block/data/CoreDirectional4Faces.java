package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;

public class CoreDirectional4Faces extends CoreAbstractDirectional {

	public CoreDirectional4Faces(Material material) {
		super(material);
	}

	@Override
	public Set<BlockFace> getFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}

	@Override
	protected int getFaceValue(BlockFace face) {
		switch (getFacing()) {
		case NORTH: return 0;
		case EAST: return 3;
		case SOUTH: return 1;
		case WEST: return 2;
		default:
			return -1;
		}
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+getFaceValue();
	}

}
