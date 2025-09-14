package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Sapling;

class StageProperty extends AbstractIntProperty {

	public StageProperty() {
		super("stage");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Sapling sapling)
			sapling.setStage(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Sapling sapling)
			return sapling.getStage();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Sapling;
	}

}
