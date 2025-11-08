package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Flower;

class FlowerAmountProperty extends AbstractIntProperty {

	public FlowerAmountProperty() {
		super("flower_amount");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Flower petals)
			petals.setFlowerAmount(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Flower petals)
			return petals.getFlowerAmount();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Flower;
	}

}
