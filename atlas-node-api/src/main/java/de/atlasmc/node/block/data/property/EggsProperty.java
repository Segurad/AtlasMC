package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.TurtleEgg;

class EggsProperty extends AbstractIntProperty {

	public EggsProperty() {
		super("eggs");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof TurtleEgg egg)
			egg.setEggs(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof TurtleEgg egg)
			return egg.getEggs();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof TurtleEgg;
	}

}
