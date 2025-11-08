package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.SeaPickle;

class PicklesProperty extends AbstractIntProperty {

	public PicklesProperty() {
		super("pickles");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof SeaPickle pickle)
			pickle.setPickles(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof SeaPickle pickle)
			return pickle.getPickles();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SeaPickle;
	}

}
