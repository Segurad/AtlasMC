package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Brushable;

class DustedProperty extends AbstractIntProperty {

	public DustedProperty() {
		super("dusted");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Brushable block)
			block.setDusted(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Brushable block)
			return block.getDusted();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Brushable;
	}

}
