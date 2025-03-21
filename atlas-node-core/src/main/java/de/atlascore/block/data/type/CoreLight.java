package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreLevelled;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Light;

public class CoreLight extends CoreLevelled implements Light {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreLevelled.PROPERTIES, BlockDataProperty.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreLight(BlockType type) {
		super(type, 15);
		setLevel(15);
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
		return getType().getBlockStateID() + (waterlogged?0:1) + getLevel()*2;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
