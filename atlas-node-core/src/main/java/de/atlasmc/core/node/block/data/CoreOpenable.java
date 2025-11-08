package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Openable;
import de.atlasmc.node.block.data.property.PropertyType;

public class CoreOpenable extends CoreBlockData implements Openable {

	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.OPEN);
	}
	
	private boolean open;
	
	public CoreOpenable(BlockType type) {
		super(type);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(open?0:1);
	}
	
	@Override
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}

}
