package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.Bisected;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Bisected.Half;

class HalfProperty extends AbstractEnumProperty<Half> {

	public HalfProperty() {
		super("half", Half.class);
	}

	@Override
	public void set(BlockData data, Half value) {
		if (data instanceof Bisected bisected)
			bisected.setHalf(value);
	}

	@Override
	public Half get(BlockData data) {
		if (data instanceof Bisected bisected)
			return bisected.getHalf();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bisected;
	}

}
