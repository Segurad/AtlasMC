package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Distanced;

class DistanceProperty extends AbstractIntProperty {

	public DistanceProperty() {
		super("distance");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Distanced leaves)
			leaves.setDistance(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Distanced leaves)
			return leaves.getDistance();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Distanced;
	}

}
