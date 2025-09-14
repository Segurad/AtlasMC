package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.Ominous;

class OminousProperty extends AbstractBooleanProperty {

	public OminousProperty() {
		super("ominous");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Ominous ominous)
			ominous.setOminous(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Ominous ominous)
			return ominous.isOminous();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Ominous;
	}

}
