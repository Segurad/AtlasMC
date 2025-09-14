package de.atlasmc.core.node.block.data;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.Attachable;
import de.atlasmc.node.block.data.property.BlockDataProperty;

public class CoreAttachable extends CoreBlockData implements Attachable {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.ATTACHED);
	}
	
	private boolean attached;
	
	public CoreAttachable(BlockType type) {
		super(type);
	}
	
	@Override
	public boolean isAttached() {
		return attached;
	}
	
	@Override
	public void setAttached(boolean attached) {
		this.attached = attached;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID() + (attached ? 0 : 1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
