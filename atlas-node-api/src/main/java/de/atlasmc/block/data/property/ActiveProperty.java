package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.CreakingHeart;

class ActiveProperty extends AbstractBooleanProperty {

	public ActiveProperty() {
		super("active");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof CreakingHeart heart)
			heart.setActive(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof CreakingHeart heart)
			return heart.isActive();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof CreakingHeart;
	}

}
