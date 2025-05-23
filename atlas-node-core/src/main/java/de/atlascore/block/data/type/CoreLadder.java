package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreDirectional4Faces;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Ladder;

public class CoreLadder extends CoreDirectional4Faces implements Ladder {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreDirectional4Faces.PROPERTIES, BlockDataProperty.WATERLOGGED);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
