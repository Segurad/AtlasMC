package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreMultipleFacing6;
import de.atlasmc.Material;
import de.atlasmc.block.BlockFace;
import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreMultipleFacing6Waterlogged extends CoreMultipleFacing6 implements Waterlogged {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreMultipleFacing6.PROPERTIES, BlockDataProperty.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreMultipleFacing6Waterlogged(Material material) {
		super(material);
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
	public int getStateID() {
		return getMaterial().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(waterlogged?0:2)+
				(hasFace(BlockFace.UP)?0:4)+
				(hasFace(BlockFace.SOUTH)?0:8)+
				(hasFace(BlockFace.NORTH)?0:16)+
				(hasFace(BlockFace.EAST)?0:32)+
				(hasFace(BlockFace.DOWN)?0:64);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
