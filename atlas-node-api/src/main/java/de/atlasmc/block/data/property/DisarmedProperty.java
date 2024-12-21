package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Tripwire;

class DisarmedProperty extends AbstractBooleanProperty {

	public DisarmedProperty() {
		super("disarmed");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Tripwire tripwire)
			tripwire.setDisarmed(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Tripwire tripwire)
			return tripwire.isDisarmed();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Tripwire;
	}

}
