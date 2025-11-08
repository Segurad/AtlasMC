package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreWaterlogged;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Lantern;

public class CoreLantern extends CoreWaterlogged implements Lantern {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreWaterlogged.PROPERTIES, PropertyType.HANGING);
	}
	
	protected boolean hanging;
	
	public CoreLantern(BlockType type) {
		super(type);
	}

	@Override
	public boolean isHanging() {
		return hanging;
	}

	@Override
	public void setHanging(boolean hanging) {
		this.hanging = hanging;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+
				(hanging?0:2);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
