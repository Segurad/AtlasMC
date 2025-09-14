package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.HangingSign;

public class CoreHangingSign extends CoreSign implements HangingSign {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreSign.PROPERTIES, BlockDataProperty.ATTACHED);
	}
	
	private boolean attached;
	
	public CoreHangingSign(BlockType type) {
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
		return super.getStateID()+(attached?0:32);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
