package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreAxisOrientable;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Chain;

public class CoreChain extends CoreAxisOrientable implements Chain {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAxisOrientable.PROPERTIES, BlockDataProperty.WATERLOGGED);
	}
	
	private boolean waterlogged;
	
	public CoreChain(BlockType type) {
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
		return type.getBlockStateID()+
				(waterlogged?0:1)+
				axis.ordinal()*2;
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
