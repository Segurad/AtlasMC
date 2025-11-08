package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Beehive;

class HoneyLevelProperty extends AbstractIntProperty {

	public HoneyLevelProperty() {
		super("honey_level");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Beehive beehive)
			beehive.setHoneyLevel(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Beehive beehive)
			return beehive.getHoneyLevel();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Beehive;
	}

}
