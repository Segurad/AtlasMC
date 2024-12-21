package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.TNT;

class UnstableProperty extends AbstractBooleanProperty {

	public UnstableProperty() {
		super("unstable");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof TNT tnt)
			tnt.setUnstable(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof TNT tnt)
			return tnt.isUnstable();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof TNT;
	}

}
