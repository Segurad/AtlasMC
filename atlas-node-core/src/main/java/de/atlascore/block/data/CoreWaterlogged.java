package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.Waterlogged;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreWaterlogged extends CoreBlockData implements Waterlogged {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.WATERLOGGED);
	}
	
	protected boolean waterlogged;
	
	public CoreWaterlogged(BlockType type) {
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
		return super.getStateID()+(waterlogged?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
