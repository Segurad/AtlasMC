package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.SeaPickle;

class PicklesProperty extends AbstractIntProperty {

	public PicklesProperty() {
		super("pickles");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof SeaPickle pickle)
			pickle.setPickles(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof SeaPickle pickle)
			return pickle.getPickles();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SeaPickle;
	}

}
