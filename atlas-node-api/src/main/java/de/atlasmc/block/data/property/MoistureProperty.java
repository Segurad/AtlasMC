package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Farmland;

class MoistureProperty extends AbstractIntProperty {

	public MoistureProperty() {
		super("moisture");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Farmland farmland)
			farmland.setMoisture(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Farmland farmland)
			return farmland.getMoisture();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Farmland;
	}

}
