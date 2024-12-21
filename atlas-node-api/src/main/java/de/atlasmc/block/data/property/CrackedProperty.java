package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.DecoratedPot;

class CrackedProperty extends AbstractBooleanProperty {

	public CrackedProperty() {
		super("cracked");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof DecoratedPot pot)
			pot.setCracked(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof DecoratedPot pot)
			return pot.isCracked();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof DecoratedPot;
	}

}
