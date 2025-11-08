package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreRotatable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.Skull;

public class CoreSkull extends CoreRotatable implements Skull {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreRotatable.PROPERTIES, PropertyType.POWERED);
	}
	
	private boolean powered;
	
	public CoreSkull(BlockType type) {
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
		return super.getStateID()+(powered?0:16);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
