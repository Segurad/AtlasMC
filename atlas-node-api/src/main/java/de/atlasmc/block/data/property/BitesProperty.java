package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Cake;

class BitesProperty extends AbstractIntProperty {

	public BitesProperty() {
		super("bites");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Cake cake)
			cake.setBites(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Cake cake)
			return cake.getBites();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Cake;
	}

}
