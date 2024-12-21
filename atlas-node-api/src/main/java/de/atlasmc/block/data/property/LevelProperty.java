package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Levelled;

class LevelProperty extends AbstractIntProperty {

	public LevelProperty() {
		super("level");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Levelled levelled)
			levelled.setLevel(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Levelled levelled)
			return levelled.getLevel();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Levelled;
	}

}
