package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreLightable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Bulb;

public class CoreBulb extends CoreLightable implements Bulb {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreLightable.PROPERTIES, PropertyType.POWERED);
	}
	
	protected boolean powered;
	
	public CoreBulb(BlockType type) {
		super(type);
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

	@Override
	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	@Override
	public int getStateID() {
		return getType().getBlockStateID() + (lit?0:1) + (powered?0:2);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
