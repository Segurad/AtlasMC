package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Repeater;

class DelayProperty extends AbstractIntProperty {

	public DelayProperty() {
		super("delay");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Repeater repeater)
			repeater.setDelay(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Repeater repeater)
			return repeater.getDelay();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Repeater;
	}
	
}
