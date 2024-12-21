package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.SculkCatalyst;

class BloomProperty extends AbstractBooleanProperty {

	public BloomProperty() {
		super("bloom");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof SculkCatalyst catalyst)
			catalyst.setBloom(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof SculkCatalyst catalyst)
			return catalyst.isBloom();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof SculkCatalyst;
	}

}
