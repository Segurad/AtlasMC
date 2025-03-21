package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreWaterlogged;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Slab;

public class CoreSlab extends CoreWaterlogged implements Slab {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, BlockDataProperty.TYPE);
	}
	
	protected Type slabType;
	
	public CoreSlab(BlockType type) {
		super(type);
		slabType = Type.BOTTOM;
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
	public Type getSlabType() {
		return slabType;
	}

	@Override
	public void setSlabType(Type type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		this.slabType = type;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(waterlogged?0:1)+
				slabType.ordinal()*2;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
