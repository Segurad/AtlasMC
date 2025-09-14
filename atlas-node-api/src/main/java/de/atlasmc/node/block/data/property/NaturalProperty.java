package de.atlasmc.node.block.data.property;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.data.type.CreakingHeart;

class NaturalProperty extends AbstractBooleanProperty {

	public NaturalProperty() {
		super("natural");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof CreakingHeart heart)
			heart.setNatural(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof CreakingHeart heart)
			return heart.isNatural();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof CreakingHeart;
	}

}
