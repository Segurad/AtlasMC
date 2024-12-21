package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreMultipleFacing5 extends CoreAbstractMultipleFacing {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAbstractMultipleFacing.PROPERTIES,
				BlockDataProperty.NORTH,
				BlockDataProperty.SOUTH,
				BlockDataProperty.EAST,
				BlockDataProperty.WEST,
				BlockDataProperty.UP);
	}
	
	private static final Set<BlockFace> ALLOWED_FACES =
			EnumSet.of(BlockFace.NORTH,
					BlockFace.SOUTH,
					BlockFace.EAST,
					BlockFace.WEST,
					BlockFace.UP);
	
	public CoreMultipleFacing5(Material material) {
		super(material);
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(hasFace(BlockFace.UP)?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16);
	}
	
	@Override
	public boolean isValid(BlockFace face) {
		return face.ordinal()<5;
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
