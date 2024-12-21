package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Leaves;

class PersistentProperty extends AbstractBooleanProperty {

	public PersistentProperty() {
		super("persistent");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Leaves leaves)
			leaves.setPersistent(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Leaves leaves)
			return leaves.isPersistent();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Leaves;
	}

}
