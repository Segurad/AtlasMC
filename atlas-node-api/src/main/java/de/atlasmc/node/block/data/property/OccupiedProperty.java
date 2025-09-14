package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.Bed;

class OccupiedProperty extends AbstractBooleanProperty {

	public OccupiedProperty() {
		super("occupied");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Bed bed)
			bed.setOccupied(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Bed bed)
			return bed.isOccupied();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Bed;
	}

}
