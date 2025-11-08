package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Farmland;

class MoistureProperty extends AbstractIntProperty {

	public MoistureProperty() {
		super("moisture");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Farmland farmland)
			farmland.setMoisture(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Farmland farmland)
			return farmland.getMoisture();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Farmland;
	}

}
