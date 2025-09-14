package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Gate;

class InWallProperty extends AbstractBooleanProperty {

	public InWallProperty() {
		super("in_wall");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Gate gate)
			gate.setInWall(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Gate gate)
			return gate.isInWall();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Gate;
	}

}
