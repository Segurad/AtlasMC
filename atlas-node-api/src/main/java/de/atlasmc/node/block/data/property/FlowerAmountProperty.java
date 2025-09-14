package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.PinkPetals;

class FlowerAmountProperty extends AbstractIntProperty {

	public FlowerAmountProperty() {
		super("flower_amount");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof PinkPetals petals)
			petals.setFlowerAmount(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof PinkPetals petals)
			return petals.getFlowerAmount();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof PinkPetals;
	}

}
