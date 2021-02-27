package de.atlascore.block.data.type;

import java.util.EnumSet;
import java.util.Set;

import de.atlascore.block.data.CoreAbstractMultipleFacing;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.type.Fence;

public class CoreFence extends CoreAbstractMultipleFacing implements Fence {

	private boolean waterlogged;
	
	public CoreFence(Material material) {
		super(material, 4);
	}

	@Override
	public boolean isWaterlogged() {
		return waterlogged;
	}

	@Override
	public void setWaterlogged(boolean waterlogged) {
		this.waterlogged = waterlogged;
	}

	@Override
	public Set<BlockFace> getAllowedFaces() {
		return EnumSet.range(BlockFace.NORTH, BlockFace.WEST);
	}

	@Override
	public int getStateID() {
		return getMaterial().getBlockID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(waterlogged?0:2)+
				(hasFace(BlockFace.SOUTH)?0:4)+
				(hasFace(BlockFace.NORTH)?0:8)+
				(hasFace(BlockFace.EAST)?0:16);
	}

	@Override
	public boolean isValid(BlockFace face) {
		return face.ordinal() < 4;
	}

}
