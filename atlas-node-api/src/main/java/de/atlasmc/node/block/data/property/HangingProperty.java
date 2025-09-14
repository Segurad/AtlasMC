package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Hangable;

class HangingProperty extends AbstractBooleanProperty {

	public HangingProperty() {
		super("hanging");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Hangable hangable)
			hangable.setHanging(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Hangable hangable)
			return hangable.isHanging();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Hangable;
	}

}
