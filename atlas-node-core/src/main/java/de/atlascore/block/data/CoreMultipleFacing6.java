package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.block.BlockFace;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreMultipleFacing6 extends CoreAbstractMultipleFacing {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAbstractMultipleFacing.PROPERTIES,
				BlockDataProperty.NORTH,
				BlockDataProperty.SOUTH,
				BlockDataProperty.EAST,
				BlockDataProperty.WEST,
				BlockDataProperty.UP,
				BlockDataProperty.DOWN);
	}
	
	private static final Set<BlockFace> ALLOWED_FACES =
			EnumSet.of(BlockFace.NORTH,
					BlockFace.SOUTH,
					BlockFace.EAST,
					BlockFace.WEST,
					BlockFace.UP,
					BlockFace.DOWN);
	
	public CoreMultipleFacing6(BlockType type) {
		super(type);
	}
	
	@Override
	public int getStateID() {
		return getType().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.UP)?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16)+
				(hasFace(BlockFace.DOWN)?0:32);
	}
	
	@Override
	public boolean isValid(BlockFace face) {
		return face.ordinal()<6;
	}
	
	@Override
	public Set<BlockFace> getAllowedFaces() {
		return ALLOWED_FACES;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
