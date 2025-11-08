package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreLevelled;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Light;

public class CoreLight extends CoreLevelled implements Light {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreLevelled.PROPERTIES, PropertyType.WATERLOGGED);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
