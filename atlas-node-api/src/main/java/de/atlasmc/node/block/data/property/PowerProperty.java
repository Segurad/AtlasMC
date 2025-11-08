package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.AnaloguePowerable;
import de.atlasmc.node.block.data.BlockData;

class PowerProperty extends AbstractIntProperty {

	public PowerProperty() {
		super("power");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof AnaloguePowerable powerable)
			powerable.setPower(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof AnaloguePowerable powerable)
			return powerable.getPower();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof AnaloguePowerable;
	}

}
