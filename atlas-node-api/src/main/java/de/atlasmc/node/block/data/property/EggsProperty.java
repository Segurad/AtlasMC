package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.TurtleEgg;

class EggsProperty extends AbstractIntProperty {

	public EggsProperty() {
		super("eggs");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof TurtleEgg egg)
			egg.setEggs(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof TurtleEgg egg)
			return egg.getEggs();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof TurtleEgg;
	}

}
