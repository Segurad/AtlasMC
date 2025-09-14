package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Hopper;

class EnabledProperty extends AbstractBooleanProperty {

	public EnabledProperty() {
		super("enabled");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Hopper hopper)
			hopper.setEnabled(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Hopper hopper)
			return hopper.isEnabled();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Hopper;
	}

}
