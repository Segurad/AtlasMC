package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Cake;

class BitesProperty extends AbstractIntProperty {

	public BitesProperty() {
		super("bites");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Cake cake)
			cake.setBites(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Cake cake)
			return cake.getBites();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Cake;
	}

}
