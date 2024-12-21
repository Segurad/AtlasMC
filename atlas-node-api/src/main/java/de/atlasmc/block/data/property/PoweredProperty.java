package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Powerable;

class PoweredProperty extends AbstractBooleanProperty {

	public PoweredProperty() {
		super("powered");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Powerable powerable)
			powerable.setPowered(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Powerable powerable)
			return powerable.isPowered();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Powerable;
	}

}
