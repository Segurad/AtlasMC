package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Door;
import de.atlasmc.block.data.type.Door.Hinge;

class HingeProperty extends AbstractEnumProperty<Hinge> {

	public HingeProperty() {
		super("hinge", Hinge.class);
	}

	@Override
	public void set(BlockData data, Hinge value) {
		if (data instanceof Door door)
			door.setHinge(value);
	}

	@Override
	public Hinge get(BlockData data) {
		if (data instanceof Door door)
			return door.getHinge();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Door;
	}

}
