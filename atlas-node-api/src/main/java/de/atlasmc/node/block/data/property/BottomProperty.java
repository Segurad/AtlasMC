package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.BottomConnectable;

class BottomProperty extends AbstractBooleanProperty {

	public BottomProperty() {
		super("bottom");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof BottomConnectable bottom)
			bottom.setBottom(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof BottomConnectable bottom)
			return bottom.isBottom();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof BottomConnectable;
	}

}
