package de.atlascore.block.data.type;

import java.util.List;

import de.atlascore.block.data.CoreBlockData;
import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.block.data.type.BubbleColumn;

public class CoreBubbleColumn extends CoreBlockData implements BubbleColumn {
	
	protected static final List<BlockDataProperty<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, BlockDataProperty.DRAG);
	}
	
	private boolean drag;
	
	public CoreBubbleColumn(BlockType type) {
		super(type);
		drag = true;
	}

	@Override
	public boolean isDrag() {
		return drag;
	}

	@Override
	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	@Override
	public int getStateID() {
		return super.getStateID()+(drag?0:1);
	}
	
	@Override
	public List<BlockDataProperty<?>> getProperties() {
		return PROPERTIES;
	}
	
}
