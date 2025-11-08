package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.Ageable;
import de.atlasmc.node.block.data.BlockData;

class AgeProperty extends AbstractIntProperty {

	public AgeProperty() {
		super("age");
	}

	@Override
	public void setInt(BlockData data, int value) {
		if (data instanceof Ageable ageable)
			ageable.setAge(value);
	}

	@Override
	public int getInt(BlockData data) {
		if (data instanceof Ageable ageable)
			return ageable.getAge();
		return 0;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Ageable;
	}

}
