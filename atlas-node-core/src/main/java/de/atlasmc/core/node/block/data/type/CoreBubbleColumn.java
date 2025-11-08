package de.atlasmc.core.node.block.data.type;

import java.util.List;

import de.atlasmc.core.node.block.data.CoreBlockData;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.property.PropertyType;
import de.atlasmc.node.block.data.type.BubbleColumn;

public class CoreBubbleColumn extends CoreBlockData implements BubbleColumn {
	
	protected static final List<PropertyType<?>> PROPERTIES;
	
	static {
		PROPERTIES = merge(CoreBlockData.PROPERTIES, PropertyType.DRAG);
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
	public List<PropertyType<?>> getProperties() {
		return PROPERTIES;
	}
	
}
