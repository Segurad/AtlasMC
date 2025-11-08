package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreAxisOrientable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Chain;

public class CoreChain extends CoreAxisOrientable implements Chain {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreAxisOrientable.PROPERTIES, PropertyType.WATERLOGGED);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
