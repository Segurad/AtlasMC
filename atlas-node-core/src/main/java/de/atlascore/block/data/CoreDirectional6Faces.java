package de.atlascore.block.data;

import java.util.Set;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;

public class CoreDirectional6Faces extends CoreAbstractDirectional {
	
	private static final Set<BlockFace> ALLOWED_FACES =
			Set.of(BlockFace.NORTH,
					BlockFace.SOUTH,
					BlockFace.EAST,
					BlockFace.WEST,
					BlockFace.UP,
					BlockFace.DOWN);

	public CoreDirectional6Faces(BlockType type) {
		super(type);
	}

	public CoreDirectional6Faces(BlockType type, BlockFace face) {
		super(type, face);
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
		return getType().getBlockStateID()+getFaceValue();
	}

}
