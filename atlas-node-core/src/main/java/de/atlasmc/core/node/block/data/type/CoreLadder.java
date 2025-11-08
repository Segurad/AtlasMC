package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreDirectional4Faces;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Ladder;

public class CoreLadder extends CoreDirectional4Faces implements Ladder {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, PropertyType.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreLadder(BlockType type) {
		super(type);
	}

	@Override
	public int getStateID() {
		return type.getBlockStateID()+getFaceValue()*2+(waterlogged?0:1);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
