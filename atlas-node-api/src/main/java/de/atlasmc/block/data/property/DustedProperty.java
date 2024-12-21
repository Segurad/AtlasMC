package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Brushable;

class DustedProperty extends AbstractIntProperty {

	public DustedProperty() {
		super("dusted");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Brushable block)
			block.setDusted(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Brushable block)
			return block.getDusted();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Brushable;
	}

}
