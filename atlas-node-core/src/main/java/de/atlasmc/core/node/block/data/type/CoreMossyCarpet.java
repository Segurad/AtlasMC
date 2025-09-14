package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreHightConnectable;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.BlockDataProperty;
import de.atlasmc.node.block.data.type.MossyCarpet;

public class CoreMossyCarpet extends CoreHightConnectable implements MossyCarpet {

	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreHightConnectable.PROPERTIES, BlockDataProperty.BOTTOM);
	}
	
	protected boolean bottom;
	
	public CoreMossyCarpet(BlockType type) {
		super(type);
		bottom = true;
	}

	@Override
	public boolean isBottom() {
		return bottom;
	}

	@Override
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
	
	@Override
	public int getStateID() {
		return super.getStateID()+(bottom?0:81);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}

}
