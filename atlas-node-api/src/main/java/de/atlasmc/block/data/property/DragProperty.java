package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.BubbleColumn;

class DragProperty extends AbstractBooleanProperty {

	public DragProperty() {
		super("drag");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof BubbleColumn column)
			column.setDrag(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof BubbleColumn column)
			return column.isDrag();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof BubbleColumn;
	}

}
