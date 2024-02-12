package de.atlascore.block.data;

import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;

public class CoreDirectional6Faces extends CoreAbstractDirectional {
	
	private static final Set<BlockFace> ALLOWED_FACES =
			Set.of(BlockFace.NORTH,
					BlockFace.SOUTH,
					BlockFace.EAST,
					BlockFace.WEST,
					BlockFace.UP,
					BlockFace.DOWN);

	public CoreDirectional6Faces(Material material) {
		super(material);
	}

	public CoreDirectional6Faces(Material material, BlockFace face) {
		super(material, face);
	}
	
	@Override
	public Set<BlockFace> getFaces() {
		return ALLOWED_FACES;
	}

	@Override
	protected int getFaceValue(BlockFace face) {
		int val = face.ordinal();
		return val <= 6 ? val : -1;
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+getFaceValue();
	}

}
