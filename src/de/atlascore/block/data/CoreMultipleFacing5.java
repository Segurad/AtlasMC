package de.atlascore.block.data;

import java.util.EnumSet;
import java.util.Set;

import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;

public class CoreMultipleFacing5 extends CoreAbstractMultipleFacing {

	public CoreMultipleFacing5(Material material) {
		super(material);
	}
	
	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
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
		return EnumSet.range(BlockFace.NORTH, BlockFace.UP);
	}

}
