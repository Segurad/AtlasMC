package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.TurtleEgg;

class HatchProperty extends AbstractIntProperty {

	public HatchProperty() {
		super("hatch");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof TurtleEgg egg)
			egg.setHatch(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof TurtleEgg egg)
			return egg.getHatch();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof TurtleEgg;
	}

}
