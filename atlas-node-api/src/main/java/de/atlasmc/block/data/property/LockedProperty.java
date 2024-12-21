package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Repeater;

class LockedProperty extends AbstractBooleanProperty {

	public LockedProperty() {
		super("locked");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Repeater repeater)
			repeater.setLocked(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Repeater repeater)
			return repeater.isLocked();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Repeater;
	}

}
