package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreMultipleFacing6;
import de.atlasmc.node.block.BlockFace;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreMultipleFacing6Waterlogged extends CoreMultipleFacing6 implements Waterlogged {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreMultipleFacing6.PROPERTIES, PropertyType.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreMultipleFacing6Waterlogged(BlockType type) {
		super(type);
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
		return getType().getBlockStateID()+
				(hasFace(BlockFace.WEST)?0:1)+
				(waterlogged?0:2)+
				(hasFace(BlockFace.UP)?0:4)+
				(hasFace(BlockFace.SOUTH)?0:8)+
				(hasFace(BlockFace.NORTH)?0:16)+
				(hasFace(BlockFace.EAST)?0:32)+
				(hasFace(BlockFace.DOWN)?0:64);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
