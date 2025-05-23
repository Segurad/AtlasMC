package de.atlascore.block.data;

import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.Openable;
import de.atlasmc.block.data.property.BlockDataProperty;

public class CoreOpenable extends CoreBlockData implements Openable {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.OPEN);
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
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
