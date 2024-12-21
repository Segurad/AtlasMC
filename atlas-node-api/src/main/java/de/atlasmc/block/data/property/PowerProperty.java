package de.atlasmc.block.data.property;

import de.atlasmc.block.data.AnaloguePowerable;
import de.atlasmc.block.data.BlockData;

class PowerProperty extends AbstractIntProperty {

	public PowerProperty() {
		super("power");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof AnaloguePowerable powerable)
			powerable.setPower(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof AnaloguePowerable powerable)
			return powerable.getPower();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof AnaloguePowerable;
	}

}
