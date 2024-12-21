package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.DaylightDetectore;

class InvertedProperty extends AbstractBooleanProperty {

	public InvertedProperty() {
		super("inverted");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof DaylightDetectore detectore)
			detectore.setInverted(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof DaylightDetectore detectore)
			return detectore.isInverted();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof DaylightDetectore;
	}

}
