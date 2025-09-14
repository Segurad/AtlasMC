package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.SculkShrieker;

class ShriekingProperty extends AbstractBooleanProperty {

	public ShriekingProperty() {
		super("shrieking");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof SculkShrieker shrieker)
			shrieker.setShrieking(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof SculkShrieker shrieker)
			return shrieker.isShrieking();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SculkShrieker;
	}

}
