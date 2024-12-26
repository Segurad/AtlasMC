package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.Orientable;
import de.atlasmc.block.data.Orientable.Orientation;

class OrientationProperty extends AbstractEnumProperty<Orientation> {

	public OrientationProperty() {
		super("orientation", Orientation.class);
	}

	@Override
	public void set(BlockData data, Orientation value) {
		if (data instanceof Orientable orientable)
			orientable.setOrientation(value);
	}

	@Override
	public Orientation get(BlockData data) {
		if (data instanceof Orientable orientable)
			return orientable.getOrientation();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Orientable;
	}

}
