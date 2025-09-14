package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.CaveVinesPlant;

class BerriesProperty extends AbstractBooleanProperty {

	public BerriesProperty() {
		super("berries");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof CaveVinesPlant vines)
			vines.setBerries(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof CaveVinesPlant vines)
			return vines.hasBerries();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof CaveVinesPlant;
	}

}
