package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Levelled;

class LevelProperty extends AbstractIntProperty {

	public LevelProperty() {
		super("level");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Levelled levelled)
			levelled.setLevel(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Levelled levelled)
			return levelled.getLevel();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Levelled;
	}

}
