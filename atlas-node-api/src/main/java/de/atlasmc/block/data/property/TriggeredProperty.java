package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Triggerable;

class TriggeredProperty extends AbstractBooleanProperty {

	public TriggeredProperty() {
		super("triggered");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Triggerable triggerable)
			triggerable.setTriggered(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Triggerable triggerable)
			return triggerable.isTriggered();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Triggerable;
	}

}
