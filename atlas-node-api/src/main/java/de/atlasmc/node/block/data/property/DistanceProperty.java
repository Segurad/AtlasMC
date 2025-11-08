package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Distanced;

class DistanceProperty extends AbstractIntProperty {

	public DistanceProperty() {
		super("distance");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Distanced leaves)
			leaves.setDistance(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Distanced leaves)
			return leaves.getDistance();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Distanced;
	}

}
