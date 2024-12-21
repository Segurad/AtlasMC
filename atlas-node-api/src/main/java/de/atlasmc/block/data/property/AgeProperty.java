package de.atlasmc.block.data.property;

import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.BlockData;

class AgeProperty extends AbstractIntProperty {

	public AgeProperty() {
		super("age");
	}

	@Override
	public void set(BlockData data, Integer value) {
		if (data instanceof Ageable ageable)
			ageable.setAge(value);
	}

	@Override
	public Integer get(BlockData data) {
		if (data instanceof Ageable ageable)
			return ageable.getAge();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Ageable;
	}

}
