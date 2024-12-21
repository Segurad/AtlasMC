package de.atlasmc.block.data.property;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.data.type.Lectern;

class HasBookProperty extends AbstractBooleanProperty {

	public HasBookProperty() {
		super("has_book");
	}

	@Override
	public void set(BlockData data, Boolean value) {
		if (data instanceof Lectern lectern)
			lectern.setBook(value);
	}

	@Override
	public Boolean get(BlockData data) {
		if (data instanceof Lectern lectern)
			return lectern.hasBook();
		return null;
	}

	@Override
	public boolean supports(BlockData data) {
		return data instanceof Lectern;
	}

}
