package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Waterlogged;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreWaterlogged extends CoreBlockData implements Waterlogged {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.WATERLOGGED);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
