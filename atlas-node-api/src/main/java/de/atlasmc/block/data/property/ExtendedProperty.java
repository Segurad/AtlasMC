package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Piston;

class ExtendedProperty extends AbstractBooleanProperty {

	public ExtendedProperty() {
		super("extended");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Piston piston)
			piston.setExtended(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Piston piston)
			return piston.isExtended();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Piston;
	}

}
