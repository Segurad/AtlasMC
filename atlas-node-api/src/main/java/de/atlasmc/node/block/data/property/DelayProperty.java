package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Repeater;

class DelayProperty extends AbstractIntProperty {

	public DelayProperty() {
		super("delay");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Repeater repeater)
			repeater.setDelay(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Repeater repeater)
			return repeater.getDelay();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Repeater;
	}
	
}
