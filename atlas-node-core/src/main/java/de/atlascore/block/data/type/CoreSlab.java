package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.Slab;

public class CoreSlab extends CoreBlockData implements Slab {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, 
				BlockDataProperty.WATERLOGGED,
				BlockDataProperty.TYPE);
	}
	
	private boolean waterlogged;
	private Type type;
	
	public CoreSlab(Material material) {
		super(material);
		type = Type.BOTTOM;
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
	public Type getType() {
		return type;
	}

	@Override
	public void setType(Type type) {
		if (type == null) 
			throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(waterlogged?0:1)+
				type.ordinal()*2;
	}

	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
