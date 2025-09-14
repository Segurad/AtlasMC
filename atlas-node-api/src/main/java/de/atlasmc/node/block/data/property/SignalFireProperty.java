package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Campfire;

class SignalFireProperty extends AbstractBooleanProperty {

	public SignalFireProperty() {
		super("signal_fire");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Campfire fire)
			fire.setSignalFire(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Campfire fire)
			return fire.isSignalFire();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Campfire;
	}

}
